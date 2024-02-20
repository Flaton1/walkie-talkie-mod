package fr.flaton.walkietalkie.fabric;

import fr.flaton.walkietalkie.Constants;
import fr.flaton.walkietalkie.WalkieTalkieClient;
import fr.flaton.walkietalkie.client.gui.screen.SpeakerScreen;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class WalkieTalkieFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WalkieTalkieClient.init();

        HandledScreens.register(ModScreenHandlers.SPEAKER.get(), SpeakerScreen::new);

        ModelPredicateProviderRegistry.register(new Identifier(Constants.MOD_ID, "activate"), ((stack, world, entity, seed) -> {
            if (!stack.hasNbt()) {
                return 0;
            }
            return stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE) ? 1.0f : 0.0f;
        }));
    }
}
