package fr.flaton.walkietalkie.screen;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.Constants;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS = DeferredRegister.create(Constants.MOD_ID, Registry.MENU_KEY);

    public static final RegistrySupplier<ScreenHandlerType<SpeakerScreenHandler>> SPEAKER = SCREEN_HANDLERS.register("speaker",() -> new ScreenHandlerType<>(SpeakerScreenHandler::new));

    public static void register() {
        SCREEN_HANDLERS.register();
    }


}
