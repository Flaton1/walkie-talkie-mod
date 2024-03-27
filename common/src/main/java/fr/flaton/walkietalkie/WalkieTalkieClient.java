package fr.flaton.walkietalkie;

import dev.architectury.registry.item.ItemPropertiesRegistry;
import fr.flaton.walkietalkie.client.KeyBindings;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import fr.flaton.walkietalkie.network.ModMessages;
import net.minecraft.util.Identifier;

public class WalkieTalkieClient {

    public static void init() {
        ModMessages.registerS2CPackets();
        KeyBindings.register();

        ItemPropertiesRegistry.registerGeneric(new Identifier(Constants.MOD_ID, "activate"), ((stack, world, entity, seed) -> {
            if (!stack.hasNbt()) {
                return 0;
            }
            return WalkieTalkieItem.isActivate(stack) ? 1.0f : 0.0f;
        }));
    }
}
