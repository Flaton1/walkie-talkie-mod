package fr.flaton.walkietalkie.fabric;

import fr.flaton.walkietalkie.client.gui.screen.SpeakerScreen;
import fr.flaton.walkietalkie.network.ModMessages;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WalkieTalkieClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.SPEAKER.get(), SpeakerScreen::new);
    }
}
