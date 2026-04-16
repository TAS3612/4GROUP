package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
 
public class SchoolDao extends Dao {
    /**
     * getメソッド 学校コードを指定して学校インスタンスを1件取得する
     * @param cd:String
     *        学校コード
     * @return 学校クラスのインスタンス 存在しない場合はnull
     * @throws Exception
     */
    public School get(String cd) throws Exception {
        // 学校インスタンスを初期化
        School school = new School();
        // データベースへのコネクションを確保
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        
        try {
            // SQL文をセット
            statement = connection.prepareStatement("select * from school where cd = ?");
            // 学校コードをバインド
            statement.setString(1, cd);
            // SQL実行
            ResultSet rSet = statement.executeQuery();
            
            if (rSet.next()) {
                // データが存在する場合
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            } else {
                // 存在しない場合
                school = null;
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
        return school;
    }
}