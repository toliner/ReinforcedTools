package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import c6h2cl2.ReinforcedTools.isLeaf
import c6h2cl2.ReinforcedTools.isLog
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
import net.minecraft.server.management.ItemInWorldManager
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks

/**
 * @author C6H2Cl2
 */
class ItemFellingAxe(material: ToolMaterial, private val level: Int) : ItemTool(3.0f, material, null), IReinforcedTools {

    private var enchantList = listOf<Enchantment>(Enchantment.efficiency, Enchantment.fortune, Enchantment.unbreaking)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(0.2, 0.6, 1.4, 1.0).toMutableList()

    init {
        val name = material.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}FellingAxe"
        setTextureName("$MOD_ID:${name.toLowerCase()}_felling_axe")
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
        if (!itemStack.tagCompound.getBoolean("aoe_tick") && player is EntityPlayerMP && isLog(block)) {
            itemStack.tagCompound.setBoolean("aoe_tick", true)
            val set = emptySet<BlockPos>().toMutableSet()
            harvest(itemStack, player.theItemInWorldManager, block, x, y, z, set)
        }
        return super.onBlockDestroyed(itemStack, world, block, x, y, z, player)
    }

    private fun harvest(itemStack: ItemStack, manager: ItemInWorldManager, block: Block, posX: Int, posY: Int, posZ: Int, set: MutableSet<BlockPos>) {
        manager.tryHarvestBlock(posX, posY, posZ)
        set.add(BlockPos(posX, posY, posZ))
        manager.thisPlayerMP.playerNetServerHandler.sendPacket(S23PacketBlockChange(posX, posY, posZ, manager.theWorld))
        for (y in posY..(posY + 1)) {
            for (x in (posX - 1)..(posX + 1)) {
                for (z in (posZ - 1)..(posZ + 1)) {
                    if (!set.contains(BlockPos(x, y, z))) {
                        val targetBlock = manager.theWorld.getBlock(x, y, z)
                        if (targetBlock == block || isLeaf(targetBlock)) {
                            harvest(itemStack, manager, targetBlock, x, y, z, set)
                        }
                    }
                }
            }
        }
    }

    override fun onUpdate(itemStack: ItemStack?, world: World?, player: Entity?, slot: Int, p_77663_5_: Boolean) {
        if (itemStack == null || world == null || player == null) return
        if (!itemStack.hasTagCompound()) {
            itemStack.tagCompound = NBTTagCompound()
        }
        itemStack.tagCompound.setBoolean("aoe_tick", false)
    }

    override fun getToolClasses(stack: ItemStack?): MutableSet<String> {
        return mutableSetOf("axe", "fellingeaxe", "Axe", "FellingAxe")
    }

    override fun canHarvestBlock(block: Block, itemStack: ItemStack?): Boolean {
        return isLog(block)
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