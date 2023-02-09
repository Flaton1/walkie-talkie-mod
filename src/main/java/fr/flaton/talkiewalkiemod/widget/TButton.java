package fr.flaton.talkiewalkiemod.widget;

import fr.flaton.talkiewalkiemod.TalkieWalkieMod;
import fr.flaton.talkiewalkiemod.networking.ModMessages;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.icon.TextureIcon;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TButton extends io.github.cottonmc.cotton.gui.widget.WButton {
    private final int ID;

    private static final TextureIcon MICROPHONE = new TextureIcon(new Identifier(TalkieWalkieMod.MOD_ID, "textures/icons/microphone.png"));
    private static final TextureIcon MUTED = new TextureIcon(new Identifier(TalkieWalkieMod.MOD_ID, "textures/icons/muted.png"));
    private static final TextureIcon OFF = new TextureIcon(new Identifier(TalkieWalkieMod.MOD_ID, "textures/icons/off.png"));
    private static final TextureIcon ON = new TextureIcon(new Identifier(TalkieWalkieMod.MOD_ID, "textures/icons/on.png"));

    public TButton(int id) {
        super();
        this.ID = id;
    }

    public TButton(Text text, int id) {
        super(text);
        this.ID = id;
    }


    @Override
    public InputResult onClick(int x, int y, int button) {

        ClientPlayNetworking.send(ModMessages.BUTTON_PRESSED, createPacket());

        return super.onClick(x, y, button);
    }

    private PacketByteBuf createPacket() {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeByte(ID);

        return packet;
    }

    public void updateIcon(boolean value) {

        if (ID == 3) {
            if (value) {
                setIcon(MUTED);
            } else {
                setIcon(MICROPHONE);
            }
        }
        if (ID == 4) {
            if (value) {
                setIcon(ON);
            } else {
                setIcon(OFF);
            }
        }

    }

}
