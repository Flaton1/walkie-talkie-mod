package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

    public static boolean canBroadcastToReceiver(World senderWorld, World receiverWorld, Vec3d senderPos, Vec3d receiverPos, int range) {
        double senderCoordinateScale = senderWorld.getDimension().coordinateScale();
        double receiverCoordinateScale = receiverWorld.getDimension().coordinateScale();

        double appliedRange = ModConfig.applyDimensionScale ? range / Math.max(senderCoordinateScale, receiverCoordinateScale) : range;

        return senderPos.isInRange(receiverPos, appliedRange);
    }

}
