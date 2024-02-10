package fr.flaton.walkietalkie;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Constants.MOD_ID, RegistryKeys.SOUND_EVENT);

    public static final Identifier ON_ID = new Identifier(Constants.MOD_ID, "on");
    public static final RegistrySupplier<SoundEvent> ON_SOUND_EVENT = SOUND_EVENTS.register("on", () -> SoundEvent.of(ON_ID));

    public static final Identifier OFF_ID = new Identifier(Constants.MOD_ID, "off");
    public static final RegistrySupplier<SoundEvent> OFF_SOUND_EVENT = SOUND_EVENTS.register("off", () -> SoundEvent.of(OFF_ID));

    public static void register() {
        SOUND_EVENTS.register();
    }

}
