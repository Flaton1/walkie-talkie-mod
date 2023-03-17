package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import fr.flaton.walkietalkie.client.gui.screen.SpeakerScreen;
import fr.flaton.walkietalkie.network.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class WalkieTalkieClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.SPEAKER_SCREEN_HANDLER, SpeakerScreen::new);
    }
}
