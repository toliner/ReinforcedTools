package c6h2cl2.ReinforcedTools.Item

import c6h2cl2.ReinforcedTools.ReinforcedToolsCore
import c6h2cl2.ReinforcedTools.ReinforcedToolsRegistry
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemBow
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.ArrowLooseEvent
import net.minecraftforge.event.entity.player.ArrowNockEvent

/**
 * @author C6H2Cl2
 */
class LongBow :ItemBow(){
    init {
        creativeTab = ReinforcedToolsRegistry.tabReinforcedTools
        unlocalizedName = "${ReinforcedToolsCore.Domain}.longbow"
        setTextureName("${ReinforcedToolsCore.Domain}:longbow")
        maxDamage = 2048
        iconString = "${ReinforcedToolsCore.Domain}:longbow"
    }

    companion object{
        val bowPullIconNameArray = arrayOf("pulling_0", "pulling_1", "pulling_2")
    }

    override fun getItemEnchantability() = 2

    override fun onPlayerStoppedUsing(itemStack: ItemStack, world: World, player: EntityPlayer, p_77615_4_: Int) {
        if(!itemStack.hasTagCompound()){
            itemStack.tagCompound = NBTTagCompound()
        }else if (itemStack.tagCompound.getBoolean("isShot")){
            itemStack.tagCompound.setBoolean("isShot",false)
            return
        }
        shotArrow(itemStack,world,player,p_77615_4_)
    }

    override fun onItemRightClick(itemStack: ItemStack, world: World, player: EntityPlayer): ItemStack {
        val event = ArrowNockEvent(player,itemStack)
        MinecraftForge.EVENT_BUS.post(event)
        if(event.isCanceled) return event.result
        if(player.capabilities.isCreativeMode || player.inventory.hasItem(Items.arrow)){
            player.setItemInUse(itemStack,getMaxItemUseDuration(itemStack))
        }
        return itemStack
    }

    override fun getMaxItemUseDuration(itemStack: ItemStack?): Int {
        if (itemStack == null) return 72000
        if(!itemStack.hasTagCompound()){
            itemStack.tagCompound = NBTTagCompound()
            itemStack.tagCompound.setBoolean("auto_shot",false)
            return 72000
        }
        return if(itemStack.tagCompound.getBoolean("auto_shot")) 200 else 72000
    }

    override fun onEaten(itemStack: ItemStack, world: World, player: EntityPlayer): ItemStack {
        shotArrow(itemStack,world,player,getMaxItemUseDuration(itemStack))
        if (!itemStack.hasTagCompound()) itemStack.tagCompound = NBTTagCompound()
        itemStack.tagCompound.setBoolean("isShot",true)
        return itemStack
    }

