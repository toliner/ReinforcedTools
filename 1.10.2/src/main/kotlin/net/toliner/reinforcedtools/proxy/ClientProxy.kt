package net.toliner.reinforcedtools.proxy

import net.minecraftforge.fml.relauncher.Side
import net.toliner.reinforcedtools.CommonProxy

/**
 * @author kojin15.
 */
class ClientProxy : CommonProxy() {
    override val side: Side
        get() = Side.CLIENT
}