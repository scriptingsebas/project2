package dao;

/**
 *  Represents a battle record in the game.
 *
 * In theory, the battle will consist of a different scene, with two buttons: attack and run. with attack it will use the sword the user currently has and do damange, then the npc will attack back a random percentage based on difficulty, and it will repeat until one person dies. if the user chooses to run they have a 90% sucess rate.
 *
 * @author Sebastian Guillen
 * @since 4/20/26
 * @version 0.1.0
 */
public class Battle {
    private int battleId;
    private String npcName;
    private String result;
    private int reward;
    private String difficulty;

    public Battle(int battleId, String npcName, String result, int reward, String difficulty) {
        this.battleId = battleId;
        this.npcName = npcName;
        this.result = result;
        this.reward = reward;
        this.difficulty = difficulty;
    }

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

    public String getNpcName() {
        return npcName;
    }

    public void setNpcName(String npcName) {
        this.npcName = npcName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}