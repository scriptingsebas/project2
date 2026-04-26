package database;
/**
 * Manages database connection and initialization.
 *
 * @author Sebastian Guillen
 * @since 4/20/26
 * @version 0.1.0
 */
import java.sql.*;
public class DatabaseManager {
    // " jdbc : sqlite :" tells JDBC which driver to use .
    // The path after it is the database file location .
    private static final String DB_URL = "jdbc:sqlite:app.db";
    private Connection connection;
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected.");
            createTables();
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
    private void createTables() {

        String userTable = """
            CREATE TABLE IF NOT EXISTS user_stats (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                account_id INTEGER NOT NULL UNIQUE,
                health INTEGER NOT NULL DEFAULT 100,
                battles_won INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY (account_id) REFERENCES account(id)
            )
            """;

        String inventoryTable = """
            CREATE TABLE IF NOT EXISTS inventory (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                account_id INTEGER NOT NULL UNIQUE,
                sword TEXT NOT NULL DEFAULT 'Bronze',
                armor TEXT NOT NULL DEFAULT 'Bronze',
                healing_potions INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY (account_id) REFERENCES account(id)
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(userTable);
            stmt.execute(inventoryTable);
        } catch (SQLException e) {
            System.err.println("createTables failed: " + e.getMessage());
        }
    }
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Close failed: " + e.getMessage());
        }
    }
}