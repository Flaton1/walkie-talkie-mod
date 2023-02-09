package fr.flaton.talkiewalkiemod.networking.packet;

import fr.flaton.talkiewalkiemod.gui.TalkieWalkieGui;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ButtonS2CPacket {



    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packet, PacketSender packetSender) {

        int canal = packet.readInt();
        boolean mute = packet.readBoolean();
        boolean active = packet.readBoolean();

        TalkieWalkieGui.setCanalText(canal);
        TalkieWalkieGui.getMuteButton().updateIcon(mute);
        TalkieWalkieGui.getOnOffButton().updateIcon(active);


    }
}
