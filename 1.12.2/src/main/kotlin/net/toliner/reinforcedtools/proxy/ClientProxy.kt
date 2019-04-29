package net.toliner.reinforcedtools.proxy

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.Side
import net.toliner.reinforcedtools.CommonProxy
import net.toliner.reinforcedtools.ReinforcedTools

/**
 * @author kojin15.
 */
class ClientProxy : CommonProxy() {
    override val side: Side
        get() = Side.CLIENT

    override fun preInit() {
        super.preInit()

        val materials = arrayOf("wooden", "stone", "iron", "gold", "diamond")
        ReinforcedTools.Items.reinforcedAxe.forEachIndexed { i, item ->
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    ModelResourceLocation(ResourceLocation(ReinforcedTools.MOD_ID, "reinforced_axe"), materials[i]))
        }
        ReinforcedTools.Items.reinforcedHoe.forEachIndexed { i, item ->
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    ModelResourceLocation(ResourceLocation(ReinforcedTools.MOD_ID, "reinforced_hoe"), materials[i]))
        }
        ReinforcedTools.Items.reinforcedPickaxe.forEachIndexed { i, item ->
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    ModelResourceLocation(ResourceLocation(ReinforcedTools.MOD_ID, "reinforced_pickaxe"), materials[i]))
        }
        ReinforcedTools.Items.reinforcedShovel.forEachIndexed { i, item ->
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    ModelResourceLocation(ResourceLocation(ReinforcedTools.MOD_ID, "reinforced_shovel"), materials[i]))
        }
    }
}