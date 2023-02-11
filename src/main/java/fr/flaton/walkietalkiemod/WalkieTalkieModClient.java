package fr.flaton.walkietalkiemod;

import fr.flaton.walkietalkiemod.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class WalkieTalkieModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
    }
}
