package fr.flaton.walkietalkie.forge;

import fr.flaton.walkietalkie.Walkietalkie;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Walkietalkie.MOD_ID)
public class WalkietalkieForge {
    public WalkietalkieForge() {
        Walkietalkie.init();
    }
}