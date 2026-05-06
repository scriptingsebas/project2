package org.turnbasedtitans.project2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleSystemTest {
    Enemies JUnitEnemy;
    RPGBattleSystem battleSystem;

    @BeforeEach
    void setUp() {
        JUnitEnemy = new Enemies("Test Dummy", 100, "/org/turnbasedtitans/project2/characters/LightBandit-2.png");
        battleSystem = new RPGBattleSystem(JUnitEnemy);
    }

    @Test
    void activateDefend() {
        battleSystem.activateDefend();
        int enemyDMG = battleSystem.enemyAttack();
        assertTrue(enemyDMG >= 3 && enemyDMG <= 5, "DMG value: between 3 and 5 | actual: " + enemyDMG);
    }

    @Test
    void playerAttack() {
        int playerDMG = battleSystem.playerAttack();
        assertTrue(playerDMG >= 6 &&playerDMG <= 15, "DMG values: between 6 and 15 | actual: " + playerDMG);
    }

    @Test
    void enemyAttack() {

    }
}