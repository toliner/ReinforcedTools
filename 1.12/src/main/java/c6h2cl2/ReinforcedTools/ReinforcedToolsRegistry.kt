package c6h2cl2.ReinforcedTools

import c6h2cl2.ReinforcedTools.Item.*
import c6h2cl2.YukariLib.Util.ItemUtil
import c6h2cl2.YukariLib.Util.RecipeManager
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.oredict.OreDictionary
import net.minecraftforge.registries.IForgeRegistry
import org.jetbrains.annotations.Contract
import java.util.*

/**
 * @author C6H2Cl2
 */
object ReinforcedToolsRegistry {
    //クリエタブ
    val tabReinforcedTools = object : CreativeTabs("reinforcedTools") {
        @Contract(pure = true)
        override fun getTabIconItem(): ItemStack {
            return Axe[4].getEnchanted(Axe[4], 15)
        }
    }

    //ToolMaterial
    val reinforcedWood = EnumHelper.addToolMaterial("ReinforcedWood", 1, Item.ToolMaterial.WOOD.maxUses * 5, 4.0F, 1.0F, 20)!!.setRepairItem(ItemStack(Blocks.LOG))!!
    val reinforcedStone = EnumHelper.addToolMaterial("ReinforcedStone", 2, Item.ToolMaterial.STONE.maxUses * 7, 8.0F, 2.0F, 10)!!.setRepairItem(ItemStack(Blocks.FURNACE))!!
    val reinforcedIron = EnumHelper.addToolMaterial("ReinforcedIron", 3, Item.ToolMaterial.IRON.maxUses * 11, 18.0F, 6.0F, 30)!!.setRepairItem(ItemStack(Blocks.IRON_BLOCK))!!
    val reinforcedGold = EnumHelper.addToolMaterial("ReinforcedGold", 3, Item.ToolMaterial.GOLD.maxUses * 13, 36.0F, 4.0F, 50)!!.setRepairItem(ItemStack(Blocks.GOLD_BLOCK))!!
    val reinforcedDiamond = EnumHelper.addToolMaterial("ReinforcedDiamond", 4, Item.ToolMaterial.DIAMOND.maxUses * 15, 24.0F, 8.0F, 40)!!.setRepairItem(ItemStack(Blocks.DIAMOND_BLOCK))!!
    val paxelIron = EnumHelper.addToolMaterial("paxelIron", 4, reinforcedIron.maxUses * 5, 18.0F, 6.0F, 30)!!.setRepairItem(ItemStack(Blocks.IRON_BLOCK))!!
    val paxelGold = EnumHelper.addToolMaterial("paxelGold", 4, reinforcedGold.maxUses * 5, 36.0F, 4.0F, 50)!!.setRepairItem(ItemStack(Blocks.GOLD_BLOCK))!!
    val paxelDiamond = EnumHelper.addToolMaterial("paxelDiamond", 5, reinforcedDiamond.maxUses * 5, 24.0F, 8.0F, 40)!!.setRepairItem(ItemStack(Blocks.DIAMOND_BLOCK))!!

    //Toolのコレクション
    val Axe = LinkedList<ToolAxe>(mutableListOf(ToolAxe(reinforcedWood, "Wooden"),
            ToolAxe(reinforcedStone, "Stone"), ToolAxe(reinforcedIron, "Iron"),
            ToolAxe(reinforcedGold, "Gold"), ToolAxe(reinforcedDiamond, "Diamond")))
    val Pickaxe = LinkedList<ToolPickaxe>(mutableListOf(ToolPickaxe(reinforcedWood, "Wooden"),
            ToolPickaxe(reinforcedStone, "Stone"), ToolPickaxe(reinforcedIron, "Iron"),
            ToolPickaxe(reinforcedGold, "Gold"), ToolPickaxe(reinforcedDiamond, "Diamond")))
    val Shovel = LinkedList<ToolShovel>(mutableListOf(ToolShovel(reinforcedWood, "Wooden"),
            ToolShovel(reinforcedStone, "Stone"), ToolShovel(reinforcedIron, "Iron"),
            ToolShovel(reinforcedGold, "Gold"), ToolShovel(reinforcedDiamond, "Diamond")))
    val Hoe = LinkedList<ToolHoe>(mutableListOf(ToolHoe(reinforcedWood, "Wooden"),
            ToolHoe(reinforcedStone, "Stone"), ToolHoe(reinforcedIron, "Iron"),
            ToolHoe(reinforcedGold, "Gold"), ToolHoe(reinforcedDiamond, "Diamond")))
    val Sword = LinkedList<WeaponSword>(mutableListOf(WeaponSword(reinforcedWood, "Wooden"),
            WeaponSword(reinforcedStone, "Stone"), WeaponSword(reinforcedIron, "Iron"),
            WeaponSword(reinforcedGold, "Gold"), WeaponSword(reinforcedDiamond, "Diamond")))
    val Paxel = LinkedList<ToolPaxel>(mutableListOf(ToolPaxel(paxelIron, "iron"), ToolPaxel(paxelGold, "gold"), ToolPaxel(paxelDiamond, "diamond")))
    val BattleAxes = LinkedList<BattleAxe>(mutableListOf(BattleAxe(reinforcedIron, "iron"), BattleAxe(reinforcedGold, "gold"), BattleAxe(reinforcedDiamond, "diamond")))
    val MiningHammers = LinkedList<MiningHammer>(mutableListOf(MiningHammer(reinforcedIron, "iron"), MiningHammer(reinforcedGold, "gold"), MiningHammer(reinforcedDiamond, "diamond")))
    //弓
    /*val shortBow = ShortBow()
    val compositeBow = CompositeBow()
    val longBow = LongBow()*/

