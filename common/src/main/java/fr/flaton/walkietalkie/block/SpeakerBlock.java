package fr.flaton.walkietalkie.block;

import fr.flaton.walkietalkie.block.entity.SpeakerBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SpeakerBlock extends BlockWithEntity implements BlockEntityProvider {

    protected SpeakerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new SpeakerBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            if (world.getBlockEntity(pos) instanceof SpeakerBlockEntity) {
                SpeakerBlockEntity blockEntity = (SpeakerBlockEntity) world.getBlockEntity(pos);
                player.openHandledScreen(blockEntity);
            }
        }

        return ActionResult.CONSUME;
    }
}
