package dev.toliner.reinforcedtools;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;

public final class ReinforcedHoeItem extends HoeItem implements ReinforcedTool {
    private final ReinforcedToolMaterial material;

    public ReinforcedHoeItem(ReinforcedToolMaterial material, Item.Properties properties) {
        super(material.toolMaterial(), material.hoeAttackDamageBaseline(), material.hoeAttackSpeedBaseline(), properties);
        this.material = material;
    }

    @Override
    public ReinforcedToolMaterial reinforcedMaterial() {
        return material;
    }
}
