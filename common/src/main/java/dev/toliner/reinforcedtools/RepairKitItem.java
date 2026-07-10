package dev.toliner.reinforcedtools;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Repairs a matching reinforced tool when used from the off-hand. */
public final class RepairKitItem extends Item {
    private final ReinforcedToolMaterial material;

    public RepairKitItem(ReinforcedToolMaterial material, Item.Properties properties) {
        super(properties);
        this.material = material;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (hand != InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }

        ItemStack toolStack = player.getMainHandItem();
        if (!(toolStack.getItem() instanceof ReinforcedTool reinforcedTool)
            || reinforcedTool.reinforcedMaterial() != material
            || !toolStack.isDamaged()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            int repairAmount = Math.max(1, Math.round(toolStack.getMaxDamage() * 0.25F));
            toolStack.setDamageValue(toolStack.getDamageValue() - repairAmount);
            if (!player.getAbilities().instabuild) {
                player.getItemInHand(hand).shrink(1);
            }
        }

        return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
    }
}
