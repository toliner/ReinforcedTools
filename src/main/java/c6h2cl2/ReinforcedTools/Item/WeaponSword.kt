package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword

/**
 * @author C6H2Cl2
 */
class WeaponSword(material: Item.ToolMaterial, name: String) : ItemSword(material), IReinforcedTools {
    init {
        unlocalizedName = "reinforced${name}Sword"
        setTextureName(ReinforcedToolsCore.Domain + ":reinforced" + name + "Sword")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel: Int): ItemStack {
        itemStack.addEnchantment(Enchantment.sharpness, enchantLevel)
        itemStack.addEnchantment(Enchantment.looting, enchantLevel)
        itemStack.addEnchantment(Enchantment.unbreaking, enchantLevel)
        return itemStack
    }

    override fun getToolType() = EnumToolType.SWORD
}