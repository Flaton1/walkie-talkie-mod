package fr.flaton.walkietalkie.network;

import fr.flaton.walkietalkie.Constants;
import fr.flaton.walkietalkie.network.packet.c2s.*;
import fr.flaton.walkietalkie.network.packet.s2c.UpdateWalkieTalkieS2CPacket;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier UPDATE_WALKIETALKIE_S2C = new Identifier(Constants.MOD_ID, "updatewalkietalkie_s2c");

    public static final Identifier UPDATE_WALKIETALKIE_C2S = new Identifier(Constants.MOD_ID, "updatewalkietalkie_c2s");
    public static final Identifier UPDATE_SPEAKER_C2S = new Identifier(Constants.MOD_ID, "updatespeaker_c2s");

    public static final Identifier ACTIVATE_KEY_PRESSED_C2S = new Identifier(Constants.MOD_ID, "activatekeypressed_c2s");

    public static void registerC2SPackets() {

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, UPDATE_WALKIETALKIE_C2S, UpdateWalkieTalkieC2SPacket::receive);
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, UPDATE_SPEAKER_C2S, UpdateSpeakerC2SPacket::receive);

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ACTIVATE_KEY_PRESSED_C2S, ActivateKeyPressedC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, UPDATE_WALKIETALKIE_S2C, UpdateWalkieTalkieS2CPacket::receive);
    }

}