    //ToolRod
    val ToolRod = LinkedList<Item>(mutableListOf(
            ItemUtil.CreateItem(name = "woodenToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "stoneToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "ironToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "goldToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools),
            ItemUtil.CreateItem(name = "diamondToolRod", modID = ReinforcedToolsCore.Domain, creativeTabs = tabReinforcedTools)))
    //弓の中間素材
    /*val SuppleStick = ItemUtil.CreateItem(name = "suppleStick",modID = ReinforcedToolsCore.Domain,creativeTabs = tabReinforcedTools)
    val CompositeStick = ItemUtil.CreateItem(name = "compositeStick",modID = ReinforcedToolsCore.Domain,creativeTabs = tabReinforcedTools)
    val HighTensionString = ItemUtil.CreateItem(name = "highTensionString",modID = ReinforcedToolsCore.Domain,creativeTabs = tabReinforcedTools)
    val StrongString = ItemUtil.CreateItem(name = "strongString",modID = ReinforcedToolsCore.Domain,creativeTabs = tabReinforcedTools)
*/
    //エンチャントレベルの配列
    private val levels = arrayOf(0, 0, 5, 15, 10)

    //Init関数s
    fun preInit(event: FMLPreInitializationEvent) {

    }

    fun init(event: FMLInitializationEvent) {

    }

    @Suppress("UNUSED")
    @SubscribeEvent
    fun registerItems(event: RegistryEvent.Register<Item>) {
        val registry = event.registry
        registry.registerAll(
                *Axe.toTypedArray(), *Pickaxe.toTypedArray(), *Shovel.toTypedArray(), *Hoe.toTypedArray(), *Sword.toTypedArray(),
                *Paxel.toTypedArray(), *BattleAxes.toTypedArray(), *MiningHammers.toTypedArray(), *ToolRod.toTypedArray()
        )
    }

    @Suppress("UNUSED")
    @SubscribeEvent
    fun registerRecipes(event: Register<IRecipe>) {
        val registry = event.registry
        val materialBlocks = arrayOf(ItemStack(Blocks.LOG), ItemStack(Blocks.FURNACE), ItemStack(Blocks.IRON_BLOCK), ItemStack(Blocks.GOLD_BLOCK), ItemStack(Blocks.DIAMOND_BLOCK))
        val materialRods = arrayOf(ItemStack(ToolRod[0]), ItemStack(ToolRod[1]), ItemStack(ToolRod[2]), ItemStack(ToolRod[3]), ItemStack(ToolRod[4]))
        addToolRodRecipe(ToolRod, materialBlocks, registry)
        val materials = if (ReinforcedToolsCore.isHardRecipe()) {
            materialRods
        } else {
            materialBlocks
        }
        addReinforcedToolRecipe(Axe, materials, registry)
        addReinforcedToolRecipe(Pickaxe, materials, registry)
        addReinforcedToolRecipe(Shovel, materials, registry)
        addReinforcedToolRecipe(Hoe, materials, registry)
        addReinforcedToolRecipe(Sword, materials, registry)
        addReinforcedToolRecipe(Paxel, materials, registry)
        addReinforcedToolRecipe(BattleAxes, materials, registry)
        addReinforcedToolRecipe(MiningHammers, materials, registry)
    }

