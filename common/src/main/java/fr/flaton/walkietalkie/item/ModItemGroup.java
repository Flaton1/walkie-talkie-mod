package fr.flaton.walkietalkie.item;

import fr.flaton.walkietalkie.Constants;
import me.shedaniel.architectury.registry.CreativeTabs;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup WALKIETALKIE =  register("walkietalkie", ModItems.WOODEN_WALKIETALKIE);

    private static ItemGroup register(String id, RegistrySupplier<Item> icon) {
        return CreativeTabs.create(new Identifier(Constants.MOD_ID, id), () -> new ItemStack(icon.get()));
    }
}
