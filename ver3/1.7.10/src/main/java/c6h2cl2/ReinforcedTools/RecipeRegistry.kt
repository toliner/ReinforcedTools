package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.EnumRecipeType.*
import c6h2cl2.ReinforcedTools.item.EnumToolType
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.oredict.ShapedOreRecipe

/**
 * @author C6H2Cl2
 */
object RecipeRegistry {
    fun addToolRodRecipe() {
        when (RecipeDifficulty) {
            NORMAL -> kotlin.run {
                val recipe = arrayOf("M", "M", "M", 'M')
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.woodenToolRod, 2), *recipe, ReinforcedToolsRegistry.woodMaterial)
                GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(ReinforcedToolsRegistry.stoneToolRod, 2), *recipe, if (OreDictionary.doesOreNameExist("compressedCobblestone${if (RecipeDifficulty == VERY_HARD) 2 else 1}x")) "compressedCobblestone${if (RecipeDifficulty == VERY_HARD) 2 else 1}x" else ReinforcedToolsRegistry.stoneMaterial))
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.ironToolRod, 2), *recipe, ReinforcedToolsRegistry.ironMaterial)
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.goldToolRod, 2), *recipe, ReinforcedToolsRegistry.goldMaterial)
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.diamondToolRod, 2), *recipe, ReinforcedToolsRegistry.diamondMaterial)
            }
            HARD -> kotlin.run {
                val recipe = arrayOf("MM", "MM", "MM", 'M')
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.woodenToolRod), *recipe, ReinforcedToolsRegistry.woodMaterial)
                GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(ReinforcedToolsRegistry.stoneToolRod), *recipe, if (OreDictionary.doesOreNameExist("compressedCobblestone${if (RecipeDifficulty == VERY_HARD) 2 else 1}x")) "compressedCobblestone${if (RecipeDifficulty == VERY_HARD) 2 else 1}x" else ReinforcedToolsRegistry.stoneMaterial))
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.ironToolRod), *recipe, ReinforcedToolsRegistry.ironMaterial)
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.goldToolRod), *recipe, ReinforcedToolsRegistry.goldMaterial)
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.diamondToolRod), *recipe, ReinforcedToolsRegistry.diamondMaterial)
            }
            VERY_HARD -> kotlin.run {
                val recipe = arrayOf("MMM", "MRM", "MMM", 'M')
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.woodenToolRod), "MMM", "MMM", "MMM", 'M', ReinforcedToolsRegistry.woodMaterial)
                GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(ReinforcedToolsRegistry.stoneToolRod), *recipe, if (OreDictionary.doesOreNameExist("compressedCobblestone${if (RecipeDifficulty == VERY_HARD) 2 else 1}x")) "compressedCobblestone${if (RecipeDifficulty == VERY_HARD) 2 else 1}x" else ReinforcedToolsRegistry.stoneMaterial, 'R', ReinforcedToolsRegistry.woodenToolRod))
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.ironToolRod), *recipe, ReinforcedToolsRegistry.ironMaterial, 'R', ReinforcedToolsRegistry.stoneToolRod)
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.goldToolRod), *recipe, ReinforcedToolsRegistry.goldMaterial, 'R', ReinforcedToolsRegistry.ironToolRod)
                GameRegistry.addRecipe(ItemStack(ReinforcedToolsRegistry.diamondToolRod), *recipe, ReinforcedToolsRegistry.diamondMaterial, 'R', ReinforcedToolsRegistry.goldToolRod)
            }
        }
    }
}