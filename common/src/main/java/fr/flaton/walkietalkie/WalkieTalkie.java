package fr.flaton.walkietalkie;

import fr.flaton.walkietalkie.block.ModBlocks;
import fr.flaton.walkietalkie.block.entity.ModBlockEntities;
import fr.flaton.walkietalkie.item.ModItems;
import fr.flaton.walkietalkie.network.ModMessages;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;

public class WalkieTalkie {

	public static void init() {
		ModBlocks.register();
		ModItems.register();

		ModBlockEntities.register();
		ModScreenHandlers.register();

		ModMessages.registerC2SPackets();

		ModSoundEvents.register();
	}
}