package fr.flaton.talkiewalkiemod.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.Text;

public class TalkieWalkieGui extends LightweightGuiDescription {

    public TalkieWalkieGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(195,75);

        WLabel label = new WLabel(Text.translatable("gui.talkiewalkiemod.label"));
        root.add(label, 2, 1);

        WTextField textField = new WTextField(Text.literal("1"));
        root.add(textField, 2, 2, 7, 2);


    }

}
