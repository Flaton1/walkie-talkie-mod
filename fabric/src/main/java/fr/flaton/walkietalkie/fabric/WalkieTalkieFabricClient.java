package fr.flaton.walkietalkie.fabric;

import fr.flaton.walkietalkie.WalkieTalkieClient;
import fr.flaton.walkietalkie.client.gui.screen.SpeakerScreen;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WalkieTalkieFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WalkieTalkieClient.init();

        HandledScreens.register(ModScreenHandlers.SPEAKER.get(), SpeakerScreen::new);
    }
}
