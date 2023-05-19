package fr.flaton.walkietalkie.network.packet.c2s;

import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.Util;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.network.ModMessages;
import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class MuteButtonC2SPacket {

    public static void receive(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ServerPlayerEntity player = (ServerPlayerEntity) packetContext.getPlayer();

        ItemStack stack = player.getStackInHand(Util.getHandUse(player));

        if (!stack.getItem().getClass().equals(WalkieTalkieItem.class)) {
            return;
        }

        stack.getNbt().putBoolean(WalkieTalkieItem.NBT_KEY_MUTE, !stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));

        PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
        packet.writeItemStack(stack);
        NetworkManager.sendToPlayer(player, ModMessages.BUTTON_PRESSED_RESPONSE, packet);

    }
}
