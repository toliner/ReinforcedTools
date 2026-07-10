package dev.toliner.reinforcedtools;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/** The material-specific stats used by reinforced tools. */
public enum ReinforcedToolMaterial {
    WOOD("wood", "Wood", "wooden", 140, 3.0F, 0.0F, 18, BlockTags.INCORRECT_FOR_WOODEN_TOOL, ItemTags.WOODEN_TOOL_MATERIALS,
        "oak_planks", 6.0F, -3.2F, 0.0F, -3.0F),
    STONE("stone", "Stone", "stone", 240, 4.5F, 1.0F, 8, BlockTags.INCORRECT_FOR_STONE_TOOL, ItemTags.STONE_TOOL_MATERIALS,
        "cobblestone", 7.0F, -3.2F, -1.0F, -2.0F),
    COPPER("copper", "Copper", "copper", 320, 5.5F, 1.0F, 15, BlockTags.INCORRECT_FOR_COPPER_TOOL, ItemTags.COPPER_TOOL_MATERIALS,
        "copper_ingot", 7.0F, -3.2F, -1.0F, -2.0F),
    IRON("iron", "Iron", "iron", 700, 7.0F, 2.0F, 16, BlockTags.INCORRECT_FOR_IRON_TOOL, ItemTags.IRON_TOOL_MATERIALS,
        "iron_ingot", 6.0F, -3.1F, -2.0F, -1.0F),
    DIAMOND("diamond", "Diamond", "diamond", 2500, 9.0F, 3.0F, 13, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, ItemTags.DIAMOND_TOOL_MATERIALS,
        "diamond", 5.0F, -3.0F, -3.0F, 0.0F),
    NETHERITE("netherite", "Netherite", "netherite", 4000, 10.0F, 5.0F, 18, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, ItemTags.NETHERITE_TOOL_MATERIALS,
        "netherite_ingot", 5.0F, -3.0F, -4.0F, 0.0F),
    GOLD("gold", "Gold", "golden", 100, 14.0F, 0.0F, 25, BlockTags.INCORRECT_FOR_GOLD_TOOL, ItemTags.GOLD_TOOL_MATERIALS,
        "gold_ingot", 6.0F, -3.0F, 0.0F, -3.0F);

    private final String id;
    private final String displayName;
    private final String vanillaPrefix;
    private final int durability;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final TagKey<Item> repairItems;
    private final String modelItem;
    private final float axeAttackDamageBaseline;
    private final float axeAttackSpeedBaseline;
    private final float hoeAttackDamageBaseline;
    private final float hoeAttackSpeedBaseline;
    private final ToolMaterial toolMaterial;

    ReinforcedToolMaterial(
        String id,
        String displayName,
        String vanillaPrefix,
        int durability,
        float speed,
        float attackDamageBonus,
        int enchantmentValue,
        TagKey<net.minecraft.world.level.block.Block> incorrectBlocksForDrops,
        TagKey<Item> repairItems,
        String modelItem,
        float axeAttackDamageBaseline,
        float axeAttackSpeedBaseline,
        float hoeAttackDamageBaseline,
        float hoeAttackSpeedBaseline
    ) {
        this.id = id;
        this.displayName = displayName;
        this.vanillaPrefix = vanillaPrefix;
        this.durability = durability;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairItems = repairItems;
        this.modelItem = modelItem;
        this.axeAttackDamageBaseline = axeAttackDamageBaseline;
        this.axeAttackSpeedBaseline = axeAttackSpeedBaseline;
        this.hoeAttackDamageBaseline = hoeAttackDamageBaseline;
        this.hoeAttackSpeedBaseline = hoeAttackSpeedBaseline;
        this.toolMaterial = new ToolMaterial(
            incorrectBlocksForDrops,
            durability,
            speed,
            attackDamageBonus,
            enchantmentValue,
            repairItems
        );
    }

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public String vanillaPrefix() {
        return vanillaPrefix;
    }

    public String modelItem() {
        return modelItem;
    }

    public TagKey<Item> repairItems() {
        return repairItems;
    }

    public ToolMaterial toolMaterial() {
        return toolMaterial;
    }

    public float axeAttackDamageBaseline() {
        return axeAttackDamageBaseline;
    }

    public float axeAttackSpeedBaseline() {
        return axeAttackSpeedBaseline;
    }

    public float hoeAttackDamageBaseline() {
        return hoeAttackDamageBaseline;
    }

    public float hoeAttackSpeedBaseline() {
        return hoeAttackSpeedBaseline;
    }
}
