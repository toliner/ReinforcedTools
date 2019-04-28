package c6h2cl2.ReinforcedTools.common

import net.minecraft.entity.player.EntityPlayer
import java.io.File

/**
 * @author C6H2Cl2
 */
open class CommonProxy {
    open fun getDir(): File = File(".")
    open fun getPlayerEntityInstance(): EntityPlayer? = null
}