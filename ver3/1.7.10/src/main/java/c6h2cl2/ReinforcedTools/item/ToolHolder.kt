package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.item.EnumToolType.*
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.oredict.ShapedOreRecipe

/**
 * @author C6H2Cl2
 */
class ToolHolder {
    private val tools = emptyMap<EnumToolType, IReinforcedTools>().toMutableMap().toSortedMap()
    private var toolHead = emptyMap<EnumToolType, ItemStack>().toMutableMap()
    private lateinit var toolRod: ItemStack
    private lateinit var enchantMaterial: ItemStack
    private var isOreRecipe = false
    private var toolRodOreName: String? = null
    private var enchantMaterialOreName: String? = null

    fun addTool(type: EnumToolType, tool: IReinforcedTools): ToolHolder {
        tools.put(type, tool)
        return this
    }

    fun addEnchant(enchantment: Enchantment, level: Double = 1.0): ToolHolder {
        tools.values.forEach {
            it.addEnchant(enchantment)
            it.setEnchantLevel(enchantment, level)
        }
        return this
    }

    fun setEnchantLevel(enchantment: Enchantment, level: Double): ToolHolder {
        tools.values.forEach {
            it.setEnchantLevel(enchantment, level)
        }
        return this
    }

    fun setEnchantLevel(vararg levels: Double): ToolHolder {
        tools.values.forEach {
            it.setEnchantLevel(*levels)
        }
        return this
    }

    fun removeEnchant(enchantment: Enchantment): ToolHolder {
        tools.values.forEach {
            it.removeEnchant(enchantment)
        }
        return this
    }

    fun setEnchantMaterial(enchantMaterial: ItemStack): ToolHolder {
        this.enchantMaterial = enchantMaterial
        return this
    }

    fun setToolHead(toolHead: Map<EnumToolType, ItemStack>): ToolHolder {
        this.toolHead = toolHead.toMutableMap()
        return this
    }

    fun setToolHead(type: EnumToolType, toolHead: ItemStack): ToolHolder {
        this.toolHead.put(type, toolHead)
        return this
    }

    fun setToolRod(toolRod: ItemStack): ToolHolder {
        this.toolRod = toolRod
        return this
    }

    fun getTool(type: EnumToolType): Item {
        return tools[type] as Item
    }

    fun setUseOre(enable: Boolean): ToolHolder {
        isOreRecipe = enable
        return this
    }

    fun setOreName(oreName: String): ToolHolder {
        toolRodOreName = oreName
        return this
    }

    fun register() {
        tools.values.forEach {
            GameRegistry.registerItem(it as Item, (it as Item).unlocalizedName)
        }
    }

    fun addRecipe() {
        tools.keys.forEach {
            val item = tools[it] as Item
            val itemStack = ItemStack(item)
            val r = arrayOf('R', if (isOreRecipe && OreDictionary.doesOreNameExist(toolRodOreName)) toolRodOreName!! else toolRod)
            when (it!!) {
                AXE -> addRecipe(itemStack, " R", "HR", "HH", 'H', toolHead[it]!!, *r)
                PICKAXE -> addRecipe(itemStack, " R ", " R ", "HHH", 'H', toolHead[it]!!, *r)
                SHOVEL -> addRecipe(itemStack, "R", "R", "H", 'H', toolHead[it]!!, *r)
                HOE -> addRecipe(itemStack, " R", " R", "HH", 'H', toolHead[it]!!, *r)
                SWORD -> addRecipe(itemStack, "  H", "RH ", "HR ", 'H', toolHead[it]!!, *r)
                PAXEL -> addRecipe(itemStack, "ASP", " R ", " R ", 'A', tools[AXE] as Item, 'S', tools[SHOVEL] as Item, 'P', tools[PICKAXE] as Item, *r)
                MINING_HAMMER -> addRecipe(itemStack, "PSP", " R ", " R ", 'P', tools[PICKAXE] as Item, 'S', tools[SHOVEL] as Item, *r)
                BATTLE_AXE -> addRecipe(itemStack, "AS", "AR", " R", 'A', tools[AXE] as Item, 'S', tools[SWORD] as Item, *r)
                TWO_HANDED_SWORD -> addRecipe(itemStack, " S ", "SRS", "SRS", 'S', tools[SWORD] as Item, *r)
                FELLING_AXE -> addRecipe(itemStack, "AAA", "ARA", " R ", 'A', tools[AXE] as Item, *r)
                EXCAVATION_HAMMER -> addRecipe(itemStack, "PPP", "PRP", " R ", 'P', tools[PICKAXE] as Item, *r)
            }
            if (tools[it]?.getEnchantLevelBase() != 0) {
                GameRegistry.addRecipe(tools[it]?.getEnchanted()!!, "MMM", "MTM", "MMM", 'T', ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), 'M', if (isOreRecipe && OreDictionary.doesOreNameExist(enchantMaterialOreName)) enchantMaterialOreName else enchantMaterial)
            }
        }
    }

    private fun addRecipe(output: ItemStack, vararg recipe: Any) {
        GameRegistry.addRecipe(ShapedOreRecipe(output, *recipe))
    }
}