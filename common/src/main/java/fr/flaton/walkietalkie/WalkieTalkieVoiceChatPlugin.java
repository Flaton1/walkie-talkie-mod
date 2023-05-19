package fr.flaton.walkietalkie;

import de.maxhenkel.voicechat.api.ForgeVoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import de.maxhenkel.voicechat.api.packets.StaticSoundPacket;
import fr.flaton.walkietalkie.block.entity.SpeakerBlockEntity;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

@ForgeVoicechatPlugin
public class WalkieTalkieVoiceChatPlugin implements VoicechatPlugin {

    @Nullable
    public static VoicechatServerApi voicechatServerApi;

    public static OpusDecoder opusDecoder;
    public static OpusEncoder opusEncoder;

    @Override
    public String getPluginId() {
        return WalkieTalkie.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voicechatServerApi = event.getVoicechat();
        opusDecoder = voicechatServerApi.createDecoder();
        opusEncoder = voicechatServerApi.createEncoder();
    }

    private void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null) {
            return;
        }

        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity senderPlayer)) {
            return;
        }

        ItemStack senderStack = getWalkieTalkieActivate(senderPlayer);

        if (getWalkieTalkieActivate(senderPlayer) == null) {
            return;
        }

        if (isWalkieTalkieMute(senderStack)) {
            return;
        }

        int senderCanal = getCanal(senderStack);

        for (SpeakerBlockEntity entity : SpeakerBlockEntity.getSpeakersActivateInRange(senderCanal, senderPlayer.getPos(), getRange(senderStack))) {
            entity.playSound(voicechatServerApi, event.getPacket());
        }

        for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerManager().getPlayerList()) {

            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) {
                continue;
            }

            ItemStack receiverStack = getWalkieTalkieActivate(receiverPlayer);

            if (receiverStack == null) {
                continue;
            }

            int receiverRange = getRange(receiverStack);
            int receiverCanal = getCanal(receiverStack);

            if (!senderPlayer.getPos().isInRange(receiverPlayer.getPos(), receiverRange)) {
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

            byte[] data = compressOpusData(event.getPacket().getOpusEncodedData());
            StaticSoundPacket soundPacket = event.getPacket().staticSoundPacketBuilder().opusEncodedData(data).build();

            voicechatServerApi.sendStaticSoundPacketTo(connection, soundPacket);
        }
    }

    private static short[] downsample(short[] audio, int srcFreq, int dstFreq) {
        int ratio = srcFreq / dstFreq;
        int newLength = audio.length / ratio;
        short[] newAudio = new short[newLength];
        for (int i = 0, j = 0; i < audio.length && j < newLength; i += ratio, j++) {
            newAudio[j] = audio[i];
        }
        return newAudio;
    }

    private static short[] upsample(short[] audio, int srcFreq, int dstFreq) {
        int ratio = dstFreq / srcFreq;
        int newLength = audio.length * ratio;
        short[] newAudio = new short[newLength];
        for (int i = 0, j = 0; i < audio.length - 1 && j < newLength - 1; i++, j += ratio) {
            newAudio[j] = audio[i];
            for (int k = 1; k < ratio; k++) {
                newAudio[j + k] = (short) ((audio[i] * (ratio - k) + audio[i + 1] * k) / ratio);
            }
        }
        newAudio[newLength - 1] = audio[audio.length - 1];
        return newAudio;
    }

    private static short[] compressAudio(short[] audio) {
        short[] downsampled = downsample(audio, 48000, 4800);
        short[] upsampled = upsample(downsampled, 4800, 48000);
        return upsampled;
    }

    private byte[] compressOpusData(byte[] data) {
        short[] audio = opusDecoder.decode(data);
        short[] audioCompressed = compressAudio(audio);
        return opusEncoder.encode(audioCompressed);
    }

    private ItemStack getWalkieTalkieActivate(PlayerEntity player) {

        ItemStack itemStack = null;

        int range = 0;

        PlayerInventory playerInventory = player.getInventory();

        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.addAll(playerInventory.main);
        inventory.addAll(playerInventory.armor);
        inventory.addAll(playerInventory.offHand);

        for (ItemStack item : inventory) {

            if (item.getItem() instanceof WalkieTalkieItem && item.hasNbt()) {

                WalkieTalkieItem walkieTalkieItem = (WalkieTalkieItem) Objects.requireNonNull(item.getItem());

                if (!Objects.requireNonNull(item.getNbt()).getBoolean(WalkieTalkieItem.NBT_KEY_ACTIVATE)) {
                    continue;
                }

                if (walkieTalkieItem.getRange() > range) {
                    itemStack = item;
                    range = walkieTalkieItem.getRange();
                }
            }
            
        }

        return itemStack;

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
}