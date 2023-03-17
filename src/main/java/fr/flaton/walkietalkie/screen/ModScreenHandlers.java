package fr.flaton.walkietalkie.screen;

import fr.flaton.walkietalkie.WalkieTalkie;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<SpeakerScreenHandler> SPEAKER_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(WalkieTalkie.MOD_ID, "speaker"),
                SPEAKER_SCREEN_HANDLER);
    }

    static {
        SPEAKER_SCREEN_HANDLER =
                new ExtendedScreenHandlerType<>(SpeakerScreenHandler::new);
    }


}
