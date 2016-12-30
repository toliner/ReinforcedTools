package c6h2cl2.ReinforcedTools.event

import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraftforge.client.event.FOVUpdateEvent

/**
 * @author C6H2Cl2
 */
@SideOnly(Side.CLIENT)
class FOVEvent {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun updateFOV(event: FOVUpdateEvent) {
        val player = event.entity
        val item = player.itemInUse
        if (item?.item == ReinforcedToolsRegistry.compositeBow) {
            var f = player.itemInUseDuration / 20.0f
            f = if (f > 1.0f) 1.0f else f * f
            event.newfov *= 1.0f - f * 0.2f
        } else if (item?.item == ReinforcedToolsRegistry.longBow) {
            var f = player.itemInUseDuration / 100.0f
            f = if (f > 1.0f) 1.0f else f * f
            event.newfov *= 1.0f - f * 0.6f
        }
    }
}