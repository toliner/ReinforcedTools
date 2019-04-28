package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraftforge.common.ForgeHooks

/**
 * @author C6H2Cl2
 */
class ItemBattleAxe(material: ToolMaterial, private val level: Int) : ItemTool(8.0f, material, null), IReinforcedTools {

    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.unbreaking, Enchantment.sharpness, Enchantment.looting)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(0.2, 1.2 ,1.4, 1.0).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}BattleAxe"
        setTextureName("$MOD_ID:${name.toLowerCase()}_battle_axe")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        efficiencyOnProperMaterial *= 0.7f
        maxDamage = material.maxUses * 3
        for (toolClass in getToolClasses(ItemStack(this))) {
            setHarvestLevel(toolClass, material.harvestLevel)
        }
    }

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("axe", "sword", "battleaxe", "Axe", "Sword", "BattleAxe", "battleAxe")
    }

    override fun hitEntity(itemStack: ItemStack, targetEntity: EntityLivingBase?, player: EntityLivingBase?): Boolean {
        itemStack.damageItem(1, player)
        return true
    }

    override fun func_150893_a(itemStack: ItemStack?, block: Block): Float {
        return if (itemStack != null && ForgeHooks.isToolEffective(itemStack, block, 0)) {
            efficiencyOnProperMaterial
        } else {
            0f
        }
    }

    override fun canHarvestBlock(block: Block, itemStack: ItemStack?): Boolean {
        return block.getHarvestLevel(0) <= toolMaterial.harvestLevel && getToolClasses(itemStack).contains(block.getHarvestTool(0))
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