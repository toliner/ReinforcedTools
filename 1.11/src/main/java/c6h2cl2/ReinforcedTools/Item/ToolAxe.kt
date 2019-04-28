package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.init.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemAxe
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class ToolAxe(material:Item.ToolMaterial,name:String) :ItemAxe(material,material.damageVsEntity+3,-3.0f),IReinforcedTools {
    init {
        unlocalizedName = "reinforced" + name + "Axe"
        registryName = ResourceLocation(ReinforcedToolsCore.Domain,"reinforced${name}Axe")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel:Int):ItemStack{
        itemStack.addEnchantment(Enchantments.EFFICIENCY, enchantLevel)
        itemStack.addEnchantment(Enchantments.FORTUNE, enchantLevel)
        itemStack.addEnchantment(Enchantments.UNBREAKING, enchantLevel)
        return itemStack
    }

    override fun getToolType(): EnumToolType = EnumToolType.AXE
}