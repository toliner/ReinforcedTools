package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.EnumRecipeType.*
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

/**
 * @author C6H2Cl2
 */

const val MOD_ID = "reinforcedtools"
const val MOD_NAME = "ReinforcedTools Maple Custom"
const val VERSION = "3.0.0-b1"
const val DEPENDENT = "required-after:YukariLib@[1.2.0,);after:ExtraUtilities"

var RecipeDifficulty: EnumRecipeType = NORMAL

fun isToolEffective(stack: ItemStack, block: Block, metadata: Int): Boolean {
    return stack.item.getToolClasses(stack).any { ("pickaxe" == it && (block === Blocks.redstone_ore || block === Blocks.lit_redstone_ore || block === Blocks.obsidian)) || block.isToolEffective(it, metadata) }
}

fun isLog(block: Block): Boolean {
    return OreDictionary.getOres("logWood").filter {
        it.item is ItemBlock
    }.any {
        (it.item as ItemBlock).field_150939_a == block
    }
}

fun isLeaf(block: Block): Boolean {
    return OreDictionary.getOres("treeLeaves").filter {
        it.item is ItemBlock
    }.any {
        (it.item as ItemBlock).field_150939_a == block
    }
}