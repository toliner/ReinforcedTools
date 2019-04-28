package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.EnumRecipeType.NORMAL
import c6h2cl2.ReinforcedTools.item.EnumToolType.*
import c6h2cl2.ReinforcedTools.item.ItemLongBow
import c6h2cl2.ReinforcedTools.item.ItemShortBow
import c6h2cl2.ReinforcedTools.item.ToolBuilder
import c6h2cl2.YukariLib.Util.BlockUtil
import c6h2cl2.YukariLib.Util.ItemUtil
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.oredict.OreDictionary

/**
 * @author C6H2Cl2
 */
object ReinforcedToolsRegistry {

    @JvmStatic
    val tabReinforcedTools = object : CreativeTabs("reinforcedTools") {
        override fun getTabIconItem(): Item {
            return Items.diamond_axe
        }
    }

    val reinforcedWood = EnumHelper.addToolMaterial("reinforcedWooden", 1, 250, 3.0F, 1.0F, 20)!!
    val reinforcedStone = EnumHelper.addToolMaterial("reinforcedStone", 2, 1000, 6.5F, 3.0F, 10)!!
    val reinforcedIron = EnumHelper.addToolMaterial("reinforcedIron", 3, 3000, 14.0F, 6.0F, 25)!!
    val reinforcedGold = EnumHelper.addToolMaterial("reinforcedGold", 3, 600, 28.0F, 2.0F, 30)!!
    val reinforcedDiamond = EnumHelper.addToolMaterial("reinforcedDiamond", 4, 30000, 20.0F, 8.0f, 80)!!

    val woodenToolRod = ItemUtil.CreateItem("woodenToolRod", "wooden_tool_rod", MOD_ID, tabReinforcedTools)
    val stoneToolRod = ItemUtil.CreateItem("stoneToolRod", "stone_tool_rod", MOD_ID, tabReinforcedTools)
    val ironToolRod = ItemUtil.CreateItem("ironToolRod", "iron_tool_rod", MOD_ID, tabReinforcedTools)
    val goldToolRod = ItemUtil.CreateItem("goldToolRod", "gold_tool_rod", MOD_ID, tabReinforcedTools)
    val diamondToolRod = ItemUtil.CreateItem("diamondToolRod", "diamond_tool_rod", MOD_ID, tabReinforcedTools)

    val woodMaterial = ItemStack(Blocks.log, OreDictionary.WILDCARD_VALUE)
    val stoneMaterial = ItemStack(Blocks.furnace)
    val ironMaterial = ItemStack(Blocks.iron_block)
    val goldMaterial = ItemStack(Blocks.gold_block)
    val diamondMaterial = ItemStack(Blocks.diamond_block)

    //弓の中間素材
    val SuppleStick = ItemUtil.CreateItem(name = "suppleStick", modID = MOD_ID, creativeTabs = tabReinforcedTools)
    val CompositeStick = ItemUtil.CreateItem(name = "compositeStick", modID = MOD_ID, creativeTabs = tabReinforcedTools)
    val HighTensionString = ItemUtil.CreateItem(name = "highTensionString", modID = MOD_ID,creativeTabs = tabReinforcedTools)
    val StrongString = ItemUtil.CreateItem(name = "strongString", modID = MOD_ID, creativeTabs = tabReinforcedTools)
    val SlimeBlock = BlockUtil.CreateBlock(name = "slimeBlock", modID = MOD_ID, material = Material.rock).setCreativeTab(tabReinforcedTools)!!

    val shortBow = ItemShortBow()
    val longBow = ItemLongBow()

    val woodTool = ToolBuilder()
            .setMaterial(reinforcedWood)
            .setEnchantLevel(0)
            .setType(AXE, PICKAXE, SHOVEL, HOE, SWORD)
            .build()
            .setToolRod(ItemStack(woodenToolRod))
            .setToolHead(AXE, ItemStack(Items.wooden_axe))
            .setToolHead(PICKAXE, ItemStack(Items.wooden_pickaxe))
            .setToolHead(SHOVEL, ItemStack(Items.wooden_shovel))
            .setToolHead(HOE, ItemStack(Items.wooden_hoe))
            .setToolHead(SWORD, ItemStack(Items.wooden_sword))

