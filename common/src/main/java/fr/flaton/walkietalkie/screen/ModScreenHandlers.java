package fr.flaton.walkietalkie.screen;

import fr.flaton.walkietalkie.Constants;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.MenuRegistry;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
    public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS = DeferredRegister.create(Constants.MOD_ID, Registry.MENU_KEY);

    public static final RegistrySupplier<ScreenHandlerType<SpeakerScreenHandler>> SPEAKER = SCREEN_HANDLERS.register("speaker",() -> MenuRegistry.of(SpeakerScreenHandler::new));

    public static void register() {
        SCREEN_HANDLERS.register();
    }


}
