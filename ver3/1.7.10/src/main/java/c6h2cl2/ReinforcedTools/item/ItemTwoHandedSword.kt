package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.MOD_ID
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import com.google.common.collect.Multimap
import net.minecraft.block.Block
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.EnumAction.none
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.AxisAlignedBB
import net.minecraft.world.World

/**
 * @author C6H2Cl2
 */
class ItemTwoHandedSword(private val toolMaterial: ToolMaterial, private val level: Int) : Item(), IReinforcedTools {

    private val damageVsEntity = (toolMaterial.damageVsEntity * 2.0) + 4

    private var enchantList = listOf<Enchantment>(Enchantment.sharpness, Enchantment.looting, Enchantment.unbreaking)
            .toMutableSet()
            .toSortedSet(Comparator { o1, o2 -> o1.name.compareTo(o2.name) })
    private var levelList = listOf(1.2, 1.2, 1.2).toMutableList()

    init {
        val name = toolMaterial.name.substring(10)
        unlocalizedName = "${name[0].toLowerCase()}${name.substring(1)}TwoHandedSword"
        setTextureName("$MOD_ID:${name.toLowerCase()}_two_handed_sword")
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        maxDamage = toolMaterial.maxUses * 4
        maxStackSize = 1
    }

    override fun hitEntity(itemStack: ItemStack, targetEntity: EntityLivingBase, player: EntityLivingBase): Boolean {
        itemStack.damageItem(1, player)
        if (!(itemStack.tagCompound?.getBoolean("aoe_on_tick") ?: false) && player is EntityPlayer) {
            itemStack.tagCompound.setBoolean("aoe_on_tick", true)
            val x = targetEntity.posX
            val y = targetEntity.posY
            val z = targetEntity.posZ
            targetEntity.worldObj.getEntitiesWithinAABBExcludingEntity(targetEntity, AxisAlignedBB.getBoundingBox(x - 1.5, y - 0.5, z - 1.5, x + 1.5, y + 0.5, z +1.5)).forEach {
                if (it != player && it is Entity) {
                    player.attackTargetEntityWithCurrentItem(it)
                }
            }
        }
        return true
    }

    override fun onUpdate(itemStack: ItemStack?, world: World?, entity: Entity?, slot: Int, p_77663_5_: Boolean) {
        if (!(itemStack?.hasTagCompound() ?: true)) {
            itemStack?.tagCompound = NBTTagCompound()
        }
        itemStack?.tagCompound?.setBoolean("aoe_on_tick", false)
    }

    override fun canHarvestBlock(block: Block, itemStack: ItemStack?): Boolean {
        return false
    }

    override fun func_150893_a(itemStack: ItemStack?, block: Block): Float = 0f

    override fun getItemEnchantability(): Int {
        return toolMaterial.enchantability
    }

    override fun getItemAttributeModifiers(): Multimap<*, *> {
        val multimap = super.getItemAttributeModifiers()
        multimap.put(SharedMonsterAttributes.attackDamage.attributeUnlocalizedName, AttributeModifier(field_111210_e, "Weapon modifier", damageVsEntity, 0))
        return multimap
    }

    override fun getItemUseAction(p_77661_1_: ItemStack?): EnumAction {
        return none
    }

    override fun getMaxItemUseDuration(p_77626_1_: ItemStack?): Int {
        return 0
    }

    override fun onItemRightClick(p_77659_1_: ItemStack, p_77659_2_: World?, p_77659_3_: EntityPlayer?): ItemStack {
        return p_77659_1_
    }

    override fun func_150897_b(p_150897_1_: Block?): Boolean {
        return false
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