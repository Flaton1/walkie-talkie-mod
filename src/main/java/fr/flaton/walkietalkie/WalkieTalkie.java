package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.ModItems;
import fr.flaton.walkietalkie.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkieTalkie implements ModInitializer {
	public static final String MOD_ID = "walkietalkie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModConfig.registerModConfig();
		ModItems.registerModItems();
		ModMessages.registerC2SPackets();

	}
}