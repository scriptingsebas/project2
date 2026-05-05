package org.turnbasedtitans.project2.controller;
import org.turnbasedtitans.project2.SceneFactory;
import org.turnbasedtitans.project2.*;
import org.turnbasedtitans.project2.Enemies;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.turnbasedtitans.project2.database.InventoryDAO;

public class DungeonController {

    private Enemies dungeonEnemy;
    private RPGBattleSystem battleSystem;
    private Label battleLog;

    private Label playerHP;
    private Label enemyHP;

    private SceneFactory sceneFactory;
    private TownController townController;
    private Stage stage;

    private InventoryDAO inventoryDAO;
    private String currentUsername;

    public DungeonController (Label battleLog, Label playerHP, Label enemyHP, SceneFactory sceneFactory, TownController townController, String currentUsername, Stage stage, InventoryDAO inventoryDAO) {
        this.battleLog = battleLog;

        this.playerHP = playerHP;
        this.enemyHP = enemyHP;

        this.sceneFactory = sceneFactory;
        this.townController = townController;
        this.stage = stage;

        this.currentUsername = currentUsername;
        this.inventoryDAO = inventoryDAO;

        battleInitialization();
    }
    private void battleInitialization() {
        EnemyController enemyController = new EnemyController();
        this.dungeonEnemy = enemyController.enemyRandomizer();
        this.battleSystem = new RPGBattleSystem(dungeonEnemy, inventoryDAO, currentUsername);
        healthUpdater();
    }
    public void healthUpdater() {
        playerHP.setText("Player HP: " + battleSystem.getPlayerHP());
        enemyHP.setText(dungeonEnemy.getEnemyName() + " HP: " + battleSystem.getEnemyHP());
    }
    public void playerAttack() {
        int playerDMG = battleSystem.playerAttack();
        if(battleSystem.enemyDefeatedTF()) {
            townController.addBattlesWon();
            stage.setScene(sceneFactory.create(SceneType.TOWN, stage));
            return;
        }
        int enemyDMG = battleSystem.enemyAttack();
        healthUpdater();

        battleLog.setText("You dealt " + playerDMG + "!\n" + dungeonEnemy.getEnemyName() + "dealt " + enemyDMG + "!");

        if(battleSystem.playerDefeatedTF()) {
            stage.setScene(sceneFactory.create(SceneType.TOWN, stage));
        }
    }
    public void playerDefend() {
        battleSystem.activateDefend();
        int enemyDMG = battleSystem.enemyAttack();

        healthUpdater();
        battleLog.setText("You defend yourself against the oncoming attack...\n" + dungeonEnemy.getEnemyName() +  "dealt " + enemyDMG + "!");

        if(battleSystem.playerDefeatedTF()) {
            stage.setScene(sceneFactory.create(SceneType.TOWN, stage));
        }
    }
    public void playerEscape() {
        int rngRoll = battleSystem.escapeChance();
        if (battleSystem.escapeSuccess(rngRoll)) {
            stage.setScene(sceneFactory.create(SceneType.TOWN, stage));
            return;
        }
        int enemyDMG = battleSystem.enemyAttack();
        healthUpdater();

        battleLog.setText("You failed to run away!\n" + dungeonEnemy.getEnemyName() + "dealt " + enemyDMG + "!");

        if(battleSystem.playerDefeatedTF()) {
            stage.setScene(sceneFactory.create(SceneType.TOWN, stage));
        }
    }

    public Enemies getDungeonEnemy() {
        return dungeonEnemy;
    }
}
