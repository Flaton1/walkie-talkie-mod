package fr.flaton.walkietalkie.block.entity;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(WalkieTalkie.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);

    public static final RegistrySupplier<BlockEntityType<SpeakerBlockEntity>> SPEAKER = BLOCK_ENTITIES.register("speaker", () ->
            BlockEntityType.Builder.create(SpeakerBlockEntity::new, ModBlocks.SPEAKER.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }



}
