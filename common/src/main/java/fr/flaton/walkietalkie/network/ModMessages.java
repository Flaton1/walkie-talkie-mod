package fr.flaton.walkietalkie.network;

import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.network.packet.c2s.ActivateButtonC2SPacket;
import fr.flaton.walkietalkie.network.packet.c2s.CanalButtonC2SPacket;
import fr.flaton.walkietalkie.network.packet.c2s.MuteButtonC2SPacket;
import fr.flaton.walkietalkie.network.packet.c2s.UpdateSpeakerC2SPacket;
import fr.flaton.walkietalkie.network.packet.s2c.ButtonS2CPacket;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier MUTE_PRESSED = new Identifier(WalkieTalkie.MOD_ID, "mutebuttonpressed");
    public static final Identifier ACTIVATE_PRESSED = new Identifier(WalkieTalkie.MOD_ID, "activatebuttonpressed");
    public static final Identifier CANAL_PRESSED = new Identifier(WalkieTalkie.MOD_ID, "canalbuttonpressed");
    public static final Identifier BUTTON_PRESSED_RESPONSE = new Identifier(WalkieTalkie.MOD_ID, "buttonpressedresponse");

    public static final Identifier UPDATE_SPEAKER = new Identifier(WalkieTalkie.MOD_ID, "updatespeaker");

    public static void registerC2SPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, MUTE_PRESSED, MuteButtonC2SPacket::receive);

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, ACTIVATE_PRESSED, ActivateButtonC2SPacket::receive);
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, CANAL_PRESSED, CanalButtonC2SPacket::receive);

        NetworkManager.registerReceiver(NetworkManager.Side.C2S, UPDATE_SPEAKER, UpdateSpeakerC2SPacket::receive);

    }

    public static void registerS2CPackets() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, BUTTON_PRESSED_RESPONSE, ButtonS2CPacket::receive);
    }

}
