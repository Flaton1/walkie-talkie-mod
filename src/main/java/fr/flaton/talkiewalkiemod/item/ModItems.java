package fr.flaton.talkiewalkiemod.item;

import fr.flaton.talkiewalkiemod.TalkieWalkieMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item WOODEN_TALKIEWALKIE = registerItem("wooden_talkiewalkie", new Item(new FabricItemSettings()));
    public static final Item STONE_TALKIEWALKIE = registerItem("stone_talkiewalkie", new Item(new FabricItemSettings()));
    public static final Item IRON_TALKIEWALKIE = registerItem("iron_talkiewalkie", new Item(new FabricItemSettings()));
    public static final Item DIAMOND_TALKIEWALKIE = registerItem("diamond_talkiewalkie", new Item(new FabricItemSettings()));
    public static final Item NETHERITE_TALKIEWALKIE = registerItem("netherite_talkiewalkie", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TalkieWalkieMod.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        addToItemGroup(ItemGroups.TOOLS, WOODEN_TALKIEWALKIE);
        addToItemGroup(ItemGroups.TOOLS, STONE_TALKIEWALKIE);
        addToItemGroup(ItemGroups.TOOLS, IRON_TALKIEWALKIE);
        addToItemGroup(ItemGroups.TOOLS, DIAMOND_TALKIEWALKIE);
        addToItemGroup(ItemGroups.TOOLS, NETHERITE_TALKIEWALKIE);
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    public static void registerModItems() {
        TalkieWalkieMod.LOGGER.info("Registering Mod Items for " + TalkieWalkieMod.MOD_ID);

        addItemsToItemGroup();
    }

}
