package c6h2cl2.ReinforcedTools.item

import c6h2cl2.ReinforcedTools.item.EnumToolType.*
import net.minecraft.item.Item.ToolMaterial
import net.minecraft.item.ItemStack

/**
 * @author C6H2Cl2
 */
class ToolBuilder {
    private lateinit var material: ToolMaterial
    private var enchantLevel = 0
    private var toolTypes = emptySet<EnumToolType>().toSortedSet().toMutableSet()

    fun setMaterial(material: ToolMaterial): ToolBuilder {
        this.material = material
        return this
    }

    fun setEnchantLevel(level: Int): ToolBuilder {
        enchantLevel = level
        return this
    }

    fun setType(vararg types: EnumToolType): ToolBuilder {
        toolTypes = types.toSet().toSortedSet().toMutableSet()
        return this
    }

    fun addType(vararg types: EnumToolType): ToolBuilder {
        types.forEach {
            toolTypes.add(it)
        }
        return this
    }

    fun build(): ToolHolder {
        val holder = ToolHolder()
        toolTypes.forEach {
            val item: IReinforcedTools = when (it!!) {
                AXE -> ItemReinforcedAxe(material, enchantLevel)
                PICKAXE -> ItemReinforcedPickaxe(material, enchantLevel)
                SHOVEL -> ItemReinforcedShovel(material, enchantLevel)
                HOE -> ItemReinforcedHoe(material, enchantLevel)
                SWORD -> ItemReinforcedSword(material, enchantLevel)
                PAXEL -> ItemPaxel(material, enchantLevel)
                MINING_HAMMER -> ItemMiningHammer(material, enchantLevel)
                BATTLE_AXE -> ItemBattleAxe(material, enchantLevel)
                TWO_HANDED_SWORD -> ItemTwoHandedSword(material, enchantLevel)
                FELLING_AXE -> ItemFellingAxe(material, enchantLevel)
                EXCAVATION_HAMMER -> ItemExcavationHammer(material, enchantLevel)
                EXCAVATION_SHOVEL -> ItemExcavationShovel(material, enchantLevel)
                FARMERS_HOE -> ItemFarmersHoe(material, enchantLevel)
            }
            holder.addTool(it, item)
        }
        return holder
    }
}