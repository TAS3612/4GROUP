package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    // ベースSQL
	private static final String baseSql =
		    "SELECT s.ent_year, s.no AS student_no, s.name AS student_name, s.class_num, " +
		    "       t.no, t.point " +
		    "FROM student s " +
		    "LEFT JOIN test t ON s.no = t.student_no " +
		    "                  AND t.subject_cd = ? " +
		    "WHERE s.ent_year = ? " +
		    "AND s.class_num = ? " +
		    "AND s.school_cd = ? " +
		    "ORDER BY s.no, t.no";

    // メイン処理
	public List<TestListSubject> filter(int entYear, String classNum, String subjectCd, School school) throws Exception {

        List<TestListSubject> list = new ArrayList<>();

        Connection con = getConnection();
        PreparedStatement st = con.prepareStatement(baseSql);

        st.setString(1, subjectCd.trim());  
        st.setInt(2, entYear);
        st.setString(3, classNum);
        st.setString(4, school.getCd());

        ResultSet rs = st.executeQuery();

        list = postFilter(rs);

        st.close();
        con.close();

        return list;
    }
    private List<TestListSubject> postFilter(ResultSet rs) throws Exception {

        List<TestListSubject> list = new ArrayList<>();

        String currentStudentNo = null;
        TestListSubject bean = null;

        while (rs.next()) {

            String studentNo = rs.getString("student_no");

            // 学生が変わったら新しくBean作る
            if (!studentNo.equals(currentStudentNo)) {

                bean = new TestListSubject();

                bean.setEntYear(rs.getInt("ent_year"));
                bean.setStudentNo(studentNo);
                bean.setStudentName(rs.getString("student_name"));
                bean.setClassNum(rs.getString("class_num"));
                bean.setPoints(new HashMap<>());

                list.add(bean);

                currentStudentNo = studentNo;
            }

            // 回数と点数をMapに格納
            int no = rs.getInt("no");
            int point = rs.getInt("point");

            if (!rs.wasNull()) {
                bean.putPoint(no, point);
            }
        }

        return list;
    }
}