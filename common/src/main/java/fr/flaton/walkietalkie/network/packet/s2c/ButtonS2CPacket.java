package fr.flaton.walkietalkie.network.packet.s2c;

import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.client.gui.screen.WalkieTalkieScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class ButtonS2CPacket {

    public static void receive(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ItemStack stack = packetByteBuf.readItemStack();

        WalkieTalkieScreen.getInstance().checkButtons(stack);
    }
}
