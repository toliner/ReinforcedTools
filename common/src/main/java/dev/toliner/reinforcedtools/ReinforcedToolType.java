package dev.toliner.reinforcedtools;

/** The five vanilla tool types supported by this mod. */
public enum ReinforcedToolType {
    SWORD("sword", "Sword"),
    PICKAXE("pickaxe", "Pickaxe"),
    AXE("axe", "Axe"),
    SHOVEL("shovel", "Shovel"),
    HOE("hoe", "Hoe");

    private final String id;
    private final String displayName;

    ReinforcedToolType(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }
}
