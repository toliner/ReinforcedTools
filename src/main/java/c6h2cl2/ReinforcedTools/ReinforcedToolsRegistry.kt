package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.Item.*
import c6h2cl2.YukariLib.Util.BlockUtil
import c6h2cl2.YukariLib.Util.ItemUtil
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.enchantment.Enchantment
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.oredict.OreDictionary
import org.jetbrains.annotations.Contract
import java.util.*

/**
 * @author C6H2Cl2
 */
object ReinforcedToolsRegistry {
    //クリエタブ
    val tabReinforcedTools = object : CreativeTabs("reinforcedTools") {
        @Contract(pure = true)
        override fun getTabIconItem(): Item {
            return Axe[4]
        }
    }

    //ToolMaterial
    val reinforcedWood = EnumHelper.addToolMaterial("ReinforcedWood", 1, Item.ToolMaterial.WOOD.maxUses * 5, 4.0F, 1.0F, 20).setRepairItem(ItemStack(Blocks.log))!!
    val reinforcedStone = EnumHelper.addToolMaterial("ReinforcedStone", 2, Item.ToolMaterial.STONE.maxUses * 7, 8.0F, 2.0F, 10).setRepairItem(ItemStack(Blocks.furnace))!!
    val reinforcedIron = EnumHelper.addToolMaterial("ReinforcedIron", 3, Item.ToolMaterial.IRON.maxUses * 11, 18.0F, 6.0F, 30).setRepairItem(ItemStack(Blocks.iron_block))!!
    val reinforcedGold = EnumHelper.addToolMaterial("ReinforcedGold", 3, Item.ToolMaterial.GOLD.maxUses * 13, 36.0F, 4.0F, 50).setRepairItem(ItemStack(Blocks.gold_block))!!
    val reinforcedDiamond = EnumHelper.addToolMaterial("ReinforcedDiamond", 4, Item.ToolMaterial.EMERALD.maxUses * 15, 24.0F, 8.0F, 40).setRepairItem(ItemStack(Blocks.diamond_block))!!
    val paxelIron = EnumHelper.addToolMaterial("paxelIron", 4, reinforcedIron.maxUses * 5, 18.0F, 6.0F, 30).setRepairItem(ItemStack(Blocks.iron_block))!!
    val paxelGold = EnumHelper.addToolMaterial("paxelGold", 4, reinforcedGold.maxUses * 5, 36.0F, 4.0F, 50).setRepairItem(ItemStack(Blocks.gold_block))!!
    val paxelDiamond = EnumHelper.addToolMaterial("paxelDiamond", 5, reinforcedDiamond.maxUses * 5, 24.0F, 8.0F, 40).setRepairItem(ItemStack(Blocks.diamond_block))!!

    //Toolのコレクション
    val Axe = LinkedList<ToolAxe>(listOf(ToolAxe(reinforcedWood, "Wooden"),
            ToolAxe(reinforcedStone, "Stone"), ToolAxe(reinforcedIron, "Iron"),
            ToolAxe(reinforcedGold, "Gold"), ToolAxe(reinforcedDiamond, "Diamond")))
    val Pickaxe = LinkedList<ToolPickaxe>(listOf(ToolPickaxe(reinforcedWood, "Wooden"),
            ToolPickaxe(reinforcedStone, "Stone"), ToolPickaxe(reinforcedIron, "Iron"),
            ToolPickaxe(reinforcedGold, "Gold"), ToolPickaxe(reinforcedDiamond, "Diamond")))
    val Shovel = LinkedList<ToolShovel>(listOf(ToolShovel(reinforcedWood, "Wooden"),
            ToolShovel(reinforcedStone, "Stone"), ToolShovel(reinforcedIron, "Iron"),
            ToolShovel(reinforcedGold, "Gold"), ToolShovel(reinforcedDiamond, "Diamond")))
    val Hoe = LinkedList<ToolHoe>(listOf(ToolHoe(reinforcedWood, "Wooden"),
            ToolHoe(reinforcedStone, "Stone"), ToolHoe(reinforcedIron, "Iron"),
            ToolHoe(reinforcedGold, "Gold"), ToolHoe(reinforcedDiamond, "Diamond")))
    val Sword = LinkedList<WeaponSword>(listOf(WeaponSword(reinforcedWood, "Wooden"),
            WeaponSword(reinforcedStone, "Stone"), WeaponSword(reinforcedIron, "Iron"),
            WeaponSword(reinforcedGold, "Gold"), WeaponSword(reinforcedDiamond, "Diamond")))
    val Paxel = LinkedList<ToolPaxel>(listOf(ToolPaxel(paxelIron, "iron"), ToolPaxel(paxelGold, "gold"), ToolPaxel(paxelDiamond, "diamond")))
    val BattleAxes = LinkedList<BattleAxe>(listOf(BattleAxe(reinforcedIron, "iron"), BattleAxe(reinforcedGold, "gold"), BattleAxe(reinforcedDiamond, "diamond")))
    val MiningHammers = LinkedList<MiningHammer>(listOf(MiningHammer(reinforcedIron, "iron"), MiningHammer(reinforcedGold, "gold"), MiningHammer(reinforcedDiamond, "diamond")))
    //弓
    val shortBow = ShortBow()
    val compositeBow = CompositeBow()
    val longBow = LongBow()

