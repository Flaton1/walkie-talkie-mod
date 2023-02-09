package fr.flaton.talkiewalkiemod;

import fr.flaton.talkiewalkiemod.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class TalkieWalkieModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();
    }
}
