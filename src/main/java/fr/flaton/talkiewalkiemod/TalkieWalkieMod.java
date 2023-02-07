package fr.flaton.talkiewalkiemod;

import fr.flaton.talkiewalkiemod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TalkieWalkieMod implements ModInitializer {
	public static final String MOD_ID = "talkiewalkiemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
	}
}