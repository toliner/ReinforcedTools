package dev.toliner.reinforcedtools.fabric.gametest;

import dev.toliner.reinforcedtools.gametest.ReinforcedToolsGameTestAssertions;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

/** Fabric GameTest entry points for the Reinforced Tools specification. */
public final class ReinforcedToolsGameTests {
    @GameTest
    public void toolStatsMatchSpecification(GameTestHelper helper) {
        ReinforcedToolsGameTestAssertions.verifyToolStats(helper);
        helper.succeed();
    }

    @GameTest
    public void generatedRecipesAreAvailable(GameTestHelper helper) {
        ReinforcedToolsGameTestAssertions.verifyRecipes(helper);
        helper.succeed();
    }

    @GameTest
    public void repairKitRepairsOnlyMatchingTools(GameTestHelper helper) {
        ReinforcedToolsGameTestAssertions.verifyRepairKits(helper);
        helper.succeed();
    }
}
