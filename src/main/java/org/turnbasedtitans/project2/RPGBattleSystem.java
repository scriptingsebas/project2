package org.turnbasedtitans.project2;

import org.turnbasedtitans.project2.database.InventoryDAO;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;

//The "org.turnbasedtitans.project2.RPGBattleSystem" Class is the handler/functionality implementer of the player's buttons.
//It also serves to manage states.

public class RPGBattleSystem {
    //HP PRESET(S):
    private int playerHP;
    private int enemyHP;
    private InventoryDAO inventoryDAO;
    private String username;
    private String sword = "Bronze";
    private String armor = "Bronze";

    //ATTACK PRESET(S):
    private final int baseDMG = 5;
    private final Random RNG = new Random();

    //DEFEND PRESET(S):
    private boolean defendState = false;

    //CONSTRUCTOR:
    public RPGBattleSystem(Enemies dungeonEnemy) {
        //CONSTRUCTOR
        playerHP = 100;
        enemyHP = dungeonEnemy.getEnemyHP();
    }

    public RPGBattleSystem(Enemies dungeonEnemy, InventoryDAO inventoryDAO, String username) {
        //CONSTRUCTOR WITH DATABASE HEALTH
        this.inventoryDAO = inventoryDAO;
        this.username = username;
        loadInventoryStats();
        enemyHP = dungeonEnemy.getEnemyHP();
    }

    private void loadInventoryStats() {
        try (ResultSet inventory = inventoryDAO.getInventory(username)) {
            if (inventory.next()) {
                playerHP = inventory.getInt("health");
                sword = inventory.getString("sword");
                armor = inventory.getString("armor");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Could not load inventory stats: " + e.getMessage());
        }
        playerHP = 100;
    }

    private void savePlayerHP() {
        if (inventoryDAO == null || username == null) {
            return;
        }

        try {
            inventoryDAO.updateHealth(username, playerHP);
        } catch (SQLException e) {
            System.out.println("Could not save player health: " + e.getMessage());
        }
    }

//    Sword:
//    Bronze: 0 (default)
//    Iron: +20% damage boost
//    Diamond: +50%
//
//    Armor:
//    Bronze: 0 (default)
//    Iron: +20% protection
//    Diamond: +50 protection

    private int applySwordBoost(int damage) {
        if ("Diamond".equalsIgnoreCase(sword)) {
            return (int) Math.round(damage * 1.5);
        }
        if ("Iron".equalsIgnoreCase(sword)) {
            return (int) Math.round(damage * 1.2);
        }
        return damage;
    }

    private int applyArmorProtection(int damage) {
        if ("Diamond".equalsIgnoreCase(armor)) {
            return (int) Math.round(damage * 0.5);
        }
        if ("Iron".equalsIgnoreCase(armor)) {
            return (int) Math.round(damage * 0.8);
        }
        return damage;
    }



    //PLAYER HEALTH MANAGEMENT SECTION:


    public int getPlayerHP() {
        return playerHP;
    }

    public void setPlayerHP(int playerHP) {
        this.playerHP = Math.max(0, Math.min(100, playerHP));
        savePlayerHP();
    }

    public void ApplyDamagePlayer(int damageDealt) {
        //Reduce the player's HP by the damage dealt
        playerHP -= damageDealt;
        //Minimum value of HP is 0; cannot be negative
        if(playerHP < 0) {
            playerHP = 0;
        }
        savePlayerHP();
    }
    public boolean playerDefeatedTF() {
        //Player is defeated upon HP hitting/reaching 0
        return playerHP <= 0;
    }

    //ENEMY HEALTH MANAGEMENT SECTION:

    public int getEnemyHP() {
        return enemyHP;
    }
    public void ApplyDamageEnemy(int damageDealt) {
        //Reduce the enemy's HP by the damage dealt
        enemyHP -= damageDealt;
        //Minimum value of HP is 0; cannot be negative
        if (enemyHP < 0) {
            enemyHP = 0;
        }
    }
    public boolean enemyDefeatedTF() {
        return enemyHP <= 0;
    }

    //DEFEND MANAGEMENT SECTION:
    public void activateDefend() {
        defendState = true;
    }

    //PLAYER ATTACK MANAGEMENT SECTION:
    public int playerAttack() {
        int rollDMG = RNG.nextInt(10) + 1;
        int totalDMG = applySwordBoost(baseDMG + rollDMG);
        ApplyDamageEnemy(totalDMG);
        return totalDMG;
    }

    //ENEMY ATTACK MANAGEMENT SECTION:
    public int enemyAttack() {
        int rollDMG = RNG.nextInt(5) + 1;
        int totalDMG = baseDMG + rollDMG;
        if (defendState) {
            totalDMG /= 2;
            defendState = false;
        }
        totalDMG = applyArmorProtection(totalDMG);
        ApplyDamagePlayer(totalDMG);
        return totalDMG;
    }
    //ESCAPE MANAGEMENT SECTION:
    public int escapeChance() {
        return RNG.nextInt(6) + 1;
    }
    public boolean escapeSuccess(int rngRoll) {
        return rngRoll >= 3;
    }
}
