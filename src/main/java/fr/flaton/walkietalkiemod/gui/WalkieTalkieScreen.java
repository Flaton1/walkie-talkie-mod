package fr.flaton.walkietalkiemod.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public class WalkieTalkieScreen extends CottonClientScreen {

    public static void setScreen(ItemStack itemStack) {
        MinecraftClient.getInstance().setScreen(new WalkieTalkieScreen(new WalkieTalkieGui(itemStack)));
    }

    public WalkieTalkieScreen(GuiDescription description) {
        super(description);
    }
}
