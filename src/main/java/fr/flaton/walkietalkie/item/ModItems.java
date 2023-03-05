package fr.flaton.walkietalkie.item;

import fr.flaton.walkietalkie.WalkieTalkie;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item WOODEN_WALKIETALKIE = registerItem("wooden_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), ModConfig.woodenWalkieTalkieRange));
    public static final Item STONE_WALKIETALKIE = registerItem("stone_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), ModConfig.stoneWalkieTalkieRange));
    public static final Item IRON_WALKIETALKIE = registerItem("iron_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), ModConfig.ironWalkieTalkieRange));
    public static final Item DIAMOND_WALKIETALKIE = registerItem("diamond_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), ModConfig.diamondWalkieTalkieRange));
    public static final Item NETHERITE_WALKIETALKIE = registerItem("netherite_walkietalkie", new WalkieTalkieItem(new FabricItemSettings().maxCount(1).fireproof().group(ItemGroup.TOOLS), ModConfig.netheriteWalkieTalkieRange));



    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(WalkieTalkie.MOD_ID, name), item);
    }

    public static void registerModItems() {
        WalkieTalkie.LOGGER.info("Registering Mod Items for " + WalkieTalkie.MOD_ID);

    }

}
