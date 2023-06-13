package fr.flaton.walkietalkie.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.client.gui.widget.ToggleImageButton;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.network.ModMessages;
import io.netty.buffer.Unpooled;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WalkieTalkieScreen extends Screen {

    private static WalkieTalkieScreen instance;

    private final int xSize = 195;
    private final int ySize = 76;

    private int guiLeft;
    private int guiTop;

    private final ItemStack stack;

    private ToggleImageButton mute;
    private ToggleImageButton activate;
    private Text canal;

    private static final Identifier BG_TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/gui/gui_walkietalkie.png");
    private static final Identifier MUTE_TEXTURE = new Identifier("voicechat", "textures/icons/microphone_button.png");
    private static final Identifier ACTIVATE_TEXTURE = new Identifier(WalkieTalkie.MOD_ID, "textures/icons/activate.png");

    public WalkieTalkieScreen(ItemStack stack) {
        super(Text.translatable("gui.walkietalkie.title"));
        instance = this;
        this.stack = stack;

        MinecraftClient.getInstance().setScreen(this);
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        mute = new ToggleImageButton(guiLeft + 6, guiTop + ySize - 6 - 20, MUTE_TEXTURE, button -> NetworkManager.sendToServer(ModMessages.MUTE_PRESSED, new PacketByteBuf(Unpooled.EMPTY_BUFFER)), stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));
        this.addDrawableChild(mute);

        activate = new ToggleImageButton(guiLeft + 28, guiTop + ySize - 26, ACTIVATE_TEXTURE, button -> NetworkManager.sendToServer(ModMessages.ACTIVATE_PRESSED, new PacketByteBuf(Unpooled.EMPTY_BUFFER)), stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));
        this.addDrawableChild(activate);


        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), button -> {
            PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
            packet.writeBoolean(true);
            NetworkManager.sendToServer(ModMessages.CANAL_PRESSED, packet);
        }).dimensions(this.width / 2 - 10 + 40, guiTop + 20, 20, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), button -> {
            PacketByteBuf packet = new PacketByteBuf(Unpooled.buffer());
            packet.writeBoolean(false);
            NetworkManager.sendToServer(ModMessages.CANAL_PRESSED, packet);
        }).dimensions(this.width / 2 - 10 - 40, guiTop + 20, 20, 20).build());

        canal = Text.literal(String.valueOf(stack.getNbt().getInt(WalkieTalkieItem.NBT_KEY_CANAL)));

    }

    @Override
    public void renderBackground(DrawContext context) {
        super.renderBackground(context);
        context.drawTexture(BG_TEXTURE, guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawCenteredText(context, this.textRenderer, this.title, this.width / 2, guiTop + 7, 4210752);
        drawCenteredText(context, this.textRenderer, this.canal, this.width / 2, guiTop + 26, 4210752);
    }

    protected void drawCenteredText(DrawContext context, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        context.drawText(textRenderer, text, centerX - textRenderer.getWidth(text) / 2, y, color, false);
    }

    public void checkButtons(ItemStack stack) {
        mute.setState(stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));
        activate.setState(stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));
        canal = Text.literal(String.valueOf(stack.getNbt().getInt(WalkieTalkieItem.NBT_KEY_CANAL)));
    }

    public static WalkieTalkieScreen getInstance() {
        return instance;
    }

}
