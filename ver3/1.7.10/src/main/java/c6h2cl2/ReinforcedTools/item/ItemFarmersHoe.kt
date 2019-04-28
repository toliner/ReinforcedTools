package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import c6h2cl2.YukariLib.Util.BlockPos
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemHoe
import net.minecraft.item.ItemStack
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class ItemFarmersHoe(material: ToolMaterial, private val level: Int) : ItemHoe(material), IReinforcedTools {

    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.fortune, Enchantment.unbreaking)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(1.0, 1.0, 1.0).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}FarmersHoe"
        setTextureName("$MOD_ID:${name.toLowerCase()}_farmers_hoe")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        maxDamage *= 8
    }

    override fun onItemUse(itemStack: ItemStack?, player: EntityPlayer?, world: World?, x: Int, y: Int, z: Int, side: Int, p_77648_8_: Float, p_77648_9_: Float, p_77648_10_: Float): Boolean {
        val pos = BlockPos(x, y, z)
        val target = setOf(pos, pos.east, pos.west, pos.north, pos.north.east, pos.north.west, pos.south, pos.south.east, pos.south.west)
        target.forEach {
            super.onItemUse(itemStack, player, world, it.getX(), it.getY(), it.getZ(), side, p_77648_8_, p_77648_9_, p_77648_10_)
        }
        return true
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