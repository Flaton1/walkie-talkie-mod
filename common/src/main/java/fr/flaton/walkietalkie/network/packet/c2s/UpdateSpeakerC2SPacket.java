package fr.flaton.walkietalkie.network.packet.c2s;

import fr.flaton.walkietalkie.Util;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.screen.SpeakerScreenHandler;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateSpeakerC2SPacket {

    public static void receive(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ServerPlayerEntity player = (ServerPlayerEntity) packetContext.getPlayer();

        int index = packetByteBuf.readInt();
        boolean status = packetByteBuf.readBoolean();

        ScreenHandler screenHandler = player.currentScreenHandler;

        if (screenHandler == null) {
            return;
        }

        if (screenHandler instanceof SpeakerScreenHandler speakerScreenHandler) {

            boolean activate = speakerScreenHandler.isActivate();
            int canal = speakerScreenHandler.getCanal();

            switch (index) {
                case 0 -> activate = !activate;
                case 1 -> {
                    if (status) {
                        canal = Util.loop(canal + 1, 1, ModConfig.maxCanal);
                    } else {
                        canal = Util.loop(canal - 1, 1, ModConfig.maxCanal);
                    }
                }
            }
            speakerScreenHandler.setPropertyDelegate(activate , canal);
        }
    }
}
