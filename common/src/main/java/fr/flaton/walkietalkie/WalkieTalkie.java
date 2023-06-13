package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.block.ModBlocks;
import fr.flaton.walkietalkie.block.entity.ModBlockEntities;
import fr.flaton.walkietalkie.item.ModItemGroup;
import fr.flaton.walkietalkie.item.ModItems;
import fr.flaton.walkietalkie.network.ModMessages;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalkieTalkie {
	public static final String MOD_ID = "walkietalkie";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void init() {
		ModBlocks.register();
		ModItems.register();
		ModItemGroup.register();

		ModBlockEntities.register();
		ModScreenHandlers.register();

		ModMessages.registerC2SPackets();
	}
}