package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Account Database will store all information like password and  username.
 *
 * @author Bill Ung
 * @since 4/20/26
 * @version 0.1.0
 */
public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:Account.sqlite";

    private static Connection connecter;

    public Connection getConnection() {
        return connecter;
    }

    public DatabaseManager(){
        try {
            connecter = DriverManager.getConnection(URL) ;
            System.out.println ("Database connected.") ;
            makeDatabase();
        } catch (SQLException e) {
            System.err.println ("Connection failed : " + e.getMessage());
        }
    }

    public void close(){
        try {
            if (connecter != null && !connecter.isClosed())
                connecter.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void makeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE,
                password TEXT
            );
        """;

        try (Statement stmt = connecter.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println ("makeDatabase failed : " + e.getMessage()) ;
        }
    }
}