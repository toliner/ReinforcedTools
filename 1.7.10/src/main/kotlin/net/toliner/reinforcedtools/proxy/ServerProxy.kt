package net.toliner.reinforcedtools.proxy

import cpw.mods.fml.relauncher.Side
import net.toliner.reinforcedtools.CommonProxy

/**
 * @author kojin15.
 */
class ServerProxy : CommonProxy() {
    override val side: Side
        get() = Side.SERVER
}