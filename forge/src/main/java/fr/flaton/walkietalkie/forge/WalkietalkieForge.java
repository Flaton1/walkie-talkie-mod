package fr.flaton.walkietalkie.forge;

import dev.architectury.platform.forge.EventBuses;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.client.gui.screen.SpeakerScreen;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.network.ModMessages;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(WalkieTalkie.MOD_ID)
public class WalkietalkieForge {
    public WalkietalkieForge() {
        EventBuses.registerModEventBus(WalkieTalkie.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        ModConfig config = new ModConfig(FMLPaths.CONFIGDIR.get());
        config.loadModConfig();

        WalkieTalkie.init();
    }

    @Mod.EventBusSubscriber(modid = WalkieTalkie.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModMessages.registerS2CPackets();

            HandledScreens.register(ModScreenHandlers.SPEAKER.get(), SpeakerScreen::new);
        }
    }

}