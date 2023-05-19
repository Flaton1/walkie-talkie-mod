package fr.flaton.walkietalkie.fabric;

import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class WalkieTalkieFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig config = new ModConfig(FabricLoader.getInstance().getConfigDir());
        config.loadModConfig();

        WalkieTalkie.init();
    }
}