package dev.toliner.reinforcedtools;

import net.minecraft.world.item.Item;

/** Sword and pickaxe implementation, whose behavior is supplied by Item components. */
public class ReinforcedToolItem extends Item implements ReinforcedTool {
    private final ReinforcedToolMaterial material;

    public ReinforcedToolItem(ReinforcedToolMaterial material, Item.Properties properties) {
        super(properties);
        this.material = material;
    }

    @Override
    public ReinforcedToolMaterial reinforcedMaterial() {
        return material;
    }
}
