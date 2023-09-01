package fr.flaton.walkietalkie.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ImageButton extends PressableWidget {

    protected MinecraftClient mc;
    protected Identifier texture;
    protected PressAction onPress;
    @Nullable
    protected TooltipSupplier tooltipSupplier;

    public ImageButton(int x, int y, Identifier texture, PressAction onPress, @Nullable TooltipSupplier tooltipSupplier) {
        super(x, y, 20, 20, Text.empty());
        mc = MinecraftClient.getInstance();
        this.texture = texture;
        this.onPress = onPress;
        this.tooltipSupplier = tooltipSupplier;
    }

    public ImageButton(int x, int y, Identifier texture, PressAction onPress) {
        this(x, y, texture, onPress, null);
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    protected void renderImage(MatrixStack matrices, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, texture);
        drawTexture(matrices, x + 2, y + 2, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);
        renderImage(matrices, mouseX, mouseY);

        if (hovered) {
            renderToolTip(matrices, mc.textRenderer, mouseX, mouseY);
        }
    }

    public void renderToolTip(MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY) {
        if (tooltipSupplier == null) {
            return;
        }
        tooltipSupplier.onTooltip(this, matrices, textRenderer, mouseX, mouseY);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        appendDefaultNarrations(builder);
    }

    public interface TooltipSupplier {
        void onTooltip(ImageButton button, MatrixStack matrices, TextRenderer textRenderer, int mouseX, int mouseY);
    }

    public interface PressAction {
        void onPress(ImageButton button);
    }


}
