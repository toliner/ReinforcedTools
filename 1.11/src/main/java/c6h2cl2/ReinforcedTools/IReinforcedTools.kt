package c6h2cl2.ReinforcedTools

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */
interface IReinforcedTools {
    fun getToolType():EnumToolType
    fun getEnchanted(itemStack: ItemStack, enchantLevel:Int): ItemStack
    fun getEnchanted(item: Item, enchantLevel: Int):ItemStack = getEnchanted(ItemStack(item), enchantLevel)
}