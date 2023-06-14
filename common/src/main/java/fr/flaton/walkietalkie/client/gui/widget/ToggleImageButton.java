package fr.flaton.walkietalkie.client.gui.widget;

import net.minecraft.client.gui.DrawContext;
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
    protected void renderImage(DrawContext context) {
        context.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);

        if (state) {
            drawTexture(context, texture, getX() + 2, getY()+ 2, 16, 0, 0, 16, 16, 32, 32);
        } else {
            drawTexture(context, texture, getX() + 2, getY()+ 2, 0, 0, 0, 16, 16, 32, 32);
        }

    }
}
