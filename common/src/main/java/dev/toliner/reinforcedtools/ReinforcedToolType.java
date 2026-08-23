package dev.toliner.reinforcedtools;

/** The five vanilla tool types supported by this mod. */
public enum ReinforcedToolType {
    SWORD("sword", "Sword", "剣"),
    PICKAXE("pickaxe", "Pickaxe", "ツルハシ"),
    AXE("axe", "Axe", "斧"),
    SHOVEL("shovel", "Shovel", "シャベル"),
    HOE("hoe", "Hoe", "クワ");

    private final String id;
    private final String displayName;
    private final String japaneseDisplayName;

    ReinforcedToolType(String id, String displayName, String japaneseDisplayName) {
        this.id = id;
        this.displayName = displayName;
        this.japaneseDisplayName = japaneseDisplayName;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public String japaneseDisplayName() {
        return japaneseDisplayName;
    }
}
