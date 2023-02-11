package fr.flaton.walkietalkiemod.item;

import fr.flaton.walkietalkiemod.WalkieTalkieMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item WOODEN_WALKIETALKIE = registerItem("wooden_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1)));
    public static final Item STONE_WALKIETALKIE = registerItem("stone_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1)));
    public static final Item IRON_WALKIETALKIE = registerItem("iron_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1)));
    public static final Item DIAMOND_WALKIETALKIE = registerItem("diamond_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1)));
    public static final Item NETHERITE_WALKIETALKIE = registerItem("netherite_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).fireproof()));


    public static final String NBT_KEY_WALKIETALKIE_CANAL = "walkietalkiemod.canal";
    public static final String NBT_KEY_WALKIETALKIE_MUTE = "walkietalkiemod.mute";
    public static final String NBT_KEY_WALKIETALKIE_ACTIVATE = "walkietalkiemod.activate";

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(WalkieTalkieMod.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        addToItemGroup(ItemGroups.TOOLS, WOODEN_WALKIETALKIE);
        addToItemGroup(ItemGroups.TOOLS, STONE_WALKIETALKIE);
        addToItemGroup(ItemGroups.TOOLS, IRON_WALKIETALKIE);
        addToItemGroup(ItemGroups.TOOLS, DIAMOND_WALKIETALKIE);
        addToItemGroup(ItemGroups.TOOLS, NETHERITE_WALKIETALKIE);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        WalkieTalkieMod.LOGGER.info("Registering Mod Items for " + WalkieTalkieMod.MOD_ID);

        addItemsToItemGroup();
    }

}
