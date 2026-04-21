package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // クラス図にある基本のSQL文
    private String baseSql = "select * from test where school_cd = ?";

    /**
     * getメソッド：特定の成績情報を1件取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "select * from test where student_no=? and subject_cd=? and school_cd=? and no=?")) {
            st.setString(1, student.getNo());
            st.setString(2, subject.getCd());
            st.setString(3, school.getCd());
            st.setInt(4, no);
            
            try (ResultSet rs = st.executeQuery()) {
                List<Test> list = postFilter(rs, school);
                if (!list.isEmpty()) {
                    test = list.get(0);
                }
            }
        }
        return test;
    }

    /**
     * postFilterメソッド：ResultSetをList<Test>に変換する
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        // 注意：本来はここでStudentDaoやSubjectDaoを使って完全なオブジェクトを作る必要がありますが
        // ここでは簡易的にIDのみをセットする例にします
        while (rSet.next()) {
            Test test = new Test();
            // 学生や科目のセット（詳細はプロジェクトの設計に合わせて調整してください）
            test.setNo(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            test.setSchool(school);
            // 実際には rs.getString("student_no") などを使って各Beanをセットします
            list.add(test);
        }
        return list;
    }

    /**
     * filterメソッド：条件を指定して成績一覧を取得する
     */
//    これ入れないと一覧にデータが表示されない
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

        List<Test> list = new ArrayList<>();

        String sql =
            "SELECT " +
            " s.no AS student_no, " +
            " s.name, " +
            " s.ent_year, " +
            " s.class_num, " +
            " t.point " +
            "FROM student s " +
            "LEFT JOIN test t " +
            " ON s.no = t.student_no " +
            " AND t.subject_cd = ? " +
            " AND t.no = ? " +
            " AND t.school_cd = ? " +
            "WHERE s.ent_year = ? " +
            " AND s.class_num = ? " +
            " AND s.school_cd = ? " +
            "ORDER BY s.no";

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // バインド
            st.setString(1, subject.getCd());
            st.setInt(2, num);
            st.setString(3, school.getCd());
            st.setInt(4, entYear);
            st.setString(5, classNum);
            st.setString(6, school.getCd());

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Test test = new Test();

                // ===== Studentセット =====
                Student student = new Student();
                student.setNo(rs.getString("student_no"));
                student.setName(rs.getString("name"));
                student.setEntYear(rs.getInt("ent_year"));
                student.setClassNum(rs.getString("class_num"));

                test.setStudent(student);

                // ===== その他セット =====
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(num);

                // ===== 点数（NULL対応）=====
                int point = rs.getInt("point");
                if (!rs.wasNull()) {
                    test.setPoint(point);
                }

                list.add(test);
            }
        }

        return list;
    }

    /**
     * saveメソッド（リスト）：リスト内の全成績を保存する
     */
    public boolean save(List<Test> list) throws Exception {
        boolean result = true;
        try (Connection con = getConnection()) {
            // 自動コミットをオフにしてトランザクション管理
            con.setAutoCommit(false);
            try {
                for (Test test : list) {
                    if (!save(test, con)) {
                        result = false;
                        break;
                    }
                }
                if (result) {
                    con.commit();
                } else {
                    con.rollback();
                }
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        }
        return result;
    }

    /**
     * saveメソッド（1件）：1件の成績を保存（INSERT or UPDATE）する
     * クラス図の赤色（private）に合わせてprivateで実装
     */
    private boolean save(Test test, Connection con) throws Exception {

        int count = 0;

        // 既存チェック
        String checkSql =
            "SELECT COUNT(*) FROM test " +
            "WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?";

        try (PreparedStatement checkSt = con.prepareStatement(checkSql)) {

            checkSt.setString(1, test.getStudent().getNo());
            checkSt.setString(2, test.getSubject().getCd());
            checkSt.setString(3, test.getSchool().getCd());
            checkSt.setInt(4, test.getNo());

            ResultSet rs = checkSt.executeQuery();
            rs.next();

            boolean exists = rs.getInt(1) > 0;

            if (exists) {
                // UPDATE
                String updateSql =
                    "UPDATE test SET point=?, class_num=? " +
                    "WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?";

                try (PreparedStatement st = con.prepareStatement(updateSql)) {

                    st.setInt(1, test.getPoint());
                    st.setString(2, test.getStudent().getClassNum()); // ★重要

                    st.setString(3, test.getStudent().getNo());
                    st.setString(4, test.getSubject().getCd());
                    st.setString(5, test.getSchool().getCd());
                    st.setInt(6, test.getNo());

                    count = st.executeUpdate();
                }

            } else {
                // INSERT
                String insertSql =
                    "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement st = con.prepareStatement(insertSql)) {

                    st.setString(1, test.getStudent().getNo());
                    st.setString(2, test.getSubject().getCd());
                    st.setString(3, test.getSchool().getCd());
                    st.setInt(4, test.getNo());
                    st.setInt(5, test.getPoint());
                    st.setString(6, test.getStudent().getClassNum()); // ★重要

                    count = st.executeUpdate();
                }
            }
        }

        return count > 0;
    }
}