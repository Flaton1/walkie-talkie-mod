package fr.flaton.walkietalkie.item;

import fr.flaton.walkietalkie.WalkieTalkie;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static ItemGroup WALKIETALKIE;

    static {
        WALKIETALKIE = FabricItemGroup.builder(new Identifier(WalkieTalkie.MOD_ID, "walkietalkie"))
                .displayName(Text.translatable("itemGroup.walkietalkie.walkietalkie"))
                .icon(() -> new ItemStack(ModItems.WOODEN_WALKIETALKIE)).build();
    }
}
