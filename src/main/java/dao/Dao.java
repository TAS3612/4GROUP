package dao;
 
import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
 
public class Dao {
 
    static DataSource ds;
 
    static {
        try {
            Context context = new InitialContext();
            ds = (DataSource) context.lookup("java:/comp/env/jdbc/test2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public Connection getConnection() throws Exception {
        // データベースへのコネクションを返却
        return ds.getConnection();
    }
}