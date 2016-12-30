package c6h2cl2.ReinforcedTools

import net.minecraft.init.Items
import net.minecraft.item.Item

/**
 * @author C6H2Cl2
 */
//ToolHead:H, ToolRod:R
//Pickaxe:P, Axe:A, Shove:S
enum class EnumToolType(recipe1: String, recipe2: String, recipe3: String, val isHighLevelTool: Boolean) {
    PICKAXE(" R ", " R ", "HHH", false), AXE(" R ", "HR ", "HH ", false), SHOVEL(" R ", " R ", " H ", false), HOE(" R ", " R ", "HH ", false), SWORD("  H", "RH ", "HR ", false),
    PAXEL("ASP", " R ", " R ", true), BATTLEAXE("AS", "AR", " R", true), MININGHAMMER("PSP", " R ", " R ", true);

    val recipe = arrayListOf(recipe1, recipe2, recipe3)
    fun getMaterials(): Array<Item> {
        return when (this) {
            PICKAXE -> arrayOf(Items.wooden_pickaxe, Items.stone_pickaxe, Items.iron_pickaxe, Items.golden_pickaxe, Items.diamond_pickaxe)
            AXE -> arrayOf(Items.wooden_axe, Items.stone_axe, Items.iron_axe, Items.golden_axe, Items.diamond_axe)
            SHOVEL -> arrayOf(Items.wooden_shovel, Items.stone_shovel, Items.iron_shovel, Items.golden_shovel, Items.diamond_shovel)
            HOE -> arrayOf(Items.wooden_hoe, Items.stone_hoe, Items.iron_hoe, Items.golden_hoe, Items.diamond_hoe)
            SWORD -> arrayOf(Items.wooden_sword, Items.stone_sword, Items.iron_sword, Items.golden_sword, Items.diamond_sword)
            else -> emptyArray()
        }
    }
}