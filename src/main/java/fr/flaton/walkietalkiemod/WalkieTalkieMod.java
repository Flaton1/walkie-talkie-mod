package fr.flaton.walkietalkiemod;

import fr.flaton.walkietalkiemod.item.ModItems;
import fr.flaton.walkietalkiemod.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WalkieTalkieMod implements ModInitializer {
	public static final String MOD_ID = "walkietalkiemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static  Map<Item, Integer> RANGE_MAP = new HashMap<>();

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModMessages.registerC2SPackets();


		RANGE_MAP.put(ModItems.WOODEN_WALKIETALKIE, 128);
		RANGE_MAP.put(ModItems.STONE_WALKIETALKIE, 256);
		RANGE_MAP.put(ModItems.IRON_WALKIETALKIE, 512);
		RANGE_MAP.put(ModItems.DIAMOND_WALKIETALKIE, 1024);
		RANGE_MAP.put(ModItems.NETHERITE_WALKIETALKIE, 2048);

	}
}