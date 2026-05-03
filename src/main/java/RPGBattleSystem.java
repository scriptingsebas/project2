import java.util.Random;

//The "RPGBattleSystem" Class is the handler/functionality implementer of the player's buttons.
//It also serves to manage states.

public class RPGBattleSystem {
    //HP PRESET(S):
    private int playerHP;
    private int enemyHP;

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

    //PLAYER HEALTH MANAGEMENT SECTION:

    public int getPlayerHP() {
        return playerHP;
    }

    public void ApplyDamagePlayer(int damageDealt) {
        //Reduce the player's HP by the damage dealt
        playerHP -= damageDealt;
        //Minimum value of HP is 0; cannot be negative
        if(playerHP < 0) {
            playerHP = 0;
        }
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
        int totalDMG = baseDMG + rollDMG;
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
