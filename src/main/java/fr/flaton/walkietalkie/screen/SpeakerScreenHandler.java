package fr.flaton.walkietalkie.screen;

import fr.flaton.walkietalkie.block.ModBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class SpeakerScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;

    private final ScreenHandlerContext context;

    public SpeakerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory.player, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()), new ArrayPropertyDelegate(2), ScreenHandlerContext.EMPTY);
    }

    public SpeakerScreenHandler(int syncId, PlayerEntity player, BlockEntity entity, PropertyDelegate delegate, ScreenHandlerContext context) {
        super(ModScreenHandlers.SPEAKER_SCREEN_HANDLER, syncId);
        this.propertyDelegate = delegate;
        this.context = context;

        addProperties(delegate);
    }

    public boolean isActivate() {
        return propertyDelegate.get(0) > 0;
    }

    public int getCanal() {
        return propertyDelegate.get(1);
    }

    public void setPropertyDelegate(boolean activate, int canal) {
        propertyDelegate.set(0, activate ? 1 : 0);
        propertyDelegate.set(1, canal);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.SPEAKER);
    }
}
