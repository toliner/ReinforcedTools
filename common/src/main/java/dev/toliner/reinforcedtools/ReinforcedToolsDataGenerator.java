package dev.toliner.reinforcedtools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.Identifier;

import com.google.common.hash.Hashing;
import org.jspecify.annotations.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/** Generates recipes, item models, tags, and translations for the mod. */
public final class ReinforcedToolsDataGenerator {
    private ReinforcedToolsDataGenerator() {
    }

    static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected one output directory argument");
        }

        SharedConstants.setVersion(DetectedVersion.BUILT_IN);
        var generator = new DataGenerator.Uncached(Path.of(args[0]));
        generator.getVanillaPack(true).addProvider(ReinforcedToolsDataProvider::new);
        generator.run();
    }

    private static final class ReinforcedToolsDataProvider implements DataProvider {
        private final PackOutput.PathProvider recipePath;
        private final PackOutput.PathProvider modelPath;
        private final PackOutput.PathProvider tagPath;
        private final PackOutput.PathProvider langPath;
        private final Path gameTestStructurePath;
        private final Map<Path, JsonObject> files = new LinkedHashMap<>();

        private ReinforcedToolsDataProvider(PackOutput output) {
            recipePath = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
            modelPath = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models/item");
            tagPath = output.createPathProvider(PackOutput.Target.DATA_PACK, "tags/item");
            langPath = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang");
            gameTestStructurePath = output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(ReinforcedTools.MOD_ID)
                .resolve("structure")
                .resolve("empty.nbt");

            generateToolRecipes();
            generateRepairKitRecipes();
            generateModels();
            generateTags();
            generateLanguage("en_us", false);
            generateLanguage("ja_jp", true);
        }

        @Override
        public @NonNull CompletableFuture<?> run(CachedOutput cache) {
            var jsonWrites = files.entrySet().stream()
                .map(entry -> DataProvider.saveStable(cache, entry.getValue(), entry.getKey()))
                .toArray(CompletableFuture[]::new);
            try {
                byte[] structure = emptyGameTestStructure();
                cache.writeIfNeeded(gameTestStructurePath, structure, Hashing.sha1().hashBytes(structure));
            } catch (IOException exception) {
                throw new UncheckedIOException("Failed to generate the NeoForge GameTest structure", exception);
            }
            return CompletableFuture.allOf(jsonWrites);
        }

        @Override
        public @NonNull String getName() {
            return "ReinforcedTools resources";
        }

        private void generateToolRecipes() {
            for (var material : ReinforcedToolMaterial.values()) {
                for (var type : ReinforcedToolType.values()) {
                    String resultId = modId(ReinforcedTools.toolId(material, type));
                    if (material == ReinforcedToolMaterial.NETHERITE) {
                        addSmithingRecipe(
                            ReinforcedTools.toolId(material, type) + "_from_vanilla_netherite",
                            vanillaToolId(material, type),
                            resultId
                        );
                        addSmithingRecipe(
                            ReinforcedTools.toolId(material, type) + "_from_reinforced_diamond",
                            modId(ReinforcedTools.toolId(ReinforcedToolMaterial.DIAMOND, type)),
                            resultId
                        );
                        continue;
                    }

                    String vanillaBase = vanillaToolId(material, type);
                    if (type == ReinforcedToolType.PICKAXE || type == ReinforcedToolType.AXE) {
                        addShapedRecipe(
                            ReinforcedTools.toolId(material, type) + "_top",
                            List.of("AAA", " B ", "   "),
                            materialIngredient(material),
                            vanillaBase,
                            resultId,
                            1
                        );
                        addShapedRecipe(
                            ReinforcedTools.toolId(material, type) + "_cross",
                            List.of("A A", "ABA", "A A"),
                            materialIngredient(material),
                            vanillaBase,
                            resultId,
                            1
                        );
                    } else {
                        addShapedRecipe(
                            ReinforcedTools.toolId(material, type),
                            List.of("A A", " B ", "A A"),
                            materialIngredient(material),
                            vanillaBase,
                            resultId,
                            1
                        );
                    }
                }
            }
        }

        private void generateRepairKitRecipes() {
            for (var material : ReinforcedToolMaterial.values()) {
                String resultId = modId(ReinforcedTools.repairKitId(material));
                String materialIngredient = materialIngredient(material);
                String craftingTable = "minecraft:crafting_table";

                addShapedRecipe(
                    ReinforcedTools.repairKitId(material) + "_four",
                    List.of(" A ", "ABA", " A "),
                    materialIngredient,
                    craftingTable,
                    resultId,
                    4
                );
                addShapedRecipe(
                    ReinforcedTools.repairKitId(material) + "_six_rows",
                    List.of("AAA", " B ", "AAA"),
                    materialIngredient,
                    craftingTable,
                    resultId,
                    6
                );
                addShapedRecipe(
                    ReinforcedTools.repairKitId(material) + "_six_cross",
                    List.of("A A", "ABA", "A A"),
                    materialIngredient,
                    craftingTable,
                    resultId,
                    6
                );
                addShapedRecipe(
                    ReinforcedTools.repairKitId(material) + "_eight",
                    List.of("AAA", "ABA", "AAA"),
                    materialIngredient,
                    craftingTable,
                    resultId,
                    8
                );
            }
        }

        private void generateModels() {
            for (var material : ReinforcedToolMaterial.values()) {
                for (var type : ReinforcedToolType.values()) {
                    String itemId = ReinforcedTools.toolId(material, type);
                    addModel(itemId, "minecraft:item/" + material.vanillaPrefix() + "_" + type.id());
                }
                addModel(ReinforcedTools.repairKitId(material), "minecraft:item/" + material.modelItem());
            }
        }

        private void generateTags() {
            for (var type : ReinforcedToolType.values()) {
                JsonObject tag = new JsonObject();
                tag.addProperty("replace", false);
                JsonArray values = new JsonArray();
                for (var material : ReinforcedToolMaterial.values()) {
                    values.add(modId(ReinforcedTools.toolId(material, type)));
                }
                tag.add("values", values);
                files.put(tagPath.json(Identifier.withDefaultNamespace(typeTagName(type))), tag);
            }
        }

        private void generateLanguage(String language, boolean japanese) {
            JsonObject lang = new JsonObject();
            lang.addProperty("itemGroup." + ReinforcedTools.MOD_ID + ".tools", japanese ? "強化ツール" : "Reinforced Tools");
            for (var material : ReinforcedToolMaterial.values()) {
                for (var type : ReinforcedToolType.values()) {
                    String itemId = ReinforcedTools.toolId(material, type);
                    String name = japanese
                        ? "強化" + material.displayName() + type.displayName()
                        : "Reinforced " + material.displayName() + " " + type.displayName();
                    lang.addProperty("item." + ReinforcedTools.MOD_ID + "." + itemId, name);
                }
                String kitId = ReinforcedTools.repairKitId(material);
                lang.addProperty(
                    "item." + ReinforcedTools.MOD_ID + "." + kitId,
                    japanese ? "強化" + material.displayName() + "修理キット" : "Reinforced " + material.displayName() + " Repair Kit"
                );
            }
            files.put(langPath.json(Identifier.fromNamespaceAndPath(ReinforcedTools.MOD_ID, language)), lang);
        }

        private static byte[] emptyGameTestStructure() throws IOException {
            StringBuilder snbt = new StringBuilder("{DataVersion:2730,size:[8,8,8],data:[");
            boolean first = true;
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    for (int z = 0; z < 8; z++) {
                        if (!first) {
                            snbt.append(',');
                        }
                        first = false;
                        snbt.append("{pos:[").append(x).append(',').append(y).append(',').append(z).append("],state:\"minecraft:air\"}");
                    }
                }
            }
            snbt.append("],entities:[],palette:[\"minecraft:air\"]}");

            try (var output = new ByteArrayOutputStream()) {
                NbtIo.writeCompressed(NbtUtils.snbtToStructure(snbt.toString()), output);
                return output.toByteArray();
            } catch (com.mojang.brigadier.exceptions.CommandSyntaxException exception) {
                throw new IllegalStateException("The built-in GameTest structure is invalid", exception);
            }
        }

        private void addShapedRecipe(
            String name,
            List<String> pattern,
            String materialIngredient,
            String baseIngredient,
            String resultId,
            int count
        ) {
            JsonObject recipe = new JsonObject();
            recipe.addProperty("type", "minecraft:crafting_shaped");
            recipe.addProperty("category", "equipment");

            JsonObject key = new JsonObject();
            key.addProperty("A", materialIngredient);
            key.addProperty("B", baseIngredient);
            recipe.add("key", key);

            JsonArray patternArray = new JsonArray();
            pattern.forEach(patternArray::add);
            recipe.add("pattern", patternArray);
            recipe.add("result", result(resultId, count));
            files.put(recipePath.json(modIdentifier(name)), recipe);
        }

        private void addSmithingRecipe(String name, String baseId, String resultId) {
            JsonObject recipe = new JsonObject();
            recipe.addProperty("type", "minecraft:smithing_transform");
            recipe.addProperty("template", "minecraft:netherite_upgrade_smithing_template");
            recipe.addProperty("base", baseId);
            recipe.addProperty("addition", "#minecraft:netherite_tool_materials");
            recipe.add("result", result(resultId, 1));
            files.put(recipePath.json(modIdentifier(name)), recipe);
        }

        private void addModel(String itemId, String parent) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", parent);
            files.put(modelPath.json(modIdentifier(itemId)), model);
        }

        private static JsonObject result(String id, int count) {
            JsonObject result = new JsonObject();
            result.addProperty("id", id);
            if (count != 1) {
                result.addProperty("count", count);
            }
            return result;
        }

        private static String materialIngredient(ReinforcedToolMaterial material) {
            return "#" + material.repairItems().location();
        }

        private static String vanillaToolId(ReinforcedToolMaterial material, ReinforcedToolType type) {
            return "minecraft:" + material.vanillaPrefix() + "_" + type.id();
        }

        private static String modId(String path) {
            return ReinforcedTools.MOD_ID + ":" + path;
        }

        private static Identifier modIdentifier(String path) {
            return Identifier.fromNamespaceAndPath(ReinforcedTools.MOD_ID, path);
        }

        private static String typeTagName(ReinforcedToolType type) {
            return switch (type) {
                case SWORD -> "swords";
                case PICKAXE -> "pickaxes";
                case AXE -> "axes";
                case SHOVEL -> "shovels";
                case HOE -> "hoes";
            };
        }
    }
}
