package fr.flaton.talkiewalkiemod.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public class TalkieWalkieScreen extends CottonClientScreen {

    public static void setScreen(ItemStack itemStack) {
        MinecraftClient.getInstance().setScreen(new TalkieWalkieScreen(new TalkieWalkieGui(itemStack)));
    }

    public TalkieWalkieScreen(GuiDescription description) {



        super(description);
    }
}
