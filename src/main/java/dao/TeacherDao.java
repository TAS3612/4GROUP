package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;
 
public class TeacherDao extends Dao {
   
    public Teacher get(String id) throws Exception {
        // 学校インスタンスを初期化
        Teacher teacher = new Teacher();
        // データベースへのコネクションを確保
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        
        try {
            // SQL文をセット
            statement = connection.prepareStatement("select * from teacher where id = ?");
            // 学校コードをバインド
            statement.setString(1, id);
            // SQL実行
            ResultSet rSet = statement.executeQuery();
            
            if (rSet.next()) {
                // データが存在する場合
                teacher.setId(rSet.getString("id"));
                teacher.setPassword(rSet.getString("password"));
            } else {
                // 存在しない場合
                teacher = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // ステートメントを閉じる
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
        return teacher;
    }
    public Teacher login(String id, String password) throws Exception {
        Teacher teacher = null;
        
        // 1. 先生を検索
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement("select * from teacher where ID=? and password=?")) {
            
            st.setString(1, id);
            st.setString(2, password);
            
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    teacher = new Teacher();
                    teacher.setId(rs.getString("id"));
                    teacher.setName(rs.getString("name"));
                    teacher.setPassword(rs.getString("password"));
                    
                    // 2. 所属学校コードを取得
                    String schoolCd = rs.getString("school_cd");
                    
                    // 3. SchoolDaoを使って学校のフルデータを取得し、Teacherにセット
                    SchoolDao sDao = new SchoolDao();
                    teacher.setSchool(sDao.get(schoolCd)); }

			st.close();
			con.close();
			return teacher;
            }
         }
     }
 }
