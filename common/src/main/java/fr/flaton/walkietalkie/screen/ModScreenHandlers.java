package fr.flaton.walkietalkie.screen;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.Constants;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS = DeferredRegister.create(Constants.MOD_ID, RegistryKeys.SCREEN_HANDLER);

    public static final RegistrySupplier<ScreenHandlerType<SpeakerScreenHandler>> SPEAKER = SCREEN_HANDLERS.register("speaker",() -> new ScreenHandlerType<>(SpeakerScreenHandler::new, FeatureFlags.DEFAULT_ENABLED_FEATURES));

    public static void register() {
        SCREEN_HANDLERS.register();
    }


}
