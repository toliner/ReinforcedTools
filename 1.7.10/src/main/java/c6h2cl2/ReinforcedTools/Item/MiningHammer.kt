package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool

/**
 * @author C6H2Cl2
 */
class MiningHammer(material: Item.ToolMaterial, name: String) : ItemTool(-material.damageVsEntity + 1, material, null), IReinforcedTools {
    init {
        unlocalizedName = "${name.toLowerCase()}MiningHammer"
        setTextureName("${ReinforcedToolsCore.Domain}:${name.toLowerCase()}MiningHammer")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        maxDamage *= 6
        efficiencyOnProperMaterial *= 1.8f
        for (toolClass in getToolClasses(null)) {
            setHarvestLevel(toolClass, material.harvestLevel + 1)
        }
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel: Int): ItemStack {
        val level = (enchantLevel.toFloat() * 1.2f).toInt()
        itemStack.addEnchantment(Enchantment.efficiency, level)
        itemStack.addEnchantment(Enchantment.fortune, level)
        itemStack.addEnchantment(Enchantment.unbreaking, level)
        return itemStack
    }

    override fun getToolType() = EnumToolType.MININGHAMMER

    override fun getToolClasses(stack: ItemStack?) = mutableSetOf("pickaxe", "shovel", "hammer", "mininghammer", "Pickaxe", "Shovel", "Hammer", "MiningHammer", "miningHammer")

    override fun func_150893_a(itemStack: ItemStack?, block: Block): Float {
        return if (itemStack != null && ReinforcedToolsCore.isToolEffective(itemStack, block, 0)) {
            efficiencyOnProperMaterial
        } else {
            1f
        }
    }

    override fun canHarvestBlock(block: Block, itemStack: ItemStack?): Boolean {
        return (block.getHarvestLevel(0) <= toolMaterial.harvestLevel && getToolClasses(itemStack).contains(block.getHarvestTool(0)))
    }

    override fun func_150897_b(block: Block): Boolean {
        return canHarvestBlock(block, null)
    }

    override fun getDigSpeed(itemStack: ItemStack?, block: Block, meta: Int): Float {
        return if (itemStack != null && ReinforcedToolsCore.isToolEffective(itemStack, block, 0)) {
            efficiencyOnProperMaterial
        } else {
            1f
        }
    }

}