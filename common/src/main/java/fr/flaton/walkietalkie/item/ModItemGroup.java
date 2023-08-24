package fr.flaton.walkietalkie.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import fr.flaton.walkietalkie.WalkieTalkie;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static CreativeTabRegistry.TabSupplier WALKIETALKIE = register("walkietalkie", ModItems.WOODEN_WALKIETALKIE);

    private static CreativeTabRegistry.TabSupplier register(String id, RegistrySupplier<Item> icon) {
        return CreativeTabRegistry.create(new Identifier(WalkieTalkie.MOD_ID, id), () -> new ItemStack(icon.get()));
    }
}
