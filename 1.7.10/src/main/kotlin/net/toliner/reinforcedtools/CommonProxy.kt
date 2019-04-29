package net.toliner.reinforcedtools

import cpw.mods.fml.relauncher.Side

/**
 * @author kojin15.
 */
abstract class CommonProxy {
    abstract val side: Side

    open fun preInit() = Unit
    open fun init() = Unit
    open fun postInit() = Unit
}