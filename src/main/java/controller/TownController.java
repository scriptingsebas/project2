package controller;

import database.DatabaseManager;
import database.InventoryDAO;

/**
 * Handles town inventory, shop prices, and shop purchases.
 *
 * @author Sebastian Guillen
 * @since 4/29/26
 * @version 0.1.0
 */
public class TownController {
    private final DatabaseManager userDataManager;
    private final InventoryDAO inventoryDAO;
    private String currentUsername;

    public TownController(DatabaseManager userDataManager, InventoryDAO inventoryDAO) {
        this.userDataManager = userDataManager;
        this.inventoryDAO = inventoryDAO;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public int getCurrentBattlesWon() {
        if (currentUsername == null) {
            return 0;
        }

        try (java.sql.ResultSet inventory = inventoryDAO.getInventory(currentUsername)) {
            if (inventory.next()) {
                return inventory.getInt("battles_won");
            }
        } catch (Exception e) {
            System.err.println("Load battles won failed: " + e.getMessage());
        }

        return 0;
    }

    public String getInventoryText(String columnName, String defaultValue) {
        if (currentUsername == null) {
            return defaultValue;
        }

        try (java.sql.ResultSet inventory = inventoryDAO.getInventory(currentUsername)) {
            if (inventory.next()) {
                return inventory.getString(columnName);
            }
        } catch (Exception e) {
            System.err.println("Load inventory failed: " + e.getMessage());
        }

        return defaultValue;
    }

    public int getInventoryNumber(String columnName, int defaultValue) {
        if (currentUsername == null) {
            return defaultValue;
        }

        try (java.sql.ResultSet inventory = inventoryDAO.getInventory(currentUsername)) {
            if (inventory.next()) {
                return inventory.getInt(columnName);
            }
        } catch (Exception e) {
            System.err.println("Load inventory failed: " + e.getMessage());
        }

        return defaultValue;
    }

    public int getSwordPrice() {
        if (currentUsername == null) {
            return 2;
        }

        String insertSql = "INSERT OR IGNORE INTO shop(username) VALUES(?)";
        String selectSql = "SELECT sword_price FROM shop WHERE username = ?";

        try (java.sql.PreparedStatement insertStmt = userDataManager.getConnection().prepareStatement(insertSql)) {
            insertStmt.setString(1, currentUsername);
            insertStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Create shop row failed: " + e.getMessage());
        }

        try (java.sql.PreparedStatement selectStmt = userDataManager.getConnection().prepareStatement(selectSql)) {
            selectStmt.setString(1, currentUsername);
            java.sql.ResultSet result = selectStmt.executeQuery();
            if (result.next()) {
                return result.getInt("sword_price");
            }
        } catch (Exception e) {
            System.err.println("Load sword price failed: " + e.getMessage());
        }

        return 2;
    }

    public int getArmorPrice() {
        if (currentUsername == null) {
            return 1;
        }

        String insertSql = "INSERT OR IGNORE INTO shop(username) VALUES(?)";
        String selectSql = "SELECT armor_price FROM shop WHERE username = ?";

        try (java.sql.PreparedStatement insertStmt = userDataManager.getConnection().prepareStatement(insertSql)) {
            insertStmt.setString(1, currentUsername);
            insertStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Create shop row failed: " + e.getMessage());
        }

        try (java.sql.PreparedStatement selectStmt = userDataManager.getConnection().prepareStatement(selectSql)) {
            selectStmt.setString(1, currentUsername);
            java.sql.ResultSet result = selectStmt.executeQuery();
            if (result.next()) {
                return result.getInt("armor_price");
            }
        } catch (Exception e) {
            System.err.println("Load armor price failed: " + e.getMessage());
        }

        return 1;
    }

    public int getHealingPrice() {
        if (currentUsername == null) {
            return 1;
        }

        String insertSql = "INSERT OR IGNORE INTO shop(username) VALUES(?)";
        String selectSql = "SELECT healing_price FROM shop WHERE username = ?";

        try (java.sql.PreparedStatement insertStmt = userDataManager.getConnection().prepareStatement(insertSql)) {
            insertStmt.setString(1, currentUsername);
            insertStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Create shop row failed: " + e.getMessage());
        }

        try (java.sql.PreparedStatement selectStmt = userDataManager.getConnection().prepareStatement(selectSql)) {
            selectStmt.setString(1, currentUsername);
            java.sql.ResultSet result = selectStmt.executeQuery();
            if (result.next()) {
                return result.getInt("healing_price");
            }
        } catch (Exception e) {
            System.err.println("Load healing price failed: " + e.getMessage());
        }

        return 1;
    }

    public boolean buySword() {
        if (currentUsername == null) {
            return false;
        }

        String currentSword = getInventoryText("sword", "Bronze");
        if (currentSword.equals("Diamond")) {
            return false;
        }

        int swordPrice = getSwordPrice();
        int battlesWon = getCurrentBattlesWon();

        if (battlesWon < swordPrice) {
            return false;
        }

        String nextSword = currentSword.equals("Bronze") ? "Iron" : "Diamond";
        String inventorySql = "UPDATE inventory SET sword = ? WHERE username = ?";
        String shopSql = "UPDATE shop SET sword_price = sword_price + 2 WHERE username = ?";

        try (java.sql.PreparedStatement inventoryStmt = userDataManager.getConnection().prepareStatement(inventorySql);
             java.sql.PreparedStatement shopStmt = userDataManager.getConnection().prepareStatement(shopSql)) {
            inventoryStmt.setString(1, nextSword);
            inventoryStmt.setString(2, currentUsername);
            inventoryStmt.executeUpdate();

            shopStmt.setString(1, currentUsername);
            shopStmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.err.println("Buy sword failed: " + e.getMessage());
        }

        return false;
    }

    public boolean buyArmor() {
        if (currentUsername == null) {
            return false;
        }

        String currentArmor = getInventoryText("armor", "Bronze");
        if (currentArmor.equals("Diamond")) {
            return false;
        }

        int armorPrice = getArmorPrice();
        int battlesWon = getCurrentBattlesWon();

        if (battlesWon < armorPrice) {
            return false;
        }

        String nextArmor = currentArmor.equals("Bronze") ? "Iron" : "Diamond";
        String inventorySql = "UPDATE inventory SET armor = ? WHERE username = ?";
        String shopSql = "UPDATE shop SET armor_price = armor_price + 1 WHERE username = ?";

        try (java.sql.PreparedStatement inventoryStmt = userDataManager.getConnection().prepareStatement(inventorySql);
             java.sql.PreparedStatement shopStmt = userDataManager.getConnection().prepareStatement(shopSql)) {
            inventoryStmt.setString(1, nextArmor);
            inventoryStmt.setString(2, currentUsername);
            inventoryStmt.executeUpdate();

            shopStmt.setString(1, currentUsername);
            shopStmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.err.println("Buy armor failed: " + e.getMessage());
        }

        return false;
    }

    public String buyHealingPotion() {
        if (currentUsername == null) {
            return "";
        }

        int currentPotions = getInventoryNumber("healing_potions", 0);
        if (currentPotions >= 3) {
            return "Max 3 potions.";
        }

        int healingPrice = getHealingPrice();
        int battlesWon = getCurrentBattlesWon();

        if (battlesWon < healingPrice) {
            return "";
        }

        String inventorySql = "UPDATE inventory SET healing_potions = healing_potions + 1 WHERE username = ?";
        String shopSql = "UPDATE shop SET healing_price = healing_price + 1 WHERE username = ?";

        try (java.sql.PreparedStatement inventoryStmt = userDataManager.getConnection().prepareStatement(inventorySql);
             java.sql.PreparedStatement shopStmt = userDataManager.getConnection().prepareStatement(shopSql)) {
            inventoryStmt.setString(1, currentUsername);
            inventoryStmt.executeUpdate();

            shopStmt.setString(1, currentUsername);
            shopStmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Buy healing potion failed: " + e.getMessage());
        }

        return "";
    }

    public String itemColor(String itemName) {
        if (itemName.equals("Bronze")) {
            return "#CD7F32";
        }
        if (itemName.equals("Iron")) {
            return "white";
        }
        if (itemName.equals("Diamond")) {
            return "#00BFFF";
        }
        return "white";
    }

    public void addBattlesWon() {

    }
}
