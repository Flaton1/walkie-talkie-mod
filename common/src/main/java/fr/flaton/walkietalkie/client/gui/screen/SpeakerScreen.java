package fr.flaton.walkietalkie.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.client.gui.widget.ToggleImageButton;
import fr.flaton.walkietalkie.network.ModMessages;
import fr.flaton.walkietalkie.screen.SpeakerScreenHandler;
import io.netty.buffer.Unpooled;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpeakerScreen extends HandledScreen<SpeakerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/gui/gui_walkietalkie.png");
    private static final Identifier ACTIVATE_TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/icons/activate.png");

    private final int xSize = 195;
    private final int ySize = 76;

    private int guiLeft;
    private int guiTop;

    private ToggleImageButton activateButton;
    private Text canalText = Text.literal("");

    public SpeakerScreen(SpeakerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, title.getString(), guiTop + 7, 4210752);

        updateActivateState();

        drawCenteredText(matrices, this.textRenderer, String.valueOf(handler.getCanal()), guiTop + 26, 4210752);
    }

    protected void drawCenteredText(MatrixStack matrices, TextRenderer textRenderer, String text, int y, int color) {
        textRenderer.draw(matrices, text, (float) (this.width / 2 - textRenderer.getWidth(text) / 2), y, color);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }

    private void updateActivateState() {
        activateButton.setState(handler.isActivate());
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        activateButton = new ToggleImageButton(guiLeft + 6, guiTop + ySize - 6 - 20, ACTIVATE_TEXTURE, button -> sendUpdateSpeaker(0, false), handler.isActivate());
        this.addDrawableChild(activateButton);

        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), button -> sendUpdateSpeaker(1, true)).dimensions(this.width / 2 - 10 + 40, guiTop + 20, 20, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), button -> sendUpdateSpeaker(1, false)).dimensions(this.width / 2 - 10 - 40, guiTop + 20, 20, 20).build());

        canalText = Text.literal(String.valueOf(handler.getCanal()));
    }

    private void sendUpdateSpeaker(int index, boolean status) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(index);
        buf.writeBoolean(status);

        NetworkManager.sendToServer(ModMessages.UPDATE_SPEAKER, buf);
    }


}
