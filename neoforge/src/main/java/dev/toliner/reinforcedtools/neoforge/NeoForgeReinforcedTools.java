package dev.toliner.reinforcedtools.neoforge;

import dev.toliner.reinforcedtools.ReinforcedTools;
import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.neoforge.platform.runtime.NeoForgeLoadContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

@Mod(ReinforcedTools.MOD_ID)
public final class NeoForgeReinforcedTools {
    public NeoForgeReinforcedTools(ModContainer modContainer, IEventBus modEventBus) {
        var context = new NeoForgeLoadContext(modContainer, modEventBus);
        Balm.initializeMod(ReinforcedTools.MOD_ID, context, ReinforcedTools::initialize);
        modEventBus.addListener(RegisterGameTestsEvent.class, NeoForgeGameTests::register);
    }
}
