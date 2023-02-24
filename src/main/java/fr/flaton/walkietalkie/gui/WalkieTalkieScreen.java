package fr.flaton.walkietalkie.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.flaton.walkietalkie.WalkieTalkie;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WalkieTalkieScreen extends Screen {

    private final int xSize = 195;
    private final int ySize = 76;

    private int screenLeft;
    private int screenTop;

    private static final Identifier BG_TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/gui/gui_walkietalkie.png");

    public WalkieTalkieScreen() {
        super(Text.translatable("gui.walkietalkie.label"));
    }

    @Override
    protected void init() {
        super.init();
        this.screenLeft = (this.width - xSize) / 2;
        this.screenTop = (this.height - ySize) / 2;

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.walkietalkie.label"), button -> {

        }).dimensions(this.width / 2 - 116, this.height / 2 + 62, 20, 20).build());


    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        this.drawTexture(matrices, screenLeft, screenTop, 0, 0, xSize, ySize);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int titleWidth = this.textRenderer.getWidth(title.asOrderedText());
        this.textRenderer.draw(matrices, this.title, (float) (this.width / 2 - titleWidth / 2), (float)screenTop + 7, 4210752);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void checkButtons() {

    }

}
