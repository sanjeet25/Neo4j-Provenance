package main.java.userStorage;

/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBWrapper {

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "testuser";
        String password = "test623";

        try {
            
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
        
            

        } finally {
            
            try {
                
                if (rs != null) {
                    rs.close();
                }
                
                if (st != null) {
                    st.close();
                }
                
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                
               
            }
        }
    }
}*/

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 

public class DBWrapper {
	
	private DBWrapper(){
		
	}
	
	private static DBWrapper ds = null;
	
	public static DBWrapper getDataStore(){
		if(ds == null) {
			ds = new DBWrapper();
	      }
	      return ds;
	}
	
    public void createNewDatabase(String fileName) {
 
        String url = "jdbc:sqlite:/Users/sanjeetphatak/Documents/sqlite/db/" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTable(){
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/sanjeetphatak/Documents/sqlite/db/test.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	user text PRIMARY KEY,\n"
                + "	file text NOT NULL,\n"
                + "	password text\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public static void insertData(String user, String file, String pass) {
        String sql = "INSERT INTO users(user, file, password) VALUES(?,?,?)";
        String url = "jdbc:sqlite:/Users/sanjeetphatak/Documents/sqlite/db/test.db";
        
        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, user);
        	pstmt.setString(2, file);
            pstmt.setString(3, pass);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static boolean queryTable(String user, String file, String password){
        String sql = "SELECT user, file, password FROM users";
        String url = "jdbc:sqlite:/Users/sanjeetphatak/Documents/sqlite/db/test.db";
        
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
            	String userName = rs.getString("user");
                String fileName = rs.getString("file");
                String pass = rs.getString("password");
                
                if(userName.equals(user) && file.equals(fileName) && password.equals(pass))
                	return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    }

}