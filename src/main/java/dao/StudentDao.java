package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
 
public class StudentDao extends Dao {
 
	private String baseSql = "select * from student where school_cd=? ";
 
	public Student get(String no) throws Exception {

	    Student student = null;

	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rSet = null;

	    try {
	        statement = connection.prepareStatement(
	            "select * from student where no = ?"
	        );

	        statement.setString(1, no);

	        rSet = statement.executeQuery();

	        if (rSet.next()) {
	            student = new Student();

	            student.setNo(rSet.getString("no"));
	            student.setName(rSet.getString("name"));
	            student.setEntYear(rSet.getInt("ent_year"));
	            student.setClassNum(rSet.getString("class_num"));
	            student.setAttend(rSet.getBoolean("is_attend"));

	            SchoolDao schoolDao = new SchoolDao();
	            student.setSchool(
	                schoolDao.get(rSet.getString("school_cd"))
	            );
	        }

	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return student;
	}
 
    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
        List<Student> list = new ArrayList<>();
        try {
            // リザルトセットを全件走査
            // 引数の名前に合わせて「rSet」に統一
            while (rSet.next()) {
                // 学生インスタンスを初期化
                Student student = new Student();
                // 学生インスタンスに検索結果をセット
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(school);
                // リストに追加
                list.add(student);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
 
        return list;
    }
 
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        // リストを初期化
        List<Student> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        // SQL文の条件
        String condition = " and ent_year=? and class_num=?";
        // SQL文のソート
        String order = " order by no asc";
 
        // SQL文の在学フラグ条件
        String conditionIsAttend = "";
        // 在学フラグがtrueの場合
        if (isAttend) {
            conditionIsAttend = " and is_attend=true";
        }
 
        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd());
            // プリペアードステートメントに入学年度をバインド
            statement.setInt(2, entYear);
            // プリペアードステートメントにクラス番号をバインド
            statement.setString(3, classNum);
            // プライベートステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            list = postFilter(rSet, school);
        } catch (Exception e) {
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // コネクションを閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
 
        return list;
    }
 
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        // リストを初期化
        List<Student> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        
        // 【修正ポイント1】class_numを含まない条件にする
        String condition = " and ent_year=? ";
        // SQL文のソート
        String order = " order by no asc";
 
        // SQL文の在学フラグ条件
        String conditionIsAttend = "";
        if (isAttend) {
            conditionIsAttend = " and is_attend=true";
        }
 
        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd());
            // プリペアードステートメントに入学年度をバインド
            statement.setInt(2, entYear);
            
            // 【修正ポイント2】ここにあった statement.setString(3, classNum) を削除
 
            // プリペアードステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            list = postFilter(rSet, school);
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
 
        return list;
    }
    public List<Student> filter(School school, boolean isAttend) throws Exception {
        List<Student> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        // SQL文の条件
        String order = " order by no asc";
 
        // SQL文の在学フラグ
        String conditionIsAttend = "";
        // 在学フラグがtrueの場合
        if (isAttend) {
            conditionIsAttend = "and is_attend=true";
        }
 
        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd());
            // プリペアードステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            list = postFilter(rSet, school);
        } catch (Exception e) {
            throw e;
        } finally {
            // プリペアードステートメントを閉じる
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            // コネクションを閉じる
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
 
        return list;
    }
 
    public boolean save(Student student) throws Exception {

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            // 既存データ確認
            Student old = get(student.getNo());

            if (old == null) {
                // 新規登録
                statement = connection.prepareStatement(
                    "insert into student " +
                    "(no, name, ent_year, class_num, is_attend, school_cd) " +
                    "values (?, ?, ?, ?, ?, ?)"
                );

                statement.setString(1, student.getNo());
                statement.setString(2, student.getName());
                statement.setInt(3, student.getEntYear());
                statement.setString(4, student.getClassNum());
                statement.setBoolean(5, student.isAttend());
                statement.setString(6, student.getSchool().getCd());

            } else {
                // 更新
                statement = connection.prepareStatement(
                    "update student set " +
                    "name=?, " +
                    "class_num=?, " +
                    "is_attend=? " +
                    "where no=?"
                );

                statement.setString(1, student.getName());
                statement.setString(2, student.getClassNum());
                statement.setBoolean(3, student.isAttend());
                statement.setString(4, student.getNo());
            }

            int count = statement.executeUpdate();

            return count > 0;

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }
}
 