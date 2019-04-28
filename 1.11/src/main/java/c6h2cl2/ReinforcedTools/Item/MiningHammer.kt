package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class MiningHammer(material: Item.ToolMaterial, name: String) : ItemTool(-material.damageVsEntity + 1, -10f, material, null), IReinforcedTools {
    init {
        unlocalizedName = "${name.toLowerCase()}MiningHammer"
        registryName = ResourceLocation(ReinforcedToolsCore.Domain, "${name.toLowerCase()}MiningHammer")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        maxDamage *= 6
        efficiencyOnProperMaterial *= 1.8f
        for (toolClass in getToolClasses(null)) {
            setHarvestLevel(toolClass, material.harvestLevel + 1)
        }
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel: Int): ItemStack {
        val level = (enchantLevel.toFloat() * 1.2f).toInt()
        itemStack.addEnchantment(Enchantments.EFFICIENCY, level)
        itemStack.addEnchantment(Enchantments.FORTUNE, level)
        itemStack.addEnchantment(Enchantments.UNBREAKING, level)
        return itemStack
    }

    override fun getToolType() = EnumToolType.MININGHAMMER

    override fun getToolClasses(stack: ItemStack?) = mutableSetOf("pickaxe", "shovel", "hammer", "mininghammer", "Pickaxe", "Shovel", "Hammer", "MiningHammer", "miningHammer")

    override fun getStrVsBlock(itemStack: ItemStack?, state: IBlockState): Float {
        return if (itemStack != null && ReinforcedToolsCore.isToolEffective(itemStack, state.block, state)) {
            efficiencyOnProperMaterial
        } else {
            1f
        }
    }

    override fun canHarvestBlock(state: IBlockState?, itemStack: ItemStack?): Boolean {
        if (state == null || itemStack == null) return false
        val block = state.block
        return block.getHarvestLevel(state) <= toolMaterial.harvestLevel && getToolClasses(itemStack).contains(block.getHarvestTool(state))
    }
}