package fr.flaton.talkiewalkiemod.gui;

import fr.flaton.talkiewalkiemod.widget.TButton;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Objects;

public class TalkieWalkieGui extends LightweightGuiDescription {

    private static WText canalText;
    private static TButton muteButton;
    private static TButton onOffButton;

    public TalkieWalkieGui(ItemStack stack) {

        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(196,76);

        WGridPanel gridPanel = new WGridPanel(20);
        root.add(gridPanel, 8, 2);

        // 1 column

        WLabel label = new WLabel(Text.translatable("gui.talkiewalkiemod.label"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        label.setVerticalAlignment(VerticalAlignment.CENTER);
        gridPanel.add(label, 4, 0);

        // 2 column

        canalText = new WText(Text.literal(""));
        setCanalText(Objects.requireNonNull(stack.getNbt()).getInt("talkiewalkiemod.canal"));
        canalText.setHorizontalAlignment(HorizontalAlignment.CENTER);
        canalText.setVerticalAlignment(VerticalAlignment.CENTER);
        gridPanel.add(canalText, 4 , 1);

        TButton downCanal = new TButton(Text.literal("<<<<"), 1);
        gridPanel.add(downCanal, 0, 1, 3, 1);

        TButton upCanal = new TButton(Text.literal(">>>>"), 2);
        gridPanel.add(upCanal, 6, 1, 3, 1);

        // 3 column

        WGridPanel column3 = new WGridPanel(20);
        column3.setGaps(2, 0);
        root.add(column3, 8, 48);

        muteButton = new TButton(3);
        column3.add(muteButton, 0, 0);
        muteButton.updateIcon(stack.getNbt().getBoolean("talkiewalkiemod.mute"));

        onOffButton = new TButton(4);
        column3.add(onOffButton, 1, 0);
        onOffButton.updateIcon(stack.getNbt().getBoolean("talkiewalkiemod.activate"));

    }


    public static void setCanalText(int canal) {
        canalText.setText(Text.literal(String.valueOf(canal)));
    }


    public static TButton getMuteButton() {
        return muteButton;
    }
    public static TButton getOnOffButton() {
        return onOffButton;
    }


}
