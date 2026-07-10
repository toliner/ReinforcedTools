package dev.toliner.reinforcedtools;

import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.balm.world.item.BalmItemRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.EnumMap;
import java.util.Map;

/** Common entry point shared by the Fabric and NeoForge loaders. */
public final class ReinforcedTools {
    public static final String MOD_ID = "reinforced_tools";
    private static final Map<ReinforcedToolMaterial, Map<ReinforcedToolType, BalmItemRegistration>> TOOLS = new EnumMap<>(ReinforcedToolMaterial.class);
    private static final Map<ReinforcedToolMaterial, BalmItemRegistration> REPAIR_KITS = new EnumMap<>(ReinforcedToolMaterial.class);

    private ReinforcedTools() {
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void initialize(BalmRegistrars registrars) {
        registrars.items(items -> {
            for (var material : ReinforcedToolMaterial.values()) {
                var materialTools = new EnumMap<ReinforcedToolType, BalmItemRegistration>(ReinforcedToolType.class);
                for (var type : ReinforcedToolType.values()) {
                    var registration = items.register(toolId(material, type), properties -> createTool(material, type, properties));
                    materialTools.put(type, registration);
                }
                TOOLS.put(material, materialTools);

                var repairKit = items.register(
                    repairKitId(material),
                    properties -> new RepairKitItem(material, properties.stacksTo(16))
                );
                REPAIR_KITS.put(material, repairKit);
            }
        });

        registrars.creativeModeTabs(tabs -> tabs.register("tools", builder -> builder
            .title(Component.translatable("itemGroup.reinforced_tools.tools"))
            .icon(() -> new ItemStack(itemLike(ReinforcedToolMaterial.DIAMOND, ReinforcedToolType.PICKAXE)))
            .displayItems((parameters, output) -> {
                for (var material : ReinforcedToolMaterial.values()) {
                    for (var type : ReinforcedToolType.values()) {
                        output.accept(itemLike(material, type));
                    }
                    output.accept(REPAIR_KITS.get(material).asItemLike());
                }
            })
        ));
    }

    private static Item createTool(ReinforcedToolMaterial material, ReinforcedToolType type, Item.Properties properties) {
        if (material == ReinforcedToolMaterial.NETHERITE) {
            properties.fireResistant();
        }

        return switch (type) {
            case SWORD -> new ReinforcedToolItem(material, properties.sword(material.toolMaterial(), 3.0F, -2.4F));
            case PICKAXE -> new ReinforcedToolItem(material, properties.pickaxe(material.toolMaterial(), 1.0F, -2.8F));
            case AXE -> new ReinforcedAxeItem(material, properties);
            case SHOVEL -> new ReinforcedShovelItem(material, properties);
            case HOE -> new ReinforcedHoeItem(material, properties);
        };
    }

    public static String toolId(ReinforcedToolMaterial material, ReinforcedToolType type) {
        return "reinforced_" + material.id() + "_" + type.id();
    }

    public static String repairKitId(ReinforcedToolMaterial material) {
        return "reinforced_" + material.id() + "_repair_kit";
    }

    private static Item itemLike(ReinforcedToolMaterial material, ReinforcedToolType type) {
        return TOOLS.get(material).get(type).asItemLike().asItem();
    }
}
