package net.toliner.reinforcedtools.item

import net.minecraft.item.ItemStack

/**
 * @author kojin15.
 */
interface IReinforcedTool {
    fun getEnchantedItem(level: Int): ItemStack
}