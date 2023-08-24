package fr.flaton.walkietalkie.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
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

    protected void renderImage(DrawContext context, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        context.drawTexture(texture, getX() + 2, getY() + 2, 0, 0, 16, 16, 16, 16);
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderButton(context, mouseX, mouseY, delta);
        renderImage(context, mouseX, mouseY);

        if (hovered) {
            renderToolTip(context, mc.textRenderer, mouseX, mouseY);
        }
    }

    public void renderToolTip(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {
        if (tooltipSupplier == null) {
            return;
        }
        tooltipSupplier.onTooltip(this, context, textRenderer, mouseX, mouseY);
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        appendDefaultNarrations(builder);
    }

    public interface TooltipSupplier {
        void onTooltip(ImageButton button, DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY);
    }

    public interface PressAction {
        void onPress(ImageButton button);
    }


}
