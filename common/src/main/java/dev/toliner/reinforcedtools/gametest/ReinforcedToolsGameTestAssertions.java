package dev.toliner.reinforcedtools.gametest;

import dev.toliner.reinforcedtools.ReinforcedTool;
import dev.toliner.reinforcedtools.ReinforcedToolMaterial;
import dev.toliner.reinforcedtools.ReinforcedToolType;
import dev.toliner.reinforcedtools.ReinforcedTools;
import dev.toliner.reinforcedtools.RepairKitItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

/** Shared assertions used by each loader's GameTest registration. */
public final class ReinforcedToolsGameTestAssertions {
    private ReinforcedToolsGameTestAssertions() {
    }

    public static void verifyToolStats(GameTestHelper helper) {
        for (var material : ReinforcedToolMaterial.values()) {
            var expected = ExpectedStats.forMaterial(material);
            for (var type : ReinforcedToolType.values()) {
                Item item = item(helper, ReinforcedTools.toolId(material, type));
                ItemStack stack = new ItemStack(item);

                require(helper, item instanceof ReinforcedTool, "%s must be a reinforced tool", ReinforcedTools.toolId(material, type));
                require(helper, ((ReinforcedTool) item).reinforcedMaterial() == material,
                    "%s has the wrong material", ReinforcedTools.toolId(material, type));
                require(helper, stack.getMaxDamage() == expected.durability(),
                    "%s durability: expected %s, got %s", ReinforcedTools.toolId(material, type), expected.durability(), stack.getMaxDamage());
                require(helper, stack.get(DataComponents.ENCHANTABLE).value() == expected.enchantability(),
                    "%s enchantability: expected %s, got %s", ReinforcedTools.toolId(material, type), expected.enchantability(), stack.get(DataComponents.ENCHANTABLE).value());

                ItemAttributeModifiers modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
                double actualDamage = modifiers.compute(Attributes.ATTACK_DAMAGE, 0.0D, EquipmentSlot.MAINHAND);
                require(helper, Math.abs(actualDamage - expected.attackDamage(type)) < 0.001D,
                    "%s attack damage: expected %s, got %s", ReinforcedTools.toolId(material, type), expected.attackDamage(type), actualDamage);

                if (type != ReinforcedToolType.SWORD) {
                    Tool tool = stack.get(DataComponents.TOOL);
                    require(helper, tool != null, "%s is missing its tool component", ReinforcedTools.toolId(material, type));
                    float miningSpeed = tool.getMiningSpeed(suitableBlock(type));
                    require(helper, Math.abs(miningSpeed - expected.miningSpeed()) < 0.001F,
                        "%s mining speed: expected %s, got %s", ReinforcedTools.toolId(material, type), expected.miningSpeed(), miningSpeed);
                }

                require(helper, stack.has(DataComponents.DAMAGE_RESISTANT) == (material == ReinforcedToolMaterial.NETHERITE),
                    "%s fire resistance does not match its material", ReinforcedTools.toolId(material, type));
            }
        }
    }

    public static void verifyRecipes(GameTestHelper helper) {
        for (var material : ReinforcedToolMaterial.values()) {
            for (var type : ReinforcedToolType.values()) {
                if (material == ReinforcedToolMaterial.NETHERITE) {
                    requireRecipe(helper, ReinforcedTools.toolId(material, type) + "_from_vanilla_netherite");
                    requireRecipe(helper, ReinforcedTools.toolId(material, type) + "_from_reinforced_diamond");
                } else if (type == ReinforcedToolType.PICKAXE || type == ReinforcedToolType.AXE) {
                    requireRecipe(helper, ReinforcedTools.toolId(material, type) + "_top");
                    requireRecipe(helper, ReinforcedTools.toolId(material, type) + "_cross");
                } else {
                    requireRecipe(helper, ReinforcedTools.toolId(material, type));
                }
            }
            requireRecipe(helper, ReinforcedTools.repairKitId(material) + "_four");
            requireRecipe(helper, ReinforcedTools.repairKitId(material) + "_six_rows");
            requireRecipe(helper, ReinforcedTools.repairKitId(material) + "_six_cross");
            requireRecipe(helper, ReinforcedTools.repairKitId(material) + "_eight");
        }
    }

