package org.turnbasedtitans.project2;

import java.util.Random;

public class EnemyController {
    private static Random RNG = new Random();
    private static final Enemies [] commonMobs = {
            new Enemies("LightBandit", 100, "/org/turnbasedtitans/project2/characters/LightBandit-2.png"),
            new Enemies("HeavyBandit", 150, "/org/turnbasedtitans/project2/characters/HeavyBandit.png"),
    };
    public Enemies enemyRandomizer() {
        int poolIndex = RNG.nextInt(commonMobs.length);
        Enemies randomMob = commonMobs[poolIndex];
        return new Enemies(randomMob.getEnemyName(), randomMob.getEnemyHP(), randomMob.getSpritePath());
    }

    public int enemyStartingHP(Enemies enemy) {
        return enemy.getEnemyHP();
    }
}
