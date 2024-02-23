package fr.flaton.walkietalkie;

import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Constants.MOD_ID, Registry.SOUND_EVENT_KEY);

    public static final Identifier ON_ID = new Identifier(Constants.MOD_ID, "walkietalkie_on");
    public static final RegistrySupplier<SoundEvent> ON_SOUND_EVENT = SOUND_EVENTS.register("walkietalkie_on", () -> new SoundEvent(ON_ID));

    public static final Identifier OFF_ID = new Identifier(Constants.MOD_ID, "walkietalkie_off");
    public static final RegistrySupplier<SoundEvent> OFF_SOUND_EVENT = SOUND_EVENTS.register("walkietalkie_off", () -> new SoundEvent(OFF_ID));

    public static void register() {
        SOUND_EVENTS.register();
    }

}
