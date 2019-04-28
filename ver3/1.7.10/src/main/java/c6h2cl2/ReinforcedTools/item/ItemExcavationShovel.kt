package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import c6h2cl2.ReinforcedTools.isToolEffective
import c6h2cl2.YukariLib.Util.BlockPos
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S23PacketBlockChange
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks

/**
 * @author C6H2Cl2
 */
class ItemExcavationShovel(material: ToolMaterial, private val level: Int): ItemTool(-2f, material, null), IReinforcedTools {

    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.fortune, Enchantment.unbreaking)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(1.2, 1.2, 1.2).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}ExcavationShovel"
        setTextureName("${MOD_ID}:${name.toLowerCase()}_excavation_shovel")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        efficiencyOnProperMaterial *= 1.5f
        maxDamage = material.maxUses * 4
        maxStackSize = 1
        for (toolClass in getToolClasses(ItemStack(this))) {
            setHarvestLevel(toolClass, material.harvestLevel)
        }
    }

    override fun onBlockDestroyed(itemStack: ItemStack?, world: World?, block: Block?, x: Int, y: Int, z: Int, player: EntityLivingBase?): Boolean {
        if (itemStack == null || world == null || block == null || player == null) return true
        if (!itemStack.tagCompound.getBoolean("aoe_tick") && player is EntityPlayerMP && isToolEffective(itemStack, block, world.getBlockMetadata(x, y, z))) {
            itemStack.tagCompound.setBoolean("aoe_tick", true)
            val side = itemStack.tagCompound.getInteger("break_side")
            val basePos = BlockPos(x, y, z)
            val pos = when(side) {
            //Bottom, Up
                0, 1 -> setOf(basePos.north, basePos.south, basePos.east, basePos.west, basePos.north.east, basePos.north.west, basePos.south.east, basePos.south.west)
            //East, West
                4, 5 -> setOf(basePos.up, basePos.down, basePos.south, basePos.north, basePos.up.south, basePos.up.north, basePos.down.south, basePos.down.north)
            //North, South
                2, 3 -> setOf(basePos.up, basePos.down, basePos.east, basePos.west, basePos.up.east, basePos.up.west, basePos.down.east, basePos.down.west)
                else -> return true
            }
            val manager = player.theItemInWorldManager
            pos.forEach {
                if (canHarvestBlock(world.getBlock(it.getX(), it.getY(), it.getZ()), itemStack)) {
                    manager.tryHarvestBlock(it.getX(), it.getY(), it.getZ())
                    player.playerNetServerHandler.sendPacket(S23PacketBlockChange(it.getX(), it.getY(), it.getZ(), world))
                }
            }
        }
        return super.onBlockDestroyed(itemStack, world, block, x, y, z, player)
    }

    override fun onUpdate(itemStack: ItemStack?, world: World?, player: Entity?, slot: Int, p_77663_5_: Boolean) {
        if (itemStack == null || world == null || player == null) return
        if (!itemStack.hasTagCompound()) {
            itemStack.tagCompound = NBTTagCompound()
        }
        itemStack.tagCompound.setBoolean("aoe_tick", false)
    }

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("shovel", "Shovel")
    }

    override fun canHarvestBlock(block: Block, itemStack: ItemStack?): Boolean {
        return (block.getHarvestLevel(0) <= toolMaterial.harvestLevel && getToolClasses(itemStack).contains(block.getHarvestTool(0)))
    }

    override fun func_150897_b(block: Block): Boolean {
        return canHarvestBlock(block, null)
    }

    override fun getDigSpeed(itemStack: ItemStack?, block: Block, meta: Int): Float {
        return if (itemStack != null && isToolEffective(itemStack, block, 0)) {
            efficiencyOnProperMaterial
        } else {
            1f
        }
    }

    override fun getEnchanted(): ItemStack {
        val itemStack = ItemStack(this)
        enchantList.forEachIndexed { index, enchant ->
            itemStack.addEnchantment(enchant, (level * levelList[index]).toInt())
        }
        return itemStack
    }

    override fun func_150893_a(itemStack: ItemStack?, block: Block): Float {
        return if (itemStack != null && ForgeHooks.isToolEffective(itemStack, block, 0)) {
            efficiencyOnProperMaterial
        } else {
            0f
        }
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