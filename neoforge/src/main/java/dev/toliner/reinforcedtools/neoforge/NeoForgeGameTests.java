package dev.toliner.reinforcedtools.neoforge;

import dev.toliner.reinforcedtools.ReinforcedTools;
import dev.toliner.reinforcedtools.gametest.ReinforcedToolsGameTestAssertions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

import java.util.function.Consumer;

/** NeoForge's dynamic GameTest registration for the shared Reinforced Tools checks. */
final class NeoForgeGameTests {
    private static final Identifier EMPTY_STRUCTURE = ReinforcedTools.id("empty");

    private NeoForgeGameTests() {
    }

    static void register(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> environment = event.registerEnvironment(
            ReinforcedTools.id("reinforced_tools"), new TestEnvironmentDefinition.AllOf()
        );
        register(event, environment, "tool_stats_match_specification", ReinforcedToolsGameTestAssertions::verifyToolStats);
        register(event, environment, "generated_recipes_are_available", ReinforcedToolsGameTestAssertions::verifyRecipes);
        register(event, environment, "repair_kit_repairs_only_matching_tools", ReinforcedToolsGameTestAssertions::verifyRepairKits);
    }

    private static void register(
        RegisterGameTestsEvent event,
        Holder<TestEnvironmentDefinition<?>> environment,
        String name,
        Consumer<GameTestHelper> test
    ) {
        var data = new TestData<>(environment, EMPTY_STRUCTURE, 100, 0, true);
        event.registerTest(ReinforcedTools.id(name), new ReinforcedToolsGameTestInstance(data, test));
    }

    private static final class ReinforcedToolsGameTestInstance extends GameTestInstance {
        private final Consumer<GameTestHelper> test;

        private ReinforcedToolsGameTestInstance(TestData<Holder<TestEnvironmentDefinition<?>>> data, Consumer<GameTestHelper> test) {
            super(data);
            this.test = test;
        }

        @Override
        public void run(GameTestHelper helper) {
            test.accept(helper);
            helper.succeed();
        }

        @Override
        public MapCodec<? extends GameTestInstance> codec() {
            throw new UnsupportedOperationException("Reinforced Tools GameTests are registered dynamically");
        }

        @Override
        protected MutableComponent typeDescription() {
            return Component.literal("Reinforced Tools");
        }
    }
}
