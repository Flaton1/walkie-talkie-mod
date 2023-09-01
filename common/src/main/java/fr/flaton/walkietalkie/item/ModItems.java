package fr.flaton.walkietalkie.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.block.ModBlocks;
import fr.flaton.walkietalkie.config.ModConfig;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(WalkieTalkie.MOD_ID, Registry.ITEM_KEY);

    public static final RegistrySupplier<Item> WOODEN_WALKIETALKIE = ITEMS.register("wooden_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.woodenWalkieTalkieRange));
    public static final RegistrySupplier<Item> STONE_WALKIETALKIE = ITEMS.register("stone_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.stoneWalkieTalkieRange));
    public static final RegistrySupplier<Item> IRON_WALKIETALKIE = ITEMS.register("iron_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.ironWalkieTalkieRange));
    public static final RegistrySupplier<Item> DIAMOND_WALKIETALKIE = ITEMS.register("diamond_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).group(ModItemGroup.WALKIETALKIE), ModConfig.diamondWalkieTalkieRange));
    public static final RegistrySupplier<Item> NETHERITE_WALKIETALKIE = ITEMS.register("netherite_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).fireproof().group(ModItemGroup.WALKIETALKIE), ModConfig.netheriteWalkieTalkieRange));

    public static final RegistrySupplier<Item> SPEAKER = ITEMS.register("speaker", () -> new BlockItem(ModBlocks.SPEAKER.get(),
            new Item.Settings().group(ModItemGroup.WALKIETALKIE)));

    public static void register() {
        ITEMS.register();
    }

}
