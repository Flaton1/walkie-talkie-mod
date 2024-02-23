package fr.flaton.walkietalkie.network.packet.s2c;

import fr.flaton.walkietalkie.client.gui.screen.WalkieTalkieScreen;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;

public class UpdateWalkieTalkieS2CPacket {

    public static void receive(PacketByteBuf packetByteBuf, NetworkManager.PacketContext packetContext) {
        ItemStack stack = packetByteBuf.readItemStack();

        WalkieTalkieScreen.getInstance().updateButtons(stack);
    }
}
