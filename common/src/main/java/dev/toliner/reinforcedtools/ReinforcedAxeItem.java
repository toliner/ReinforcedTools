package dev.toliner.reinforcedtools;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public final class ReinforcedAxeItem extends AxeItem implements ReinforcedTool {
    private final ReinforcedToolMaterial material;

    public ReinforcedAxeItem(ReinforcedToolMaterial material, Item.Properties properties) {
        super(material.toolMaterial(), material.axeAttackDamageBaseline(), material.axeAttackSpeedBaseline(), properties);
        this.material = material;
    }

    @Override
    public ReinforcedToolMaterial reinforcedMaterial() {
        return material;
    }
}
