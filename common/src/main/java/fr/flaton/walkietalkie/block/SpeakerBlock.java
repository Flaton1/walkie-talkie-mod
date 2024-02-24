package fr.flaton.walkietalkie.block;


import com.mojang.serialization.MapCodec;
import fr.flaton.walkietalkie.block.entity.SpeakerBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpeakerBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final MapCodec<SpeakerBlock> CODEC = createCodec(SpeakerBlock::new);

    protected SpeakerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SpeakerBlockEntity(pos, state);
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
            if (world.getBlockEntity(pos) instanceof SpeakerBlockEntity blockEntity) {
                player.openHandledScreen(blockEntity);
            }
        }

        return ActionResult.CONSUME;
    }
}
