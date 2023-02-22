package fr.flaton.walkietalkiemod.item;

import fr.flaton.walkietalkiemod.gui.WalkieTalkieScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WalkieTalkieItem extends Item {

    public int getRange() {
        return RANGE;
    }
    private final int RANGE;

    public static final String NBT_KEY_CANAL = "walkietalkiemod.canal";
    public static final String NBT_KEY_MUTE = "walkietalkiemod.mute";
    public static final String NBT_KEY_ACTIVATE = "walkietalkiemod.activate";


    public WalkieTalkieItem(Settings settings, int range) {
        super(settings);
        RANGE = range;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (world.isClient()) {
            if (user.getStackInHand(hand).hasNbt()) {
                WalkieTalkieScreen.setScreen(user.getStackInHand(hand));
            }

        }

        return super.use(world, user, hand);
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


}
