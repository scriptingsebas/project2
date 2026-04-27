package dao;

/**
 * [Brief one-sentence description of what this class does.]
 *
 * @author Sebastian Guillen
 * @since 4/20/26
 * @version 0.1.0
 */
public class Item {

    private int id;
    private String name;
    private String type;
    private int effectValue;
    private String tier;

    public Item(int id, String name, String type, int effectValue, String tier) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.effectValue = effectValue;
        this.tier = tier;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getEffectValue() { return effectValue; }
    public String getTier() { return tier; }

    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setEffectValue(int effectValue) { this.effectValue = effectValue; }
    public void setTier(String tier) { this.tier = tier; }
}