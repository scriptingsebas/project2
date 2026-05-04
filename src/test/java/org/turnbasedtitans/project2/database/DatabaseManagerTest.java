package org.turnbasedtitans.project2.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the database connection and table creation.
 *
 * @author Sebastian Guillen
 * @since 5/3/26
 * @version 0.1.0
 */
class DatabaseManagerTest {
    private DatabaseManager databaseManager;

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(Path.of("Account.sqlite"));
        databaseManager = new DatabaseManager();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (databaseManager != null) {
            databaseManager.close();
        }
        Files.deleteIfExists(Path.of("Account.sqlite"));
    }

    @Test
    @DisplayName("Database connects successfully")
    void getConnection() throws SQLException {
        Connection connection = databaseManager.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    @DisplayName("Database closes successfully")
    void close() throws SQLException {
        Connection connection = databaseManager.getConnection();

        databaseManager.close();
        databaseManager = null;

        assertTrue(connection.isClosed());
    }

    @Test
    @DisplayName("Database creates users, inventory, and shop tables")
    void makeDatabase() throws SQLException {
        assertTrue(tableExists("users"));
        assertTrue(tableExists("inventory"));
        assertTrue(tableExists("shop"));
    }

    private boolean tableExists(String tableName) throws SQLException {
        try (ResultSet tables = databaseManager.getConnection()
                .getMetaData()
                .getTables(null, null, tableName, null)) {
            return tables.next();
        }
    }
}