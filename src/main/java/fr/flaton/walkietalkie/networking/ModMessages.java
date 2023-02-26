package fr.flaton.walkietalkie.networking;

import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.networking.packet.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier MUTE_PRESSED = new Identifier(WalkieTalkie.MOD_ID, "mutebuttonpressed");
    public static final Identifier ACTIVATE_PRESSED = new Identifier(WalkieTalkie.MOD_ID, "activatebuttonpressed");
    public static final Identifier CANAL_PRESSED = new Identifier(WalkieTalkie.MOD_ID, "canalbuttonpressed");
    public static final Identifier BUTTON_PRESSED_RESPONSE = new Identifier(WalkieTalkie.MOD_ID, "buttonpressedresponse");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MUTE_PRESSED, MuteButtonC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ACTIVATE_PRESSED, ActivateButtonC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CANAL_PRESSED, CanalButtonC2SPacket::receive);

    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(BUTTON_PRESSED_RESPONSE, ButtonS2CPacket::receive);

    }

}
