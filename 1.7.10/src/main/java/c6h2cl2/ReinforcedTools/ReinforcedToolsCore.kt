package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.client.ClientProxy
import c6h2cl2.ReinforcedTools.common.CommonProxy
import c6h2cl2.ReinforcedTools.event.FOVEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.ModMetadata
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import java.io.File

/**
 * @author C6H2Cl2
 */

@Mod(modid = ReinforcedToolsCore.Domain,name = ReinforcedToolsCore.ModID, version = ReinforcedToolsCore.Version ,useMetadata = true, dependencies = "required-after:YukariLib@[1.2.0,)")
class ReinforcedToolsCore {
    companion object {
        const val ModID = "ReinforcedTools"
        const val Domain = "reinforcedtools"
        const val Version = "2.0.2"
        @Mod.Metadata
        var meta: ModMetadata? = null
        @SidedProxy(clientSide = "c6h2cl2.ReinforcedTools.client.ClientProxy", serverSide = "c6h2cl2.ReinforcedTools.common.CommonProxy")
        var proxy: CommonProxy? = null
        var hardRecipe = false
            private set

        fun isHardRecipe() = hardRecipe
        fun isToolEffective(stack: ItemStack, block: Block, metadata: Int): Boolean {
            return stack.item.getToolClasses(stack).any { ("pickaxe" == it && (block === Blocks.redstone_ore || block === Blocks.lit_redstone_ore || block === Blocks.obsidian)) || block.isToolEffective(it, metadata) }
        }
    }

    @Mod.EventHandler
    fun preinit(event: FMLPreInitializationEvent) {
        loadMeta()
        getConfig()
        ReinforcedToolsRegistry.preInit(event)
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        ReinforcedToolsRegistry.init(event)
        if (event.side.isClient) {
            if (proxy is ClientProxy) {
                val clientProxy = proxy as ClientProxy
                clientProxy.registerRender()
            }
            MinecraftForge.EVENT_BUS.register(FOVEvent())
        }

    }

    fun getConfig() {
        val proxy = proxy as CommonProxy
        val cfg = Configuration(File(proxy.getDir(), "config/ReinforcedTools.cfg"))
        cfg.load()
        hardRecipe = cfg.getBoolean("EnableHardRecipe", "Recipes", false, "Enable hard recipe.")
        cfg.save()
    }

    fun loadMeta() {
        val meta = meta as ModMetadata
        meta.modId = Domain
        meta.name = ModID
        meta.version = Version
        meta.authorList.add("C6H2Cl2")
        meta.description = "Add some tools better than vanilla."
        meta.autogenerated = false
    }
}