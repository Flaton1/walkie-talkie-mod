package fr.flaton.talkiewalkiemod.networking.packet;

import fr.flaton.talkiewalkiemod.item.TalkieWalkieItem;
import fr.flaton.talkiewalkiemod.networking.ModMessages;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

import java.util.Objects;

public class ButtonC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {


        ItemStack itemStack = player.getStackInHand(getHandUse(player));
        int buttonId = buf.readByte();

        if (getHandUse(player) == null) {
            return;
        }


        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.copyFrom(itemStack.getNbt());

        switch (buttonId) {
            case 1 -> {
                int canal = Objects.requireNonNull(itemStack.getNbt()).getInt("talkiewalkiemod.canal");
                if (canal <= 1) {
                    nbtCompound.putInt("talkiewalkiemod.canal", 16);
                } else {
                    nbtCompound.putInt("talkiewalkiemod.canal", canal -1);
                }
            }
            case 2 -> {
                int canal = Objects.requireNonNull(itemStack.getNbt()).getInt("talkiewalkiemod.canal");
                if (canal >= 16) {
                    nbtCompound.putInt("talkiewalkiemod.canal", 1);
                } else {
                    nbtCompound.putInt("talkiewalkiemod.canal", canal + 1);
                }
            }

            case 3 -> nbtCompound.putBoolean("talkiewalkiemod.mute", !(nbtCompound.getBoolean("talkiewalkiemod.mute")));
            case 4 -> nbtCompound.putBoolean("talkiewalkiemod.activate", !(nbtCompound.getBoolean("talkiewalkiemod.activate")));

            default -> throw new IllegalStateException("Unexpected value: " + buttonId);
        }

        itemStack.setNbt(nbtCompound);


        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeInt(nbtCompound.getInt("talkiewalkiemod.canal"));
        packet.writeBoolean(nbtCompound.getBoolean("talkiewalkiemod.mute"));
        packet.writeBoolean(nbtCompound.getBoolean("talkiewalkiemod.activate"));

        responseSender.sendPacket(ModMessages.BUTTON_PRESSED_RESPONSE, packet);

    }

    private static Hand getHandUse(ServerPlayerEntity player) {

        Hand hand = null;

        ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);

        if (mainHand.getItem().getClass() == TalkieWalkieItem.class) {
            hand = Hand.MAIN_HAND;
        }
        if (player.getStackInHand(Hand.OFF_HAND).getItem().getClass() == TalkieWalkieItem.class) {
            hand = Hand.OFF_HAND;
        }
        if ((player.getStackInHand(Hand.MAIN_HAND).getItem().getClass() == TalkieWalkieItem.class) &&
                (player.getStackInHand(Hand.OFF_HAND).getItem().getClass() == TalkieWalkieItem.class)) {
            hand = Hand.MAIN_HAND;
        }


        return hand;
    }

}
