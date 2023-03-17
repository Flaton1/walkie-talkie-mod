package fr.flaton.walkietalkie.network.packet.c2s;

import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.screen.SpeakerScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class UpdateSpeakerC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {

        int index = buf.readInt();
        boolean status = buf.readBoolean();

        ScreenHandler var3 = player.currentScreenHandler;

        if (var3 instanceof SpeakerScreenHandler speakerScreenHandler) {

            boolean activate = speakerScreenHandler.isActivate();
            int canal = speakerScreenHandler.getCanal();

            if (index == 0) {

                activate = !activate;

            } else if (index == 1) {

                if (status) {
                    canal = Math.min(canal + 1, ModConfig.maxCanal);
                } else {
                    canal = Math.max(canal - 1, 1);
                }

            }

            speakerScreenHandler.setPropertyDelegate(activate , canal);
        }
    }
}
