package fr.flaton.walkietalkie.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.Constants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Constants.MOD_ID, Registry.BLOCK_KEY);

    public static final RegistrySupplier<Block> SPEAKER = registerBlock("speaker",
            () -> new SpeakerBlock(AbstractBlock.Settings.of(Material.WOOD).mapColor(MapColor.OAK_TAN).sounds(BlockSoundGroup.WOOD).strength(0.8F)));

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register() {
        BLOCKS.register();
    }
}
