package c6h2cl2.ReinforcedTools

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.ItemRenderType

/**
 * @author C6H2Cl2
 */
class Render : IItemRenderer {
    private val mc: Minecraft
    private val texturemanager: TextureManager

    init {
        val renderManager = RenderManager.instance
        this.mc = Minecraft.getMinecraft()
        this.texturemanager = this.mc.textureManager
    }

    override //HandleRenderType lets forge know if it will render the item in the requested view.
    fun handleRenderType(item: ItemStack, type: ItemRenderType): Boolean {
        //You can remove everything after "EQUIPPED" if you only want this class to render the third person item.
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON
    }

    override //RenderHelpers I don't fully understand, I'd assume they are modifiers, but I've never looked into it.
    fun shouldUseRenderHelper(type: ItemRenderType, item: ItemStack, helper: IItemRenderer.ItemRendererHelper): Boolean {
        return false
    }

    override //This function decides what to do in rendering an item, whether it's first or third person.
            //Credit to SanAndreasP on minecraftforge forums for this code.
    fun renderItem(type: ItemRenderType, item: ItemStack, vararg data: Any) {
        val entity = data[1] as EntityClientPlayerMP
        val irInstance = this.mc.entityRenderer.itemRenderer
        GL11.glPopMatrix() // prevents Forge from pre-translating the item
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            this.renderItem(entity, item, 0)
        } else {
            GL11.glPushMatrix()
            // contra-translate the item from it's standard translation
            // also apply some more scale or else the bow is tiny
            val f2 = 3f - 1f / 3f
            GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f)
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f)
            GL11.glRotatef(-60.0f, 0.0f, 0.0f, 1.0f)
            GL11.glScalef(f2, f2, f2)
            GL11.glTranslatef(-0.25f, -0.1875f, 0.1875f)

            // render the item as 'real' bow
            //This is pulled from RenderBiped
            val f3 = 0.625f
            GL11.glTranslatef(0.0f, 0.125f, 0.3125f)
            GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f)
            GL11.glScalef(f3, -f3, f3)
            GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f)
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f)

            this.renderItem(entity, item, 0)
            GL11.glPopMatrix()
        }
        GL11.glPushMatrix() // prevents GL Underflow errors
    }

    //This actually renders an Icon to be worked with.
    //All of this code is directly pulled from ItemRenderer.class
    private fun renderItem(par1EntityLiving: EntityClientPlayerMP, par2ItemStack: ItemStack, par3: Int) {
        run {
            //If you for whatever reason aren't registering icons with iconRegister, I'm assuming you'll need to change the code below.
            val icon = par1EntityLiving.getItemIcon(par2ItemStack, par3)
            if (icon == null) {
                GL11.glPopMatrix()
                return
            }
            texturemanager.getTexture(texturemanager.getResourceLocation(par2ItemStack.itemSpriteNumber))
            val tessellator = Tessellator.instance
            val f = icon.minU
            val f1 = icon.maxU
            val f2 = icon.minV
            val f3 = icon.maxV
            val f4 = 0.0f
            val f5 = 0.3f
            GL11.glEnable(GL12.GL_RESCALE_NORMAL)
            GL11.glTranslatef(-f4, -f5, 0.0f)
            val f6 = 1.5f
            GL11.glScalef(f6, f6, f6)
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f)
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f)
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f)
            ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.iconWidth, icon.iconHeight, 0.0625f)
            //This checks for enchantments.
            if (par2ItemStack.hasEffect(par3)) {
                GL11.glDepthFunc(GL11.GL_EQUAL)
                GL11.glDisable(GL11.GL_LIGHTING)
                texturemanager.getTexture(ResourceLocation("textures/misc/enchanted_item_glint.png"))
                GL11.glEnable(GL11.GL_BLEND)
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE)
                val f7 = 0.76f
                GL11.glColor4f(0.5f * f7, 0.25f * f7, 0.8f * f7, 1.0f)
                GL11.glMatrixMode(GL11.GL_TEXTURE)
                GL11.glPushMatrix()
                val f8 = 0.125f
                GL11.glScalef(f8, f8, f8)
                var f9 = (Minecraft.getSystemTime() % 3000L).toFloat() / 3000.0f * 8.0f
                GL11.glTranslatef(f9, 0.0f, 0.0f)
                GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f)
                ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f)
                GL11.glPopMatrix()
                GL11.glPushMatrix()
                GL11.glScalef(f8, f8, f8)
                f9 = (Minecraft.getSystemTime() % 4873L).toFloat() / 4873.0f * 8.0f
                GL11.glTranslatef(-f9, 0.0f, 0.0f)
                GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f)
                ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f)
                GL11.glPopMatrix()
                GL11.glMatrixMode(GL11.GL_MODELVIEW)
                GL11.glDisable(GL11.GL_BLEND)
                GL11.glEnable(GL11.GL_LIGHTING)
                GL11.glDepthFunc(GL11.GL_LEQUAL)
            }
            GL11.glDisable(GL12.GL_RESCALE_NORMAL)
        }
    }
}
