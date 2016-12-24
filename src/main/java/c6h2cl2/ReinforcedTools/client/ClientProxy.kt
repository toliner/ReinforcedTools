package c6h2cl2.ReinforcedTools.client

import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import c6h2cl2.ReinforcedTools.common.CommonProxy
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.client.MinecraftForgeClient
import java.io.File

/**
 * @author C6H2Cl2
 */
class ClientProxy :CommonProxy(){
    override fun getDir(): File = Minecraft.getMinecraft().mcDataDir
    override fun getPlayerEntityInstance(): EntityPlayer? = Minecraft.getMinecraft().thePlayer
    @SideOnly(Side.CLIENT)
    fun registerRender(){
        MinecraftForgeClient.registerItemRenderer(ReinforcedToolsRegistry.shortBow,BowRenderer())
        MinecraftForgeClient.registerItemRenderer(ReinforcedToolsRegistry.compositeBow,BowRenderer())
    }
}