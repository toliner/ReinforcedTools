package net.toliner.reinforcedtools

import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.relauncher.Side

/**
 * @author kojin15.
 */
abstract class CommonProxy {
    abstract val side: Side

    open fun preInit() {
        ReinforcedTools.Items.reinforcedAxe.forEach {
            ForgeRegistries.ITEMS.register(it)
        }
        ReinforcedTools.Items.reinforcedHoe.forEach {
            ForgeRegistries.ITEMS.register(it)
        }
        ReinforcedTools.Items.reinforcedPickaxe.forEach {
            ForgeRegistries.ITEMS.register(it)
        }
        ReinforcedTools.Items.reinforcedShovel.forEach {
            ForgeRegistries.ITEMS.register(it)
        }
    }

    open fun init() = Unit
    open fun postInit() = Unit
}