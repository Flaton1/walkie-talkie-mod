package fr.flaton.walkietalkiemod.networking.packet;

import fr.flaton.walkietalkiemod.gui.WalkieTalkieGui;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ButtonS2CPacket {



    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packet, PacketSender packetSender) {

        int canal = packet.readInt();
        boolean mute = packet.readBoolean();
        boolean active = packet.readBoolean();

        WalkieTalkieGui.setCanalText(canal);
        WalkieTalkieGui.getMuteButton().updateIcon(mute);
        WalkieTalkieGui.getOnOffButton().updateIcon(active);


    }
}
