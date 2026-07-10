package dev.toliner.reinforcedtools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShovelItem;

public final class ReinforcedShovelItem extends ShovelItem implements ReinforcedTool {
    private final ReinforcedToolMaterial material;

    public ReinforcedShovelItem(ReinforcedToolMaterial material, Item.Properties properties) {
        super(material.toolMaterial(), 1.5F, -3.0F, properties);
        this.material = material;
    }

    @Override
    public ReinforcedToolMaterial reinforcedMaterial() {
        return material;
    }
}
