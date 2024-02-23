package fr.flaton.walkietalkie.client.gui.widget;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ToggleImageButton extends ImageButton{

    public void setState(boolean state) {
        this.state = state;
    }
    protected boolean state;

    public ToggleImageButton(int x, int y, Identifier texture, PressAction onPress, boolean state) {
        super(x, y, texture, onPress);
        this.state = state;
    }

    @Override
    protected void renderImage(MatrixStack matrices, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);

        if (state) {
            drawTexture(matrices, x + 2, y + 2, 16, 0, 16, 16, 32, 32);
        } else {
            drawTexture(matrices, x + 2, y + 2, 0, 0, 16, 16, 32, 32);
        }

    }
}