    @Suppress
    @SubscribeEvent
    fun registerModels(event: ModelRegistryEvent) {
        listOf<List<Item>>(Axe, Pickaxe, Shovel, Hoe, Sword, Paxel, BattleAxes, MiningHammers, ToolRod).forEach {
            it.forEach {
                ModelLoader.setCustomModelResourceLocation(it, 0, ModelResourceLocation(it.registryName, "inventory"))
            }
        }
    }

    //Util関数s
    //Toolのレシピ追加まとめ
    private fun addReinforcedToolRecipe(reinforcedTools: List<IReinforcedTools>, materials: Array<ItemStack>, registry: IForgeRegistry<IRecipe>) {
        addToolRecipe(reinforcedTools, materials, registry)
        addToolEnchantRecipe(reinforcedTools, materials, registry)
    }

    //Toolのレシピ追加
    private fun addToolRecipe(reinforcedTools: List<IReinforcedTools>, rodItems: Array<ItemStack>, registry: IForgeRegistry<IRecipe>) {
        val type = reinforcedTools[0].getToolType()
        val recipe = type.recipe
        when (type) {
            EnumToolType.AXE, EnumToolType.PICKAXE, EnumToolType.SHOVEL, EnumToolType.SWORD, EnumToolType.HOE -> {
                val materials = type.getMaterials()
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool)
                    val item = tool as Item
                    registry.register(
                            RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, item.unlocalizedName),
                                    output = ItemStack(item),
                                    recipeComponents = *arrayOf(recipe[0], recipe[1], recipe[2], 'H', materials[index], 'R', rodItems[index])
                            ).setRegistryName(ReinforcedToolsCore.Domain, tool.unlocalizedName)
                    )
                }
            }
            EnumToolType.PAXEL -> {
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool) + 2
                    val item = tool as Item
                    registry.register(
                            RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, item.unlocalizedName),
                                    output = ItemStack(item),
                                    recipeComponents = *arrayOf(recipe[0], recipe[1], recipe[2], 'P', Pickaxe[index], 'A', Axe[index], 'S', Shovel[index], 'R', ToolRod[index])
                            ).setRegistryName(ReinforcedToolsCore.Domain, tool.unlocalizedName)
                    )
                }
            }
            EnumToolType.BATTLEAXE -> {
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool) + 2
                    val item = tool as Item
                    registry.register(
                            RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, item.unlocalizedName),
                                    output = ItemStack(item),
                                    recipeComponents = *arrayOf(recipe[0], recipe[1], recipe[2], 'A', Axe[index], 'S', Sword[index], 'R', ToolRod[index])
                            ).setRegistryName(ReinforcedToolsCore.Domain, tool.unlocalizedName)
                    )
                }
            }
            EnumToolType.MININGHAMMER -> {
                for (tool in reinforcedTools) {
                    val index = reinforcedTools.indexOf(tool) + 2
                    val item = tool as Item
                    registry.register(
                            RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, item.unlocalizedName),
                                    output = ItemStack(item),
                                    recipeComponents = *arrayOf(recipe[0], recipe[1], recipe[2], 'P', Pickaxe[index], 'S', Shovel[index], 'R', ToolRod[index])
                            ).setRegistryName(ReinforcedToolsCore.Domain, tool.unlocalizedName)
                    )
                }
            }
        }
    }

    //Toolのエンチャントレシピ追加
    private fun addToolEnchantRecipe(reinforcedTools: List<IReinforcedTools>, materials: Array<ItemStack>, registry: IForgeRegistry<IRecipe>) {
        val type = reinforcedTools[0].getToolType()
        for (tool in reinforcedTools) {
            var index = reinforcedTools.indexOf(tool)
            if (type.isHighLevelTool) index += 2
            val level = levels[index]
            if (level > 0) {
                registry.register(
                        RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, (tool as Item).unlocalizedName + ".ench"),
                                output = tool.getEnchanted(tool, level),
                                recipeComponents = *arrayOf("MMM", "MTM", "MMM", 'M', materials[index], 'T', ItemStack(tool, 1, OreDictionary.WILDCARD_VALUE))
                        ).setRegistryName(ReinforcedToolsCore.Domain, tool.unlocalizedName + ".ench")
                )
            }
        }
    }

    //ToolRodのレシピ追加
    private fun addToolRodRecipe(toolRods: List<Item>, materials: Array<ItemStack>, registry: IForgeRegistry<IRecipe>) {
        toolRods.forEach {
            val index = toolRods.indexOf(it)
            if (ReinforcedToolsCore.isHardRecipe()) {
                registry.register(
                        RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, it.unlocalizedName),
                                output = ItemStack(it),
                                recipeComponents = *arrayOf("MM", "MM", "MM", 'M', materials[index])
                        ).setRegistryName(ReinforcedToolsCore.Domain, it.unlocalizedName)
                )
            } else {
                registry.register(
                        RecipeManager.getShapedRecipe(ResourceLocation(ReinforcedToolsCore.Domain, it.unlocalizedName),
                                output = ItemStack(it, 2, 0),
                                recipeComponents = *arrayOf("M", "M", "M", 'M', materials[index])
                        ).setRegistryName(ReinforcedToolsCore.Domain, it.unlocalizedName)
                )
            }
        }
    }
