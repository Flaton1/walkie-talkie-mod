package fr.flaton.walkietalkie.network.packet.c2s;

import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.Util;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.network.ModMessages;
import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class CanalButtonC2SPacket {

    public static void receive(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ServerPlayerEntity player = (ServerPlayerEntity) packetContext.getPlayer();

        ItemStack stack = player.getStackInHand(Util.getHandUse(player));

        boolean upButton = packetByteBuf.readBoolean();

        if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            return;
        }

        int canal = stack.getNbt().getInt(WalkieTalkieItem.NBT_KEY_CANAL);

        if (upButton) {
            canal = loop(canal + 1, 1, ModConfig.maxCanal);
        } else {
            canal = loop(canal - 1, 1, ModConfig.maxCanal);
        }

        stack.getNbt().putInt(WalkieTalkieItem.NBT_KEY_CANAL, canal);

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeItemStack(stack);
        NetworkManager.sendToPlayer(player, ModMessages.BUTTON_PRESSED_RESPONSE, packet);
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
