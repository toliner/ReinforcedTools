package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import c6h2cl2.ReinforcedTools.isToolEffective
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool

/**
 * @author C6H2Cl2
 */
class ItemMiningHammer(material: ToolMaterial, private val level: Int) : ItemTool(1 - material.damageVsEntity, material, null), IReinforcedTools {

    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.fortune, Enchantment.unbreaking)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(1.2, 1.2, 1.2).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}MiningHammer"
        setTextureName("$MOD_ID:${name.toLowerCase()}_mining_hammer")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        maxDamage *= 6
        efficiencyOnProperMaterial *= 1.8f
        for (toolClass in getToolClasses(null)) {
            setHarvestLevel(toolClass, material.harvestLevel + 1)
        }
    }

    override fun func_150893_a(itemStack: ItemStack?, block: Block): Float {
        return if (itemStack != null && isToolEffective(itemStack, block, 0)) {
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
        return if (itemStack != null && isToolEffective(itemStack, block, 0)) {
            efficiencyOnProperMaterial
        } else {
            1f
        }
    }

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("pickaxe", "shovel", "Pickaxe", "Shovel", "mininghammer", "MiningHammer")
    }

    override fun getEnchanted(): ItemStack {
        val itemStack = ItemStack(this)
        enchantList.forEachIndexed { index, enchant ->
            itemStack.addEnchantment(enchant, (level * levelList[index]).toInt())
        }
        return itemStack
    }

    override fun addEnchant(enchantment: Enchantment) {
        enchantList.add(enchantment)
    }

    override fun removeEnchant(enchantment: Enchantment) {
        levelList.removeAt(enchantList.indexOf(enchantment))
        enchantList.remove(enchantment)
    }

    override fun setEnchantLevel(vararg levels: Double) {
        levelList = levels.toMutableList()
    }

    override fun setEnchantLevel(enchantment: Enchantment, level: Double) {
        levelList[enchantList.indexOf(enchantment)] = level
    }

    override fun getEnchantLevelBase() = level
}