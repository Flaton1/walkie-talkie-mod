package fr.flaton.walkietalkie.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class ImageButton extends ButtonWidget {


    protected Identifier texture;

    public ImageButton(int x, int y, Identifier texture, PressAction onPress) {
        super(x, y, 20, 20, Text.empty(), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.texture = texture;

    }

    protected void renderImage(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, texture);
        drawTexture(matrices, getX() + 2, getY()+ 2, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);
        renderImage(matrices, mouseX, mouseY, delta);
    }
}
