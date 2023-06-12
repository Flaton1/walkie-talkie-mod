package fr.flaton.walkietalkie.client.gui.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ImageButton extends ButtonWidget {

    protected Identifier texture;

    public ImageButton(int x, int y, Identifier texture, PressAction onPress) {
        super(x, y, 20, 20, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.texture = texture;

    }

    protected void renderImage(DrawContext context) {
        context.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        drawTexture(context, texture, getX() + 2, getY()+ 2, 0, 0, 16, 16, 16, 16, 16);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderButton(context, mouseX, mouseY, delta);
        renderImage(context);
    }
}
