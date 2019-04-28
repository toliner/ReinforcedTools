package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class ToolPaxel(material: ToolMaterial,name:String) :ItemTool(4.0f,-2.4f,material,null),IReinforcedTools{

    init {
        unlocalizedName = name.toLowerCase() + "Paxel"
        registryName = ResourceLocation(ReinforcedToolsCore.Domain,"${name.toLowerCase()}Paxel")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel:Int): ItemStack {
        val level = (enchantLevel.toFloat() / 5f * 3f).toInt()
        itemStack.addEnchantment(Enchantments.EFFICIENCY,level)
        itemStack.addEnchantment(Enchantments.FORTUNE,level)
        itemStack.addEnchantment(Enchantments.SHARPNESS,level)
        itemStack.addEnchantment(Enchantments.LOOTING,level)
        itemStack.addEnchantment(Enchantments.UNBREAKING,level)
        return itemStack
    }


    override fun getStrVsBlock(itemStack: ItemStack?, state: IBlockState) = efficiencyOnProperMaterial

    override fun hitEntity(itemStack: ItemStack, target: EntityLivingBase?, player: EntityLivingBase?): Boolean {
        itemStack.damageItem(1,player)
        return true
    }

    override fun canHarvestBlock(state: IBlockState?, itemStack: ItemStack?): Boolean = true

    override fun getToolType(): EnumToolType = EnumToolType.PAXEL

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("axe","pickaxe","shovel","hoe","sword","paxel","Axe","Pickaxe","Shovel","Hoe","Sword","Paxel")
    }
}