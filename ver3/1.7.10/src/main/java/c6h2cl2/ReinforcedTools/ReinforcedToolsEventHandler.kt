package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.item.ItemExcavationHammer
import c6h2cl2.ReinforcedTools.item.ItemExcavationShovel
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.item.Item
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action.LEFT_CLICK_BLOCK

/**
 * @author C6H2Cl2
 */
class ReinforcedToolsEventHandler {
    @SubscribeEvent
    fun playerOnClckBlock(event: PlayerInteractEvent) {
        if (event.action != LEFT_CLICK_BLOCK) {
            return
        }
        val item: Item? = event.entityPlayer.heldItem?.item
        if (item is ItemExcavationHammer || item is ItemExcavationShovel) {
            val stack = event.entityPlayer.heldItem
            if (!stack.hasTagCompound()) {
                stack.tagCompound = NBTTagCompound()
            }
            stack.tagCompound.setInteger("break_side", event.face)
        }
    }
}