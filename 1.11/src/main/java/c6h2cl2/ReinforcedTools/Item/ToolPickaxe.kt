package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.init.Enchantments
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class ToolPickaxe(material:ToolMaterial,name:String) :ItemPickaxe(material), IReinforcedTools{
    init {
        unlocalizedName = "reinforced" + name + "Pickaxe"
        registryName = ResourceLocation(ReinforcedToolsCore.Domain,"reinforced${name}Pickaxe")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel:Int): ItemStack {
        itemStack.addEnchantment(Enchantments.EFFICIENCY, enchantLevel)
        itemStack.addEnchantment(Enchantments.FORTUNE, enchantLevel)
        itemStack.addEnchantment(Enchantments.UNBREAKING, enchantLevel)
        return itemStack
    }

    override fun getToolType(): EnumToolType = EnumToolType.PICKAXE
}