    val stoneTool = ToolBuilder()
            .setMaterial(reinforcedStone)
            .setEnchantLevel(0)
            .setType(AXE, PICKAXE, SHOVEL, HOE, SWORD, PAXEL)
            .build()
            .setToolRod(ItemStack(stoneToolRod))
            .setToolHead(AXE, ItemStack(Items.stone_axe))
            .setToolHead(PICKAXE, ItemStack(Items.stone_pickaxe))
            .setToolHead(SHOVEL, ItemStack(Items.stone_shovel))
            .setToolHead(HOE, ItemStack(Items.stone_hoe))
            .setToolHead(SWORD, ItemStack(Items.stone_sword))

    val ironTool = ToolBuilder()
            .setMaterial(reinforcedIron)
            .setEnchantLevel(5)
            .setType(AXE, PICKAXE, SHOVEL, HOE, SWORD, PAXEL, MINING_HAMMER, BATTLE_AXE, TWO_HANDED_SWORD, FELLING_AXE, EXCAVATION_HAMMER, EXCAVATION_SHOVEL, FARMERS_HOE)
            .build()
            .setToolRod(ItemStack(ironToolRod))
            .setToolHead(AXE, ItemStack(Items.iron_axe))
            .setToolHead(PICKAXE, ItemStack(Items.iron_pickaxe))
            .setToolHead(SHOVEL, ItemStack(Items.iron_shovel))
            .setToolHead(HOE, ItemStack(Items.iron_hoe))
            .setToolHead(SWORD, ItemStack(Items.iron_sword))
            .setEnchantMaterial(if (RecipeDifficulty == NORMAL) ItemStack(Blocks.iron_block) else ItemStack(ironToolRod))

    val goldTool = ToolBuilder()
            .setMaterial(reinforcedGold)
            .setEnchantLevel(15)
            .setType(AXE, PICKAXE, SHOVEL, HOE, SWORD, PAXEL, MINING_HAMMER, BATTLE_AXE, TWO_HANDED_SWORD, FELLING_AXE, EXCAVATION_HAMMER, EXCAVATION_SHOVEL, FARMERS_HOE)
            .build()
            .setToolRod(ItemStack(goldToolRod))
            .setToolHead(AXE, ItemStack(Items.golden_axe))
            .setToolHead(PICKAXE, ItemStack(Items.golden_pickaxe))
            .setToolHead(SHOVEL, ItemStack(Items.golden_shovel))
            .setToolHead(HOE, ItemStack(Items.golden_hoe))
            .setToolHead(SWORD, ItemStack(Items.golden_sword))
            .setEnchantMaterial(if (RecipeDifficulty == NORMAL) ItemStack(Blocks.gold_block) else ItemStack(goldToolRod))

    val diamondTool = ToolBuilder()
            .setMaterial(reinforcedDiamond)
            .setEnchantLevel(10)
            .setType(AXE, PICKAXE, SHOVEL, HOE, SWORD, PAXEL, MINING_HAMMER, BATTLE_AXE, TWO_HANDED_SWORD, FELLING_AXE, EXCAVATION_HAMMER, EXCAVATION_SHOVEL, FARMERS_HOE)
            .build()
            .setToolRod(ItemStack(diamondToolRod))
            .setToolHead(AXE, ItemStack(Items.diamond_axe))
            .setToolHead(PICKAXE, ItemStack(Items.diamond_pickaxe))
            .setToolHead(SHOVEL, ItemStack(Items.diamond_shovel))
            .setToolHead(HOE, ItemStack(Items.diamond_hoe))
            .setToolHead(SWORD, ItemStack(Items.diamond_sword))
            .setEnchantMaterial(if (RecipeDifficulty == NORMAL) ItemStack(Blocks.diamond_block) else ItemStack(diamondToolRod))

    fun preInit(event: FMLPreInitializationEvent) {
        GameRegistry.registerItem(woodenToolRod, "woodenToolRod")
        GameRegistry.registerItem(stoneToolRod, "stoneToolRod")
        GameRegistry.registerItem(ironToolRod, "ironToolRod")
        GameRegistry.registerItem(goldToolRod, "goldToolRod")
        GameRegistry.registerItem(diamondToolRod, "diamondToolRod")
        GameRegistry.registerItem(shortBow, "shortBow")
        GameRegistry.registerItem(longBow, "longBow")
        woodTool.register()
        stoneTool.register()
        ironTool.register()
        goldTool.register()
        diamondTool.register()
        arrayListOf<String>().toTypedArray()
    }

    fun init(event: FMLInitializationEvent) {
        RecipeRegistry.addToolRodRecipe()
        woodTool.addRecipe()
        stoneTool.addRecipe()
        ironTool.addRecipe()
        goldTool.addRecipe()
        diamondTool.addRecipe()
    }
}