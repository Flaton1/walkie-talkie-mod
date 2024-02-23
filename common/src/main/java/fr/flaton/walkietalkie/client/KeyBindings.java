package fr.flaton.walkietalkie.client;

import fr.flaton.walkietalkie.network.ModMessages;
import io.netty.buffer.Unpooled;
import me.shedaniel.architectury.event.events.client.ClientTickEvent;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

import static me.shedaniel.architectury.registry.KeyBindings.registerKeyBinding;

public class KeyBindings {

    public static final KeyBinding ACTIVATE = new KeyBinding(
            "key.walkietalkie.activate",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.walkietalkie.keys"
    );

    public static void register() {
        registerKeyBinding(ACTIVATE);

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (ACTIVATE.wasPressed()) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                NetworkManager.sendToServer(ModMessages.ACTIVATE_KEY_PRESSED_C2S, buf);
            }
        });
    }

}
