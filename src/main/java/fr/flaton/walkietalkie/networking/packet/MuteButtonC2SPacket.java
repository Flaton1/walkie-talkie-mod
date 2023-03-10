package fr.flaton.walkietalkie.networking.packet;

import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class MuteButtonC2SPacket {


    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {

        ItemStack stack = player.getStackInHand(getHandUse(player));

        if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            return;
        }

        stack.getNbt().putBoolean(WalkieTalkieItem.NBT_KEY_MUTE, !stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));

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
