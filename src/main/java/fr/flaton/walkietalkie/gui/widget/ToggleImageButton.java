package fr.flaton.walkietalkie.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
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
    protected void renderImage(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, texture);

        if (state) {
            drawTexture(matrices, getX() + 2, getY() + 2, 16, 0, 16, 16, 32, 32);
        } else {
            drawTexture(matrices, getX() + 2, getY()+ 2, 0, 0, 16, 16, 32, 32);
        }

    }
}
