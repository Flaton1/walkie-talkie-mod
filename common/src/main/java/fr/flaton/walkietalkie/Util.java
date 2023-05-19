package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

public class Util {

    public static Hand getHandUse(ServerPlayerEntity player) {

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
