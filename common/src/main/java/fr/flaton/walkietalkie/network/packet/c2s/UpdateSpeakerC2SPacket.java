package fr.flaton.walkietalkie.network.packet.c2s;

import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.screen.SpeakerScreenHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateSpeakerC2SPacket {

    public static void receive(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ServerPlayerEntity player = (ServerPlayerEntity) packetContext.getPlayer();

        int index = packetByteBuf.readInt();
        boolean status = packetByteBuf.readBoolean();

        ScreenHandler var3 = player.currentScreenHandler;

        if (var3 instanceof SpeakerScreenHandler speakerScreenHandler) {

            boolean activate = speakerScreenHandler.isActivate();
            int canal = speakerScreenHandler.getCanal();

            if (index == 0) {

                activate = !activate;

            } else if (index == 1) {

                if (status) {
                    canal = loop(canal + 1, 1, ModConfig.maxCanal);
                } else {
                    canal = loop(canal - 1, 1, ModConfig.maxCanal);
                }
            }
            speakerScreenHandler.setPropertyDelegate(activate , canal);
        }
    }

    private static int loop(int value, int min, int max) {

        if (value > max) {
            value = min;
        } else if (value < min) {
            value = max;
        }
        return value;

    }
}
