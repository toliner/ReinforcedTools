package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool

/**
 * @author C6H2Cl2
 */
class ItemPaxel(material: ToolMaterial,private val level: Int): ItemTool(3.0f, material, emptySet<Block>()), IReinforcedTools {

    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.fortune, Enchantment.unbreaking, Enchantment.sharpness, Enchantment.looting)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(0.8, 0.8, 0.8, 0.8, 0.8).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}Paxel"
        setTextureName("$MOD_ID:${name.toLowerCase()}_paxel")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        maxDamage *= 4
    }

    override fun func_150893_a(p_150893_1_: ItemStack?, p_150893_2_: Block?): Float {
        return toolMaterial.efficiencyOnProperMaterial
    }

    override fun hitEntity(itemStack: ItemStack, target: EntityLivingBase?, player: EntityLivingBase?): Boolean {
        itemStack.damageItem(1, player)
        return true
    }

    override fun canHarvestBlock(block: Block?, itemStack: ItemStack?): Boolean = true

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("axe", "pickaxe", "shovel", "hoe", "sword", "paxel", "Axe", "Pickaxe", "Shovel", "Hoe", "Sword", "Paxel")
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