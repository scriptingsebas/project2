import java.util.Random;

public class RPGBattleSys {
    //PRESET(S)
    private int playerHP = 100;
    private final int defaultDamage = 10;
    private boolean defendState = false;
    private final Random RNG = new Random();
    private Enemies dungeonEnemy;

    //CONSTRUCTOR
    public RPGBattleSys(Enemies dungeonEnemy) {
        this.dungeonEnemy = dungeonEnemy;
    }
    //ATTACK FORMULA FOR WHEN THE PLAYER PRESSES "ATTACK"
    public int attackEnemy() {
        int diceDamage = RNG.nextInt(20) + 1;
        int finalDamage = defaultDamage + diceDamage;
        dungeonEnemy.dmgTaken(finalDamage);
        return finalDamage;
    }
    //DEFEND FUNCTIONALITY - SETS "defendState" TO TRUE
    public void activateDefend() {
        defendState = true;
    }
    //ATTACK FORMULA FOR WHEN THE ENEMY "ATTACKS" THE PLAYER
    public int attackUser() {
        int enemyDealt = RNG.nextInt(defaultDamage) + 1;
        if(defendState) {
            enemyDealt /= 2;
            defendState = false;
        }
        playerHP -= enemyDealt;
        return enemyDealt;
    }
    //ESCAPE FUNCTIONALITY FOR WHEN THE PLAYER PRESSES "ESCAPE"
    public int escapeRNG() {
        return RNG.nextInt(6) + 1;
    }
    //ESCAPE SUCCESS
    public boolean escapeYayNay(int diceRoll) {
        return diceRoll >= 3;
    }

}
