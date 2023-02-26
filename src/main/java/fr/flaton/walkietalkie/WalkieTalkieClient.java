package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class WalkieTalkieClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
    }
}
