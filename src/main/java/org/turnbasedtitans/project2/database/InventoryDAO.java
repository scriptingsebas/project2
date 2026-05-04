package org.turnbasedtitans.project2.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles all org.turnbasedtitans.project2.database operations related to a user's inventory.
 *
 * @author Sebastian Guillen
 * @since 4/27/26
 * @version 0.1.0
 */
public class InventoryDAO {

    private final Connection connection;

    public InventoryDAO(Connection connection) {
        this.connection = connection;
    }

    // Get inventory for a user by username
    public ResultSet getInventory(String username) throws SQLException {
        String sql = "SELECT * FROM inventory WHERE username = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        return stmt.executeQuery();
    }

    // Update sword
    public void updateSword(String username, String sword) throws SQLException {
        String sql = "UPDATE inventory SET sword = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sword);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    // Update armor
    public void updateArmor(String username, String armor) throws SQLException {
        String sql = "UPDATE inventory SET armor = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, armor);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    // Update healing potions
    public void updatePotions(String username, int amount) throws SQLException {
        String sql = "UPDATE inventory SET healing_potions = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    // Update battles won
    public void updateBattlesWon(String username, int battles) throws SQLException {
        String sql = "UPDATE inventory SET battles_won = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, battles);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }

    // Update health
    public void updateHealth(String username, int health) throws SQLException {
        String sql = "UPDATE inventory SET health = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, health);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }
}
