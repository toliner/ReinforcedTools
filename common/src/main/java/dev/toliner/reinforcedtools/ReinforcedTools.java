package dev.toliner.reinforcedtools;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.minecraft.resources.Identifier;

/** Common entry point shared by the Fabric and NeoForge loaders. */
public final class ReinforcedTools {
    public static final String MOD_ID = "reinforced_tools";

    private ReinforcedTools() {
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void initialize(BalmRegistrars registrars) {
        // Register common content here through Balm as the mod grows.
    }
}
