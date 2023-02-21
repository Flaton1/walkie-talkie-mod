package fr.flaton.walkietalkiemod;

import fr.flaton.walkietalkiemod.item.ModItems;
import fr.flaton.walkietalkiemod.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkieTalkieMod implements ModInitializer {
	public static final String MOD_ID = "walkietalkiemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModMessages.registerC2SPackets();

	}
}