package fr.flaton.walkietalkie;

import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import fr.flaton.walkietalkie.block.entity.SpeakerBlockEntity;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@ForgeVoicechatPlugin
public class WalkieTalkieVoiceChatPlugin implements VoicechatPlugin {

    @Nullable
    public static VoicechatServerApi voicechatServerApi;

    @Override
    public String getPluginId() {
        return Constants.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voicechatServerApi = event.getVoicechat();
    }

    private void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null) {
            return;
        }

        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity senderPlayer)) {
            return;
        }

        ItemStack senderItemStack = Util.getWalkieTalkieInHand(senderPlayer);

        if (senderItemStack == null) {
            return;
        }

        if (!(senderItemStack.getItem() instanceof WalkieTalkieItem)) {
            return;
        }

        if (isWalkieTalkieMute(senderItemStack)) {
            return;
        }

        int senderCanal = getCanal(senderItemStack);

        for (SpeakerBlockEntity entity : SpeakerBlockEntity.getSpeakersActivatedInRange(senderCanal, senderPlayer.getWorld(), senderPlayer.getPos(), getRange(senderItemStack))) {
            entity.playSound(voicechatServerApi, event);
        }

        for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerManager().getPlayerList()) {

            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) {
                continue;
            }

            if (!ModConfig.crossDimensionsEnabled && !receiverPlayer.getWorld().getDimension().equals(senderPlayer.getWorld().getDimension())) {
                continue;
            }

            ItemStack receiverStack = Util.getWalkieTalkieActivated(receiverPlayer);

            if (receiverStack == null) {
                continue;
            }

            int receiverRange = getRange(receiverStack);
            int receiverCanal = getCanal(receiverStack);

            if (!canBroadcastToReceiver(senderPlayer, receiverPlayer, receiverRange)) {
                continue;
            }

            if (receiverCanal != senderCanal) {
                continue;
            }

            // Send audio
            VoicechatConnection connection = voicechatServerApi.getConnectionOf(receiverPlayer.getUuid());
            if (connection == null) {
                continue;
            }

            voicechatServerApi.sendStaticSoundPacketTo(connection, event.getPacket().staticSoundPacketBuilder().build());
        }
    }



    private int getCanal(ItemStack stack) {
        return Objects.requireNonNull(stack.getNbt()).getInt(WalkieTalkieItem.NBT_KEY_CANAL);
    }

    private int getRange(ItemStack stack) {
        WalkieTalkieItem item = (WalkieTalkieItem) Objects.requireNonNull(stack.getItem());
        return item.getRange();
    }

    private boolean isWalkieTalkieMute(ItemStack stack) {
        return Objects.requireNonNull(stack.getNbt()).getBoolean(WalkieTalkieItem.NBT_KEY_MUTE);
    }

    private boolean canBroadcastToReceiver(PlayerEntity senderPlayer, PlayerEntity receiverPlayer, int receiverRange) {
        World senderWorld = senderPlayer.getWorld();
        World receiverWorld = receiverPlayer.getWorld();

        return Util.canBroadcastToReceiver(senderWorld, receiverWorld, senderPlayer.getPos(), receiverPlayer.getPos(), receiverRange);
    }
}