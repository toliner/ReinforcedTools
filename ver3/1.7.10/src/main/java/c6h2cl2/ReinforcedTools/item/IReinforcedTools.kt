package c6h2cl2.ReinforcedTools.item

import net.minecraft.enchantment.Enchantment
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */
interface IReinforcedTools {
    fun getEnchanted(): ItemStack
    fun addEnchant(enchantment: Enchantment)
    fun removeEnchant(enchantment: Enchantment)
    fun setEnchantLevel(vararg levels: Double)
    fun setEnchantLevel(enchantment: Enchantment, level: Double)
    fun getEnchantLevelBase(): Int
}