    public static void verifyRepairKits(GameTestHelper helper) {
        ItemStack tool = new ItemStack(item(helper, ReinforcedTools.toolId(ReinforcedToolMaterial.DIAMOND, ReinforcedToolType.PICKAXE)));
        tool.setDamageValue(1_000);
        ItemStack repairKit = new ItemStack(item(helper, ReinforcedTools.repairKitId(ReinforcedToolMaterial.DIAMOND)), 2);
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, tool);
        player.setItemInHand(InteractionHand.OFF_HAND, repairKit);

        InteractionResult result = repairKit.getItem().use(helper.getLevel(), player, InteractionHand.OFF_HAND);
        int repairAmount = Math.max(1, Math.round(tool.getMaxDamage() * 0.25F));
        require(helper, result == InteractionResult.SUCCESS_SERVER, "Matching repair kit did not report server success");
        require(helper, tool.getDamageValue() == 1_000 - repairAmount,
            "Repair kit restored %s durability instead of %s", 1_000 - tool.getDamageValue(), repairAmount);
        require(helper, repairKit.getCount() == 1, "Repair kit was not consumed exactly once");

        ItemStack wrongKit = new ItemStack(item(helper, ReinforcedTools.repairKitId(ReinforcedToolMaterial.IRON)));
        player.setItemInHand(InteractionHand.OFF_HAND, wrongKit);
        int damageBeforeWrongUse = tool.getDamageValue();
        InteractionResult wrongResult = wrongKit.getItem().use(helper.getLevel(), player, InteractionHand.OFF_HAND);
        require(helper, wrongResult == InteractionResult.PASS, "A mismatched repair kit must not be usable");
        require(helper, tool.getDamageValue() == damageBeforeWrongUse, "A mismatched repair kit modified the tool");
        require(helper, wrongKit.getCount() == 1, "A mismatched repair kit was consumed");
        require(helper, repairKit.getItem() instanceof RepairKitItem, "Repair kit item type is incorrect");
    }

    private static Item item(GameTestHelper helper, String path) {
        return BuiltInRegistries.ITEM.getOptional(ReinforcedTools.id(path))
            .orElseThrow(() -> helper.assertionException("Missing registered item: %s", path));
    }

    private static void requireRecipe(GameTestHelper helper, String path) {
        var key = ResourceKey.<net.minecraft.world.item.crafting.Recipe<?>>create(Registries.RECIPE, ReinforcedTools.id(path));
        require(helper, helper.getLevel().recipeAccess().byKey(key).isPresent(), "Missing generated recipe: %s", path);
    }

    private static net.minecraft.world.level.block.state.BlockState suitableBlock(ReinforcedToolType type) {
        return switch (type) {
            case PICKAXE -> Blocks.STONE.defaultBlockState();
            case AXE -> Blocks.OAK_LOG.defaultBlockState();
            case SHOVEL -> Blocks.DIRT.defaultBlockState();
            case HOE -> Blocks.HAY_BLOCK.defaultBlockState();
            case SWORD -> throw new IllegalArgumentException("Swords do not have a mining-speed assertion");
        };
    }

    private static void require(GameTestHelper helper, boolean condition, String message, Object... arguments) {
        if (!condition) {
            throw helper.assertionException(message, arguments);
        }
    }

    private record ExpectedStats(int durability, float miningSpeed, float damageBonus, int enchantability, float axeBaseline, float hoeBaseline) {
        private static ExpectedStats forMaterial(ReinforcedToolMaterial material) {
            return switch (material) {
                case WOOD -> new ExpectedStats(140, 3.0F, 0.0F, 18, 6.0F, 0.0F);
                case STONE -> new ExpectedStats(240, 4.5F, 1.0F, 8, 7.0F, -1.0F);
                case COPPER -> new ExpectedStats(320, 5.5F, 1.0F, 15, 7.0F, -1.0F);
                case IRON -> new ExpectedStats(700, 7.0F, 2.0F, 16, 6.0F, -2.0F);
                case DIAMOND -> new ExpectedStats(2500, 9.0F, 3.0F, 13, 5.0F, -3.0F);
                case NETHERITE -> new ExpectedStats(4000, 10.0F, 5.0F, 18, 5.0F, -4.0F);
                case GOLD -> new ExpectedStats(100, 14.0F, 0.0F, 25, 6.0F, 0.0F);
            };
        }

        private float attackDamage(ReinforcedToolType type) {
            return switch (type) {
                case SWORD -> 3.0F + damageBonus;
                case PICKAXE -> 1.0F + damageBonus;
                case AXE -> axeBaseline + damageBonus;
                case SHOVEL -> 1.5F + damageBonus;
                case HOE -> hoeBaseline + damageBonus;
            };
        }
    }
}
