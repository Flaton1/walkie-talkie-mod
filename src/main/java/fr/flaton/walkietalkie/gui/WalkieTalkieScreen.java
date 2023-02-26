package fr.flaton.walkietalkie.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.flaton.walkietalkie.WalkieTalkie;
import fr.flaton.walkietalkie.gui.widget.ToggleImageButton;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.networking.ModMessages;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
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
        super(Text.translatable("gui.walkietalkie.label"));
        instance = this;
        this.stack = stack;
        MinecraftClient.getInstance().setScreen(this);
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - xSize) / 2;
        this.guiTop = (this.height - ySize) / 2;

        mute = new ToggleImageButton(guiLeft + 6, guiTop + ySize - 6 - 20, MUTE_TEXTURE, button -> {
            ClientPlayNetworking.send(ModMessages.MUTE_PRESSED, PacketByteBufs.empty());
        }, stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));
        this.addDrawableChild(mute);

        activate = new ToggleImageButton(guiLeft + 28, guiTop + ySize - 26, ACTIVATE_TEXTURE, button -> {
            ClientPlayNetworking.send(ModMessages.ACTIVATE_PRESSED, PacketByteBufs.empty());
        }, stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));
        this.addDrawableChild(activate);


        this.addDrawableChild(ButtonWidget.builder(Text.literal(">"), button -> {
            PacketByteBuf packet = PacketByteBufs.create();
            packet.writeBoolean(true);
            ClientPlayNetworking.send(ModMessages.CANAL_PRESSED, packet);
        }).dimensions(this.width / 2 - 10 + 40, guiTop + 20, 20, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("<"), button -> {
            PacketByteBuf packet = PacketByteBufs.create();
            packet.writeBoolean(false);
            ClientPlayNetworking.send(ModMessages.CANAL_PRESSED, packet);
        }).dimensions(this.width / 2 - 10 - 40, guiTop + 20, 20, 20).build());

        canal = Text.literal(String.valueOf(stack.getNbt().getInt(WalkieTalkieItem.NBT_KEY_CANAL)));

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
        this.drawTexture(matrices, guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int titleWidth = this.textRenderer.getWidth(title.asOrderedText());
        this.textRenderer.draw(matrices, this.title, (float) (this.width / 2 - titleWidth / 2), (float) guiTop + 7, 4210752);
        int canalWidth = this.textRenderer.getWidth(canal.asOrderedText());
        this.textRenderer.draw(matrices, canal, (float) (this.width / 2 - canalWidth / 2), (float) guiTop + 26, 4210752);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void checkButtons(ItemStack stack) {
        mute.setState(stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_MUTE));
        activate.setState(stack.getNbt().getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE));
        canal = Text.literal(String.valueOf(stack.getNbt().getInt(WalkieTalkieItem.NBT_KEY_CANAL)));

        MinecraftClient.getInstance();
    }

    public static WalkieTalkieScreen getInstance() {
        return instance;
    }

}
