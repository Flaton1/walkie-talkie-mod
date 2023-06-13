package fr.flaton.walkietalkie.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ModItemGroup {

    public static final DeferredRegister<ItemGroup> TABS =
            DeferredRegister.create("walkietalkie", RegistryKeys.ITEM_GROUP);

    public static final RegistrySupplier<ItemGroup> WALKIETALKIE = TABS.register(
            "walkietalkie",
            () -> CreativeTabRegistry.create(
                    Text.translatable("itemGroup.walkietalkie.walkietalkie"),
                    () -> new ItemStack(ModItems.WOODEN_WALKIETALKIE.get())
            )
    );

    public static void register() {
        TABS.register();
    }
}
