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
            nbtCompound.putBoolean(ModItems.NBT_KEY_WALKIETALKIE_ACTIVATE, false);
            nbtCompound.putBoolean(ModItems.NBT_KEY_WALKIETALKIE_MUTE, false);
            nbtCompound.putInt(ModItems.NBT_KEY_WALKIETALKIE_CANAL, 1);
            stack.setNbt(nbtCompound);
        }

    }


}
