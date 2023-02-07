package fr.flaton.talkiewalkiemod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TalkieWalkieItem extends Item {

    private final int TRANSMIT_RANGE;
    public TalkieWalkieItem(Settings settings, int transmitRange) {
        super(settings);
        this.TRANSMIT_RANGE = transmitRange;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            user.sendMessage(Text.literal("Transmit range : " + this.TRANSMIT_RANGE));
        }

        return super.use(world, user, hand);
    }
}
