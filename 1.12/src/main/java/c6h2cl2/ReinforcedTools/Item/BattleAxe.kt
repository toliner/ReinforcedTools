package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.EnumToolType
import c6h2cl2.ReinforcedTools.IReinforcedTools
import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.init.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.util.ResourceLocation

/**
 * @author C6H2Cl2
 */
class BattleAxe(material: ToolMaterial,name:String) :ItemTool(8.0f,-1.2f,material,null),IReinforcedTools{
    init {
        unlocalizedName = "${name.toLowerCase()}BattleAxe"
        registryName = ResourceLocation(ReinforcedToolsCore.Domain,"${name}BattleAxe")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        efficiencyOnProperMaterial *= 0.7f
        maxDamage = material.maxUses * 3
        for (toolClass in getToolClasses(ItemStack(this))){
            setHarvestLevel(toolClass,material.harvestLevel)
        }
    }

    override fun getEnchanted(itemStack: ItemStack, enchantLevel: Int): ItemStack {
        val level1 = (enchantLevel.toFloat()/ 5f * 1f).toInt()
        val level3 = (enchantLevel.toFloat()/ 5f * 3f).toInt()
        val level7 = (enchantLevel.toFloat()/ 5f * 7f).toInt()
        itemStack.addEnchantment(Enchantments.SHARPNESS,level7)
        itemStack.addEnchantment(Enchantments.EFFICIENCY,level1)
        itemStack.addEnchantment(Enchantments.LOOTING,level3)
        itemStack.addEnchantment(Enchantments.UNBREAKING,enchantLevel)
        return itemStack
    }

    override fun getToolType(): EnumToolType = EnumToolType.BATTLEAXE

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("axe","sword","battleaxe","Axe","Sword","BattleAxe","battleAxe")
    }

    override fun hitEntity(itemStack: ItemStack, targetEntity: EntityLivingBase?, player: EntityLivingBase?): Boolean {
        itemStack.damageItem(1,player)
        return true
    }

    override fun getStrVsBlock(itemStack: ItemStack?, state: IBlockState): Float {
        return if(itemStack != null && ReinforcedToolsCore.isToolEffective(itemStack,state.block,state)){
            efficiencyOnProperMaterial
        }else{
            0f
        }
    }

    override fun canHarvestBlock(state: IBlockState?, itemStack: ItemStack?): Boolean {
        if(state == null || itemStack == null) return false
        val block = state.block
        return block.getHarvestLevel(state) <= toolMaterial.harvestLevel && getToolClasses(itemStack).contains(block.getHarvestTool(state))
    }
}