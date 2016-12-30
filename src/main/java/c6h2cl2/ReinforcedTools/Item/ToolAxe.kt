package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Item
import net.minecraft.item.ItemAxe
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */
class ToolAxe(material: Item.ToolMaterial, name: String) : ItemAxe(material), IReinforcedTools {
    init {
        unlocalizedName = "reinforced" + name + "Axe"
        setTextureName("${ReinforcedToolsCore.Domain}:reinforced${name}Axe")
        //setTextureName(ReinforcedToolsCore.Domain+":reinforced"+name+"Axe")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel: Int): ItemStack {
        itemStack.addEnchantment(Enchantment.efficiency, enchantLevel)
        itemStack.addEnchantment(Enchantment.fortune, enchantLevel)
        itemStack.addEnchantment(Enchantment.unbreaking, enchantLevel)
        return itemStack
    }

    override fun getToolType(): EnumToolType = EnumToolType.AXE
}