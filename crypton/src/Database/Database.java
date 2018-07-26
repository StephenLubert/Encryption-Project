package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

abstract public class Database {
    private static String URL = "jdbc:sqlite:Crypton.db";
    
    public Database() {
        
    }

    protected Connection connectToDatabase() {
        System.out.println("connect called.");
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return conn;
    }
    
    protected Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println("4");
            System.out.println(ex.getMessage());
        }
        
        return conn;
    }
    
    abstract public String getEntry(String entry);
}