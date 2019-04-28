package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemSpade
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */
class ItemReinforcedShovel(material: ToolMaterial, private val level: Int) : ItemSpade(material), IReinforcedTools {
    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.fortune, Enchantment.unbreaking)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(1.0, 1.0, 1.0).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "reinforced${name}Shovel"
        setTextureName("${MOD_ID.toLowerCase()}.reinforced_${name.toLowerCase()}_shovel")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
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