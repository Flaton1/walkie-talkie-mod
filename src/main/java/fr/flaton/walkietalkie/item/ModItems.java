package fr.flaton.walkietalkie.item;

import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.config.ModConfig;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item WOODEN_WALKIETALKIE = registerItem("wooden_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1), ModConfig.woodenWalkieTalkieRange));
    public static final Item STONE_WALKIETALKIE = registerItem("stone_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1), ModConfig.stoneWalkieTalkieRange));
    public static final Item IRON_WALKIETALKIE = registerItem("iron_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1), ModConfig.ironWalkieTalkieRange));
    public static final Item DIAMOND_WALKIETALKIE = registerItem("diamond_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1), ModConfig.diamondWalkieTalkieRange));
    public static final Item NETHERITE_WALKIETALKIE = registerItem("netherite_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).fireproof(), ModConfig.netheriteWalkieTalkieRange));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(WalkieTalkie.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        addToItemGroup(ModItemGroup.WALKIETALKIE, WOODEN_WALKIETALKIE);
        addToItemGroup(ModItemGroup.WALKIETALKIE, STONE_WALKIETALKIE);
        addToItemGroup(ModItemGroup.WALKIETALKIE, IRON_WALKIETALKIE);
        addToItemGroup(ModItemGroup.WALKIETALKIE, DIAMOND_WALKIETALKIE);
        addToItemGroup(ModItemGroup.WALKIETALKIE, NETHERITE_WALKIETALKIE);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        addItemsToItemGroup();
    }

}
