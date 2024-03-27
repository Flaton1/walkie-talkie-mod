package fr.flaton.walkietalkie.item;

import fr.flaton.walkietalkie.client.gui.screen.WalkieTalkieScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class WalkieTalkieItem extends Item {

    private final int RANGE;

    private static final String NBT_KEY_CANAL = "walkietalkie.canal";
    private static final String NBT_KEY_MUTE = "walkietalkie.mute";
    private static final String NBT_KEY_ACTIVATE = "walkietalkie.activate";


    public WalkieTalkieItem(Settings settings, int range) {
        super(settings);
        RANGE = range;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (world.isClient()) {
            if (player.getStackInHand(hand).hasNbt()) {

                ItemStack stack = player.getStackInHand(hand);

                new WalkieTalkieScreen(stack);
                return TypedActionResult.success(stack);
            }
        }

        return super.use(world, player, hand);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            return;
        }

        if (!stack.hasNbt()) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE, false);
            nbtCompound.putBoolean(WalkieTalkieItem.NBT_KEY_MUTE, false);
            nbtCompound.putInt(WalkieTalkieItem.NBT_KEY_CANAL, 1);
            stack.setNbt(nbtCompound);
        }

    }

    public static int getCanal(ItemStack stack) {
        return Objects.requireNonNull(stack.getNbt()).getInt(NBT_KEY_CANAL);
    }

    public static int getRange(ItemStack stack) {
        if (stack.getItem() instanceof WalkieTalkieItem item) {
            return item.getRange();
        }
        return -1;
    }

    private int getRange() {
        return RANGE;
    }

    public static boolean isActivate(ItemStack stack) {
        return Objects.requireNonNull(stack.getNbt()).getBoolean(NBT_KEY_ACTIVATE);
    }

    public static boolean isMute(ItemStack stack) {
        return Objects.requireNonNull(stack.getNbt()).getBoolean(NBT_KEY_MUTE);
    }

    public static void setCanal(ItemStack stack, int canal) {
        stack.getNbt().putInt(NBT_KEY_ACTIVATE, canal);
    }

    public static void setActivate(ItemStack stack, boolean activate) {
        stack.getNbt().putBoolean(NBT_KEY_ACTIVATE, activate);
    }

    public static void setMute(ItemStack stack, boolean mute) {
        stack.getNbt().putBoolean(NBT_KEY_ACTIVATE, mute);
    }


}
