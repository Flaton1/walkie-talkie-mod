package fr.flaton.walkietalkie.client;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import fr.flaton.walkietalkie.network.ModMessages;
import io.netty.buffer.Unpooled;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;

public class KeyBindings {

    public static final KeyBinding ACTIVATE = new KeyBinding(
            "key.walkietalkie.activate",
            InputUtil.Type.KEYSYM,
            InputUtil.GLFW_KEY_B,
            "category.walkietalkie.keys"
    );

    public static void register() {
        KeyMappingRegistry.register(ACTIVATE);

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (ACTIVATE.wasPressed()) {
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                NetworkManager.sendToServer(ModMessages.ACTIVATE_KEY_PRESSED_C2S, buf);
            }
        });
    }

}