/*
    private fun addBowAndMaterialRecipe(){
        if (ReinforcedToolsCore.isHardRecipe()){
            GameRegistry.addRecipe(ItemStack(SuppleStick),"SRS","SRS","SRS",'R', ToolRod[0],'S',Items.MAGMA_CREAM)
            GameRegistry.addRecipe(ItemStack(CompositeStick),"ISG","IBC","ISD",'I', ToolRod[2],'S', Blocks.SLIME_BLOCK,'B',Items.BONE,'G', ToolRod[3],'D', ToolRod[4],'C',Items.ENDER_EYE)
            GameRegistry.addRecipe(ItemStack(HighTensionString),"SCS","ECE","SCS",'S',Items.STRING,'C', Blocks.SLIME_BLOCK,'E',Items.SPIDER_EYE)
            GameRegistry.addRecipe(ItemStack(StrongString),"SAS","SBS","SAS",'S', HighTensionString,'A',Items.GHAST_TEAR,'B',Items.LEATHER)
        }else{
            GameRegistry.addRecipe(ItemStack(SuppleStick),"SRS","SRS","SRS",'R', Items.STICK,'S', Blocks.SLIME_BLOCK)
            GameRegistry.addRecipe(ItemStack(CompositeStick),"ISG","IBC","ISD",'I',Blocks.IRON_BLOCK,'S', SuppleStick,'C', Blocks.SLIME_BLOCK,'B',Items.BONE,'G', ToolRod[3],'D', ToolRod[4])
            GameRegistry.addRecipe(ItemStack(HighTensionString),"SCS","SCS","SCS",'S',Items.STRING,'C',Items.SLIME_BALL)
            GameRegistry.addRecipe(ItemStack(StrongString),"SAS","SBS","SAS",'S', HighTensionString,'A',Items.GUNPOWDER,'B',Items.LEATHER)
        }
        GameRegistry.addRecipe(ItemStack(shortBow)," RS","RBS"," RS",'R', SuppleStick,'S', HighTensionString,'B',Items.BOW)
        GameRegistry.addRecipe(ItemStack(compositeBow)," RS","RBS"," RS",'R', CompositeStick,'S', StrongString,'B',Items.BOW)
        GameRegistry.addRecipe(ItemStack(longBow)," RS","RBS"," RS",'R', CompositeStick,'S', StrongString,'B', compositeBow)
        val enchantedBows = arrayOf(ItemStack(shortBow),ItemStack(compositeBow),ItemStack(longBow))
        enchantedBows.forEach {
            it.addEnchantment(Enchantments.POWER,10)
            it.addEnchantment(Enchantments.PUNCH,10)
            it.addEnchantment(Enchantments.UNBREAKING,10)
        }
        val materials = if (ReinforcedToolsCore.isHardRecipe()){
            arrayOf(ItemStack(ToolRod[2]),ItemStack(ToolRod[3]),ItemStack(ToolRod[4]))
        }else{
            arrayOf(ItemStack(Blocks.IRON_BLOCK), ItemStack(Blocks.GOLD_BLOCK), ItemStack(Blocks.DIAMOND_BLOCK))
        }
        enchantedBows.forEach {
            GameRegistry.addRecipe(it,"IGD","IBD","IGD",'I',materials[0],'G',materials[1],'D',materials[2],'B',ItemStack(it.item,1,OreDictionary.WILDCARD_VALUE))
        }
    }
    */
}