    fun shotArrow(itemStack: ItemStack, world: World, player: EntityPlayer, useTick:Int){
        var j = useTick
        //Forgeのイベント投げる
        val event = ArrowLooseEvent(player,itemStack,j)
        MinecraftForge.EVENT_BUS.post(event)
        if (event.isCanceled) return
        //チャージ時間測定
        j = event.charge
        val flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId,itemStack) > 0
        //射出可能かどうかの判定
        if(flag || player.inventory.hasItem(Items.arrow)){
            //クリティカルと威力の判定だと思われ
            var f = j.toFloat() / 40f
            f = (f * f + f * 2.0f) / 8.0f
            if (f < 2f) return
            if(f > 5f) f = 5.0f
            val entityArrow = EntityArrow(world,player,f*3f)
            //クリティカル設定?
            if(f == 1.0f) entityArrow.isCritical = true
            //ダメージ設定
            val k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId,itemStack)
            if (k > 0) entityArrow.damage += k * 1.5
            entityArrow.damage *= 2.4
            //ノックバック設定
            val l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId,itemStack)
            if(l > 0)entityArrow.setKnockbackStrength(l)
            //燃やす
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId,itemStack) > 0) entityArrow.setFire(100)
            //耐久値減らす
            itemStack.damageItem(1,player)
            //音鳴らす
            world.playSoundAtEntity(player,"random.bow",1.0f,1.0f/(Item.itemRand.nextFloat() * 0.4f + 1.2f) + f * 0.5f)
            //アイテム減らしたり
            if(flag){
                entityArrow.canBePickedUp = 2
            }else{
                player.inventory.consumeInventoryItem(Items.arrow)
            }
            //矢スポーン
            if(!world.isRemote) world.spawnEntityInWorld(entityArrow)
        }
    }

    override fun onBlockStartBreak(itemStack: ItemStack?, X: Int, Y: Int, Z: Int, player: EntityPlayer): Boolean {
        if(itemStack == null) return false
        if(player.isSneaking){
            val isAllowed = if(itemStack.hasTagCompound() && itemStack.tagCompound.getBoolean("auto_shot")){
                itemStack.tagCompound.setBoolean("auto_shot",false)
                false
            }else{
                if(!itemStack.hasTagCompound()) itemStack.tagCompound = NBTTagCompound()
                itemStack.tagCompound.setBoolean("auto_shot",true)
                true
            }
            if(!player.worldObj.isRemote){
                player.addChatComponentMessage(ChatComponentText("Auto-Shot is $isAllowed"))
            }
        }
        return false
    }

    override fun onEntitySwing(player: EntityLivingBase?, itemStack: ItemStack?): Boolean {
        if(itemStack == null) return false
        if(player !is EntityPlayer) return false
        if(!itemStack.hasTagCompound()){
            itemStack.tagCompound = NBTTagCompound()
            itemStack.tagCompound.setBoolean("mode_changed",false)
            itemStack.tagCompound.setInteger("cool_time",11)
        }
        if(itemStack.tagCompound.getBoolean("mode_changed")){
            if(itemStack.tagCompound.getInteger("change_cool") > 0){
                itemStack.tagCompound.setInteger("change_cool",itemStack.tagCompound.getInteger("change_cool")-1)
            }else{
                itemStack.tagCompound.setBoolean("mode_changed",false)
            }
            return false
        }
        if(player.isSneaking){
            val isAllowed = if(itemStack.hasTagCompound() && itemStack.tagCompound.getBoolean("auto_shot")){
                itemStack.tagCompound.setBoolean("auto_shot",false)
                false
            }else{
                if(!itemStack.hasTagCompound()) itemStack.tagCompound = NBTTagCompound()
                itemStack.tagCompound.setBoolean("auto_shot",true)
                true
            }
            if(!player.worldObj.isRemote){
                player.addChatComponentMessage(ChatComponentText("Auto-Shot is $isAllowed"))
            }
            itemStack.tagCompound.setBoolean("mode_changed",true)
            itemStack.tagCompound.setInteger("change_cool",10)
        }
        return true
    }

    //Client Only ==============================================================================================

    @SideOnly(Side.CLIENT)
    var iconArray:Array<IIcon?> = kotlin.arrayOfNulls<IIcon>(bowPullIconNameArray.size)

    @SideOnly(Side.CLIENT)
    override fun registerIcons(register: IIconRegister) {
        itemIcon = register.registerIcon("${getIconString()}_standby")
        for (i in 0 until iconArray.size){
            iconArray[i] = register.registerIcon("${getIconString()}_${bowPullIconNameArray[i]}")
        }
    }

    @SideOnly(Side.CLIENT)
    override fun getIcon(stack: ItemStack, renderPass: Int, player: EntityPlayer?, usingItem: ItemStack?, useRemaining: Int): IIcon {
        if(usingItem == null || player == null) return itemIcon
        val useTick = stack.maxItemUseDuration - useRemaining
        //println("useTick:$useTick, useRemaining:$useRemaining")
        return if(useTick < 110) iconArray[0] as IIcon else if (useTick < 220) iconArray[1] as IIcon else  iconArray[2] as IIcon
    }
}