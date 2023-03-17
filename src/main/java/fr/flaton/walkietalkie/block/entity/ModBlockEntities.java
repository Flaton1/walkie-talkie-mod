package fr.flaton.walkietalkie.block.entity;

import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static BlockEntityType<SpeakerBlockEntity> SPEAKER_BLOCK_ENTITY;

    public static void registerBlockEntities() {
    }

    static {
        SPEAKER_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(WalkieTalkie.MOD_ID, "speaker_block_entity"),
                FabricBlockEntityTypeBuilder.create(SpeakerBlockEntity::new, ModBlocks.SPEAKER).build());
    }



}
