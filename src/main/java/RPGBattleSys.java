import java.util.Random;

public class RPGBattleSys {
    //PRESET(S)
    private final int defaultDamage = 10;
    private final Random RNG = new Random();
    private Enemies dungeonEnemy;

    //CONSTRUCTOR
    public RPGBattleSys(Enemies dungeonEnemy) {
        this.dungeonEnemy = dungeonEnemy;
    }

    public int attackEnemy() {
        int diceDamage = RNG.nextInt(20) + 1;
        int finalDamage = defaultDamage + diceDamage;
        dungeonEnemy.dmgTaken(finalDamage);
        return finalDamage;
    }
}
