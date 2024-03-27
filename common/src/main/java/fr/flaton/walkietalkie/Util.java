package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Util {

    public static ItemStack getWalkieTalkieInHand(PlayerEntity player) {

        ItemStack mainHand = player.getStackInHand(Hand.MAIN_HAND);
        ItemStack offHand = player.getStackInHand(Hand.OFF_HAND);

        if (mainHand.getItem() instanceof WalkieTalkieItem) {
            return mainHand;
        }
        if (offHand.getItem() instanceof WalkieTalkieItem) {
            return offHand;
        }
        return null;
    }

    public static boolean canBroadcastToReceiver(World senderWorld, World receiverWorld, Vec3d senderPos, Vec3d receiverPos, int range) {
        double senderCoordinateScale = senderWorld.getDimension().getCoordinateScale();
        double receiverCoordinateScale = receiverWorld.getDimension().getCoordinateScale();

        double appliedRange = ModConfig.applyDimensionScale ? range / Math.max(senderCoordinateScale, receiverCoordinateScale) : range;

        return senderPos.isInRange(receiverPos, appliedRange);
    }

    public static ArrayList<ItemStack> getWalkieTalkies(PlayerEntity player) {

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        PlayerInventory playerInventory = player.inventory;
        ArrayList<ItemStack> inventory = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            inventory.add(playerInventory.main.get(i));
        }
        inventory.addAll(playerInventory.offHand);

        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) {
                continue;
            }

            if (stack.getItem() instanceof WalkieTalkieItem && stack.hasTag()) {
                itemStacks.add(stack);
            }

        }

        return itemStacks;
    }

    public static @Nullable ItemStack getOptimalWalkieTalkieRange(PlayerEntity player) {
        ArrayList<ItemStack> itemStacks = getWalkieTalkies(player);
        if (itemStacks.isEmpty()) {
            return null;
        }

        ItemStack itemStack = null;
        int range = 0;

        for (ItemStack stack : itemStacks) {
            if (stack.getItem() instanceof WalkieTalkieItem) {
                WalkieTalkieItem walkieTalkieItem = (WalkieTalkieItem) stack.getItem();
                if (walkieTalkieItem.getRange() > range) {
                    itemStack = stack;
                    range = walkieTalkieItem.getRange();
                }
            }
        }

        return itemStack;
    }

    public static @Nullable ItemStack getWalkieTalkieActivated(PlayerEntity player) {
        ItemStack stack = getOptimalWalkieTalkieRange(player);
        if (stack != null && stack.getTag().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE)) {
            return stack;
        }
        return null;
    }

    public static int loop(int value, int min, int max) {

        if (value > max) {
            value = min;
        } else if (value < min) {
            value = max;
        }
        return value;

    }
}
