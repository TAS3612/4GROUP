package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDao extends Dao {

    /**
     * 学校コードに紐づく全ての科目を取得（filterを使用）
     */
    public List<Subject> filter(String schoolCd) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? ORDER BY CD ASC");
        st.setString(1, schoolCd);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject subject = new Subject();
            subject.setSchool(rs.getString("SCHOOL_CD")); // schoolフィールドへ
            subject.setCd(rs.getString("CD"));           // cdフィールドへ
            subject.setName(rs.getString("NAME"));       // nameフィールドへ
            list.add(subject);
        }

        st.close();
        con.close();
        return list;
    }

    /**
     * 科目コードと学校コードで1件取得（重複チェック用）
     */
    public Subject get(String cd, String schoolCd) throws Exception {
        Subject subject = null;
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "SELECT * FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?")) {
            st.setString(1, cd);
            st.setString(2, schoolCd);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    subject = new Subject();
                    subject.setSchool(rs.getString("SCHOOL_CD"));
                    subject.setCd(rs.getString("CD"));
                    subject.setName(rs.getString("NAME"));
                }
            }
        }
        return subject;
    }

    /**
     * 科目の新規登録
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 1. 既存データの確認（科目コードと学校コードの両方で検索）
            // さきほど作成した get(String, String) メソッドを呼び出します
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                // 2. 新規登録（INSERT）
                statement = connection.prepareStatement(
                    "insert into subject (school_cd, cd, name) values (?, ?, ?)"
                );
                statement.setString(1, subject.getSchool()); // 学校コード
                statement.setString(2, subject.getCd());     // 科目コード
                statement.setString(3, subject.getName());   // 科目名
            } else {
                // 3. 更新（UPDATE）
                // 科目コードと学校コードが一致するデータの「名前」を更新します
                statement = connection.prepareStatement(
                    "update subject set name=? where cd=? and school_cd=?"
                );
                statement.setString(1, subject.getName());   // 新しい科目名
                statement.setString(2, subject.getCd());     // どの科目を
                statement.setString(3, subject.getSchool()); // どの学校で
            }

            // 実行
            int count = statement.executeUpdate();

            return count > 0;

        } catch (Exception e) {
            throw e;
        } finally {
            // リソースの解放
            if (statement != null) {
                try { statement.close(); } catch (SQLException sqle) { throw sqle; }
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException sqle) { throw sqle; }
            }
        }
    }
    /**
     * 科目の削除
     */
    public boolean delete(String cd, String schoolCd) throws Exception {
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?")) {
            st.setString(1, cd);
            st.setString(2, schoolCd);
            int count = st.executeUpdate();
            return count > 0;
        }
    }
}