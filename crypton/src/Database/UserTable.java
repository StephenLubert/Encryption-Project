package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import models.User;

public class UserTable extends Database {

    private int accountID = -1;

    public UserTable(String db) {
        super();
    }

    public int authenticate(String username, String password) {
        String sql = "SELECT password, accountID FROM Users WHERE username = \"" + username + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    this.accountID = rs.getInt("accountID");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return getAccountID();
    }

    public User getUserInfo(int accountID) {
        String fname = null,
                lname = null,
                email = null,
                username = null,
                password = null,
                regDate = null;
        int pin = 0;

        String sql = "SELECT username, password, pin, email, fname, lname, regDate FROM Users WHERE accountID = \"" + accountID + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                username = rs.getString("username");
                password = rs.getString("password");
                pin = rs.getInt("pin");
                email = rs.getString("email");
                fname = rs.getString("fname");
                lname = rs.getString("lname");
                regDate = rs.getString("regDate");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return new User(username, password, pin, email, fname, lname);
    }

    public void setUserPassword(int accountID, String newPassword) {
        String sql = "UPDATE Users SET password = \"" + newPassword + "\" WHERE accountID = \"" + accountID + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void setUserEmail(int accountID, String newEmail) {
        String sql = "UPDATE Users SET email = \"" + newEmail + "\" WHERE accountID = \"" + accountID + "\"";

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addNewUser(User newUser) {
        String sql = "INSERT INTO Users (username, password, pin, email, fname, lname, regDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUser.getUsername());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setInt(3, newUser.getPin());
            pstmt.setString(4, newUser.getEmail());
            pstmt.setString(5, newUser.getFname());
            pstmt.setString(6, newUser.getLname());
            pstmt.setString(7, newUser.getRegDate());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String getEntry(String entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
