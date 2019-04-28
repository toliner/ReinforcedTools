package c6h2cl2.ReinforcedTools

import net.minecraft.init.Items
import net.minecraft.item.Item

/**
 * @author C6H2Cl2
 */
//ToolHead:H, ToolRod:R
//Pickaxe:P, Axe:A, Shove:S
enum class EnumToolType(recipe1:String,recipe2:String,recipe3:String,val isHighLevelTool:Boolean) {
    PICKAXE(" R "," R ","HHH",false),AXE(" R ","HR ","HH ",false),SHOVEL(" R "," R "," H ",false),HOE(" R "," R ","HH ",false),SWORD("  H","RH ","HR ",false),
    PAXEL("ASP"," R "," R ",true),BATTLEAXE("AS","AR"," R",true),MININGHAMMER("PSP"," R "," R ",true);
    val recipe = arrayListOf(recipe1,recipe2,recipe3)
    fun getMaterials():Array<Item>{
        return when(this){
            PICKAXE -> arrayOf(Items.WOODEN_PICKAXE,Items.STONE_PICKAXE,Items.IRON_PICKAXE,Items.GOLDEN_PICKAXE,Items.DIAMOND_PICKAXE)
            AXE -> arrayOf(Items.WOODEN_AXE,Items.STONE_AXE,Items.IRON_AXE,Items.GOLDEN_APPLE,Items.DIAMOND_AXE)
            SHOVEL -> arrayOf(Items.WOODEN_SHOVEL,Items.STONE_SHOVEL,Items.IRON_SHOVEL,Items.GOLDEN_SHOVEL,Items.DIAMOND_SHOVEL)
            HOE -> arrayOf(Items.WOODEN_HOE,Items.STONE_HOE,Items.IRON_HOE,Items.GOLDEN_HOE,Items.DIAMOND_HOE)
            SWORD -> arrayOf(Items.WOODEN_SWORD,Items.STONE_SWORD,Items.IRON_SWORD,Items.GOLDEN_SWORD,Items.DIAMOND_SWORD)
            else -> emptyArray()
        }
    }
}