package fr.flaton.walkietalkie.network.packet.c2s;

import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.network.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class CanalButtonC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {

        ItemStack stack = player.getStackInHand(getHandUse(player));

        boolean upButton = packetByteBuf.readBoolean();

        if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            return;
        }

        int canal = stack.getNbt().getInt(WalkieTalkieItem.NBT_KEY_CANAL);

        if (upButton) {
            canal = Math.min(canal + 1, ModConfig.maxCanal);
        } else {
            canal = Math.max(canal - 1, 1);
        }

        stack.getNbt().putInt(WalkieTalkieItem.NBT_KEY_CANAL, canal);

        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeItemStack(stack);
        packetSender.sendPacket(ModMessages.BUTTON_PRESSED_RESPONSE, packet);
    }

    private static Hand getHandUse(ServerPlayerEntity player) {

        ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getStackInHand(Hand.OFF_HAND);

        if (mainHand.getItem() instanceof WalkieTalkieItem) {
            return Hand.MAIN_HAND;
        }
        if (offHand.getItem() instanceof WalkieTalkieItem) {
            return Hand.OFF_HAND;
        }
        return null;
    }



}
