package fr.flaton.walkietalkie.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.Constants;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup WALKIETALKIE =  register("walkietalkie", ModItems.WOODEN_WALKIETALKIE);

    private static ItemGroup register(String id, RegistrySupplier<Item> icon) {
        return CreativeTabRegistry.create(new Identifier(Constants.MOD_ID, id), () -> new ItemStack(icon.get()));
    }
}
