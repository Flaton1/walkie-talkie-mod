package fr.flaton.walkietalkie.screen;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.WalkieTalkie;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS = DeferredRegister.create(WalkieTalkie.MOD_ID, RegistryKeys.SCREEN_HANDLER);

    public static final RegistrySupplier<ScreenHandlerType<SpeakerScreenHandler>> SPEAKER = SCREEN_HANDLERS.register("speaker",() -> MenuRegistry.ofExtended(SpeakerScreenHandler::new));

    public static void register() {
        SCREEN_HANDLERS.register();
    }


}
