package dev.toliner.reinforcedtools.fabric;

import dev.toliner.reinforcedtools.ReinforcedTools;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.fabric.platform.runtime.FabricLoadContext;
import net.fabricmc.api.ModInitializer;

public final class FabricReinforcedTools implements ModInitializer {
    @Override
    public void onInitialize() {
        Balm.initializeMod(ReinforcedTools.MOD_ID, FabricLoadContext.INSTANCE, ReinforcedTools::initialize);
    }
}
