package fr.flaton.walkietalkie.fabric;

import fr.flaton.walkietalkie.Walkietalkie;
import net.fabricmc.api.ModInitializer;

public class WalkieTalkieFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Walkietalkie.init();
    }
}