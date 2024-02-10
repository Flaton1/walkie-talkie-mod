package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.client.KeyBindings;
import fr.flaton.walkietalkie.network.ModMessages;

public class WalkieTalkieClient {

    public static void init() {
        ModMessages.registerS2CPackets();
        KeyBindings.register();
    }
}
