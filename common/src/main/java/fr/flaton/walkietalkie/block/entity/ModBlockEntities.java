package fr.flaton.walkietalkie.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.Constants;
import fr.flaton.walkietalkie.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKeys;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Constants.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<SpeakerBlockEntity>> SPEAKER = BLOCK_ENTITIES.register("speaker", () ->
            BlockEntityType.Builder.create(SpeakerBlockEntity::new, ModBlocks.SPEAKER.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }



}
