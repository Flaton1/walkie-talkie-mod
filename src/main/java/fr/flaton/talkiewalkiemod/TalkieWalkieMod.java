package fr.flaton.talkiewalkiemod;

import fr.flaton.talkiewalkiemod.item.ModItems;
import fr.flaton.talkiewalkiemod.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TalkieWalkieMod implements ModInitializer {
	public static final String MOD_ID = "talkiewalkiemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static  Map<Item, Integer> RANGE_MAP = new HashMap<>();
	static {
		RANGE_MAP.put(ModItems.WOODEN_TALKIEWALKIE, 128);
		RANGE_MAP.put(ModItems.STONE_TALKIEWALKIE, 256);
		RANGE_MAP.put(ModItems.IRON_TALKIEWALKIE, 512);
		RANGE_MAP.put(ModItems.DIAMOND_TALKIEWALKIE, 1024);
		RANGE_MAP.put(ModItems.NETHERITE_TALKIEWALKIE, 2048);
	}





	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModMessages.registerC2SPackets();


	}
}