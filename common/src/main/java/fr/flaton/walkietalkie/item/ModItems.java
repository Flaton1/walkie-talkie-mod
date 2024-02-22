package fr.flaton.walkietalkie.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.Constants;
import fr.flaton.walkietalkie.block.ModBlocks;
import fr.flaton.walkietalkie.config.ModConfig;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Constants.MOD_ID, RegistryKeys.ITEM);

    public static final RegistrySupplier<Item> WOODEN_WALKIETALKIE = ITEMS.register("wooden_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).arch$tab(ModItemGroup.WALKIETALKIE), ModConfig.woodenWalkieTalkieRange));
    public static final RegistrySupplier<Item> STONE_WALKIETALKIE = ITEMS.register("stone_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).arch$tab(ModItemGroup.WALKIETALKIE), ModConfig.stoneWalkieTalkieRange));
    public static final RegistrySupplier<Item> IRON_WALKIETALKIE = ITEMS.register("iron_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).arch$tab(ModItemGroup.WALKIETALKIE), ModConfig.ironWalkieTalkieRange));
    public static final RegistrySupplier<Item> GOLDEN_WALKIETALKIE = ITEMS.register("golden_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).arch$tab(ModItemGroup.WALKIETALKIE), ModConfig.goldenWalkieTalkieRange));
    public static final RegistrySupplier<Item> DIAMOND_WALKIETALKIE = ITEMS.register("diamond_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).arch$tab(ModItemGroup.WALKIETALKIE), ModConfig.diamondWalkieTalkieRange));
    public static final RegistrySupplier<Item> NETHERITE_WALKIETALKIE = ITEMS.register("netherite_walkietalkie", () -> new WalkieTalkieItem(
            new Item.Settings().maxCount(1).fireproof().arch$tab(ModItemGroup.WALKIETALKIE), ModConfig.netheriteWalkieTalkieRange));

    public static final RegistrySupplier<Item> SPEAKER = ITEMS.register("speaker", () -> new BlockItem(ModBlocks.SPEAKER.get(),
            new Item.Settings().arch$tab(ModItemGroup.WALKIETALKIE)));

    public static void register() {
        ITEMS.register();
    }

}