    //ToolRod
    val ToolRod = LinkedList<Item>(mutableListOf(
            ItemUtil.CreateItem(name = "woodenToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "stoneToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "ironToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "goldToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "diamondToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools)))
    //弓の中間素材
    val SuppleStick = ItemUtil.CreateItem(name = "suppleStick", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools)
    val CompositeStick = ItemUtil.CreateItem(name = "compositeStick", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools)
    val HighTensionString = ItemUtil.CreateItem(name = "highTensionString", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools)
    val StrongString = ItemUtil.CreateItem(name = "strongString", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools)
    val SlimeBlock = BlockUtil.CreateBlock(name = "slimeBlock", modID = ReinforcedToolsCore.Domain, material = Material.rock).setCreativeTab(tabReinforcedTools)!!

    //エンチャントレベルの配列
    private val levels = arrayOf(0, 0, 5, 15, 10)

    //Init関数s
    fun preInit(event: FMLPreInitializationEvent) {
        registerListItems(Axe)
        registerListItems(Pickaxe)
        registerListItems(Shovel)
        registerListItems(Hoe)
        registerListItems(Sword)
        registerListItems(Paxel)
        registerListItems(BattleAxes)
        registerListItems(MiningHammers)
        GameRegistry.registerItem(shortBow, shortBow.unlocalizedName)
        GameRegistry.registerItem(compositeBow, compositeBow.unlocalizedName)
        GameRegistry.registerItem(longBow, longBow.unlocalizedName)
        registerListItems(ToolRod)
        arrayOf(SuppleStick, CompositeStick, HighTensionString, StrongString).forEach {
            GameRegistry.registerItem(it, it.unlocalizedName)
        }
        GameRegistry.registerBlock(SlimeBlock, SlimeBlock.unlocalizedName)
    }

    fun init(event: FMLInitializationEvent) {
        val materialBlocks = arrayOf(ItemStack(Blocks.log), ItemStack(Blocks.furnace), ItemStack(Blocks.iron_block), ItemStack(Blocks.gold_block), ItemStack(Blocks.diamond_block))
        val materialRods = arrayOf(ItemStack(ToolRod[0]), ItemStack(ToolRod[1]), ItemStack(ToolRod[2]), ItemStack(ToolRod[3]), ItemStack(ToolRod[4]))
        addToolRodRecipe(ToolRod, materialBlocks)
        val materials = if (ReinforcedToolsCore.isHardRecipe()) {
            materialRods
        } else {
            materialBlocks
        }
        addReinforcedToolRecipe(Axe, materials)
        addReinforcedToolRecipe(Pickaxe, materials)
        addReinforcedToolRecipe(Shovel, materials)
        addReinforcedToolRecipe(Hoe, materials)
        addReinforcedToolRecipe(Sword, materials)
        addReinforcedToolRecipe(Paxel, materials)
        addReinforcedToolRecipe(BattleAxes, materials)
        addReinforcedToolRecipe(MiningHammers, materials)
        addBowAndMaterialRecipe()
    }

    //Util関数s
    //コレクションのアイテム登録
    private fun registerListItems(items: List<Item>) {
        for (item in items) {
            GameRegistry.registerItem(item, item.unlocalizedName)
        }
    }

    //Toolのレシピ追加まとめ
    private fun addReinforcedToolRecipe(reinforcedTools: List<IReinforcedTools>, materials: Array<ItemStack>) {
        addToolRecipe(reinforcedTools, materials)
        addToolEnchantRecipe(reinforcedTools, materials)
        addSilkTouchRecipe(reinforcedTools)
    }

    //Toolのレシピ追加
    private fun addToolRecipe(reinforcedTools: List<IReinforcedTools>, rodItems: Array<ItemStack>) {
        val type = reinforcedTools[0].getToolType()
        val recipe = type.recipe
        when (type) {
            EnumToolType.AXE, EnumToolType.PICKAXE, EnumToolType.SHOVEL, EnumToolType.SWORD, EnumToolType.HOE -> {
                val materials = type.getMaterials()
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool)
                    GameRegistry.addRecipe(ItemStack(tool as Item), recipe[0], recipe[1], recipe[2], 'H', materials[index], 'R', rodItems[index])
                }
            }
            EnumToolType.PAXEL -> {
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool) + 2
                    GameRegistry.addRecipe(ItemStack(tool as Item), recipe[0], recipe[1], recipe[2], 'P', Pickaxe[index], 'A', Axe[index], 'S', Shovel[index], 'R', ToolRod[index])
                }
            }
            EnumToolType.BATTLEAXE -> {
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool) + 2
                    GameRegistry.addRecipe(ItemStack(tool as Item), recipe[0], recipe[1], recipe[2], 'A', Axe[index], 'S', Sword[index], 'R', ToolRod[index])
                }
            }
            EnumToolType.MININGHAMMER -> {
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool) + 2
                    GameRegistry.addRecipe(ItemStack(tool as Item), recipe[0], recipe[1], recipe[2], 'P', Pickaxe[index], 'S', Shovel[index], 'R', ToolRod[index])
                }
            }
        }
    }

    //Toolのエンチャントレシピ追加
    private fun addToolEnchantRecipe(reinforcedTools: List<IReinforcedTools>, materials: Array<ItemStack>) {
        val type = reinforcedTools[0].getToolType()
        for (tool in reinforcedTools) {
            var index = reinforcedTools.indexOf(tool)
            if (type.isHighLevelTool) index += 2
            val level = levels[index]
            if (level > 0) {
                GameRegistry.addRecipe(tool.getEnchanted(tool as Item, level), "MMM", "MTM", "MMM", 'M', materials[index], 'T', ItemStack(tool, 2, OreDictionary.WILDCARD_VALUE))
            }
        }
    }

    private fun addSilkTouchRecipe(reinforcedTools: List<IReinforcedTools>) {
        val type = reinforcedTools[0].getToolType()
        reinforcedTools.forEachIndexed { index, tool ->
            val i = if (type.isHighLevelTool) index + 2 else index
            val level = levels[i]
            if (level > 0){
                var stack = ItemStack(tool as Item)
                stack.addEnchantment(Enchantment.silkTouch, 1)
                GameRegistry.addRecipe(stack, "MMM", "MTM", "MMM", 'M', Blocks.emerald_block, 'T', ItemStack(tool))
                /*stack = tool.getEnchanted(tool, level)
                val stack_silk = stack.copy()
                stack_silk.addEnchantment(Enchantment.silkTouch, 1)
                GameRegistry.addRecipe(stack_silk, "MMM", "MTM", "MMM", 'M', Blocks.emerald_block, 'T', stack)*/
            }
        }
    }

    //ToolRodのレシピ追加
    private fun addToolRodRecipe(toolRods: List<Item>, materials: Array<ItemStack>) {
        for (rod in toolRods) {
            val index = toolRods.indexOf(rod)
            if (ReinforcedToolsCore.isHardRecipe()) {
                GameRegistry.addRecipe(ItemStack(rod), "MM", "MM", "MM", 'M', materials[index])
            } else {
                GameRegistry.addRecipe(ItemStack(rod, 2, 0), "M", "M", "M", 'M', materials[index])
            }
        }
    }

    private fun addBowAndMaterialRecipe() {
        if (ReinforcedToolsCore.isHardRecipe()) {
            GameRegistry.addRecipe(ItemStack(SlimeBlock), "SSS", "SSS", "SSS", 'S', Items.magma_cream)
            GameRegistry.addRecipe(ItemStack(SuppleStick), "SRS", "SRS", "SRS", 'R', ToolRod[0], 'S', Items.magma_cream)
            GameRegistry.addRecipe(ItemStack(CompositeStick), "ISG", "IBC", "ISD", 'I', ToolRod[2], 'S', SlimeBlock, 'B', Items.bone, 'G', ToolRod[3], 'D', ToolRod[4], 'C', Items.ender_eye)
            GameRegistry.addRecipe(ItemStack(HighTensionString), "SCS", "ECE", "SCS", 'S', Items.string, 'C', SlimeBlock, 'E', Items.spider_eye)
            GameRegistry.addRecipe(ItemStack(StrongString), "SAS", "SBS", "SAS", 'S', HighTensionString, 'A', Items.ghast_tear, 'B', Items.leather)
        } else {
            GameRegistry.addRecipe(ItemStack(SlimeBlock), "SSS", "SSS", "SSS", 'S', Items.slime_ball)
            GameRegistry.addRecipe(ItemStack(SuppleStick), "SRS", "SRS", "SRS", 'R', Items.stick, 'S', SlimeBlock)
            GameRegistry.addRecipe(ItemStack(CompositeStick), "ISG", "IBC", "ISD", 'I', Blocks.iron_block, 'S', SuppleStick, 'C', SlimeBlock, 'B', Items.bone, 'G', ToolRod[3], 'D', ToolRod[4])
            GameRegistry.addRecipe(ItemStack(HighTensionString), "SCS", "SCS", "SCS", 'S', Items.string, 'C', Items.slime_ball)
            GameRegistry.addRecipe(ItemStack(StrongString), "SAS", "SBS", "SAS", 'S', HighTensionString, 'A', Items.gunpowder, 'B', Items.leather)
        }
        GameRegistry.addRecipe(ItemStack(shortBow), " RS", "RBS", " RS", 'R', SuppleStick, 'S', HighTensionString, 'B', Items.bow)
        GameRegistry.addRecipe(ItemStack(compositeBow), " RS", "RBS", " RS", 'R', CompositeStick, 'S', StrongString, 'B', Items.bow)
        GameRegistry.addRecipe(ItemStack(longBow), " RS", "RBS", " RS", 'R', CompositeStick, 'S', StrongString, 'B', compositeBow)
        val enchantedBows = arrayOf(ItemStack(shortBow), ItemStack(compositeBow), ItemStack(longBow))
        for (itemStack in enchantedBows) {
            itemStack.addEnchantment(Enchantment.power, 10)
            itemStack.addEnchantment(Enchantment.punch, 10)
            itemStack.addEnchantment(Enchantment.unbreaking, 10)
        }
        val materials = if (ReinforcedToolsCore.isHardRecipe()) {
            arrayOf(ItemStack(ToolRod[2]), ItemStack(ToolRod[3]), ItemStack(ToolRod[4]))
        } else {
            arrayOf(ItemStack(Blocks.iron_block), ItemStack(Blocks.gold_block), ItemStack(Blocks.diamond_block))
        }
        enchantedBows.forEach {
            GameRegistry.addRecipe(it, "IGD", "IBD", "IGD", 'I', materials[0], 'G', materials[1], 'D', materials[2], 'B', ItemStack(it.item, 1, OreDictionary.WILDCARD_VALUE))
        }
    }
}