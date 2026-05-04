package org.turnbasedtitans.project2;

import java.util.Random;

public class EnemyController {
    private static Random RNG = new Random();
    private static final Enemies [] commonMobs = {
            new Enemies("Zombie", 125),
            new Enemies("Mummy", 150),
            new Enemies("Slime", 75),
            new Enemies("Skeleton", 100),
            new Enemies("Wyrmling", 175)
    };
    public Enemies enemyRandomizer() {
        int poolIndex = RNG.nextInt(commonMobs.length);
        Enemies randomMob = commonMobs[poolIndex];
        return new Enemies(randomMob.getEnemyName(), randomMob.getEnemyHP());
    }
}
