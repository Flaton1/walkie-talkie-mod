package fr.flaton.walkietalkie.forge;

import dev.architectury.platform.forge.EventBuses;
import fr.flaton.walkietalkie.Constants;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.WalkieTalkieClient;
import fr.flaton.walkietalkie.client.gui.screen.SpeakerScreen;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Constants.MOD_ID)
public class WalkieTalkieForge {
    public WalkieTalkieForge() {
        EventBuses.registerModEventBus(Constants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        ModConfig config = new ModConfig(FMLPaths.CONFIGDIR.get());
        config.loadModConfig();

        WalkieTalkie.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> WalkieTalkieClient::init);
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            HandledScreens.register(ModScreenHandlers.SPEAKER.get(), SpeakerScreen::new);

            ModelPredicateProviderRegistry.registerGeneric(new Identifier(Constants.MOD_ID, "activate"), ((stack, world, entity, seed) -> {
                if (!stack.hasNbt()) {
                    return 0;
                }
                return stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE) ? 1.0f : 0.0f;
            }));
        }
    }
}