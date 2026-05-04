package org.turnbasedtitans.project2;//DESCRIPTION: "org.turnbasedtitans.project2.Enemies" is the handler/assigner of enemy names/health values.

public class Enemies {
    //PRESETS
    private String enemyName;
    private int enemyHP;

    //CONSTRUCTOR
    public Enemies(String enemyName, int enemyHP) {
        this.enemyName = enemyName;
        this.enemyHP = enemyHP;
    }
    //GETTER AND SETTER(S)
    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getEnemyHP() {
        return enemyHP;
    }

    public void setEnemyHP(int enemyHP) {
        this.enemyHP = enemyHP;
    }
    public void dmgTaken (int dmgDealt) {
        enemyHP -= dmgDealt;
    }
    public boolean enemyDefeated() {
        return enemyHP <= 0;
    }
}
