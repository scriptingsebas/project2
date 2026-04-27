package database;

/**
 * This will help  us access the user information database
 *
 * @author Bill Ung
 * @since 4/20/26
 * @version 0.1.0
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final Connection connection;

    public UserDAO(DatabaseManager dbManager) {
        this.connection = dbManager.getConnection();
    }

    public boolean createUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            String inventorySql = "INSERT INTO inventory(username) VALUES(?)";
            try (PreparedStatement invStmt = connection.prepareStatement(inventorySql)) {
                invStmt.setString(1, username);
                invStmt.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            System.err.println("Create user failed: " + e.getMessage());
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }
}
