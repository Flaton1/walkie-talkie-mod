package fr.flaton.talkiewalkiemod;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.packets.StaticSoundPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Objects;

public class TalkieWalkieVoiceChatPlugin implements VoicechatPlugin {

    @Override
    public String getPluginId() {
        return TalkieWalkieMod.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicPacket);
    }

    private void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null) {
            return;
        }

        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity senderPlayer)) {
            return;
        }

        if (hasTalkieWalkieNotActivate(senderPlayer)) {
            return;
        }

        if (hasTalkieWalkieMute(senderPlayer)) {
            return;
        }

        int senderCanal = getCanal(senderPlayer);
        int senderRange = getRange(senderPlayer);

        VoicechatServerApi api = event.getVoicechat();

        for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerManager().getPlayerList()) {

            int receiverCanal = getCanal(receiverPlayer);

            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) {
                continue;
            }

            if (hasTalkieWalkieNotActivate(receiverPlayer)) {
                continue;
            }

            if (!receiverPlayer.getPos().isInRange(senderPlayer.getPos(), senderRange)) {
                continue;
            }

            if (receiverCanal != senderCanal) {
                continue;
            }


            // Send audio
            VoicechatConnection connection = api.getConnectionOf(receiverPlayer.getUuid());
            if (connection == null) {
                continue;
            }

            StaticSoundPacket packet = event.getPacket().toStaticSoundPacket();

            api.sendStaticSoundPacketTo(connection, packet);


        }
    }

    private int getCanal(PlayerEntity player) {

        Map<Item, Integer> rangeMap = TalkieWalkieMod.RANGE_MAP;

        int range = 0;
        int canal = 1;

        for (ItemStack itemStack : player.getInventory().main) {
            Item item = itemStack.getItem();

            if (!rangeMap.containsKey(item) || !itemStack.hasNbt() || !Objects.requireNonNull(itemStack.getNbt()).getBoolean("talkiewalkiemod.activate")) {
                continue;
            }
            int itemRange = rangeMap.get(item);
            if (itemRange > range) {
                range = itemRange;
                canal = itemStack.getNbt().getInt("talkiewalkiemod.canal");
            }

        }

        return canal;
    }

    private boolean hasTalkieWalkieNotActivate(PlayerEntity player) {

        Map<Item, Integer> rangeMap = TalkieWalkieMod.RANGE_MAP;

        for (ItemStack itemStack : player.getInventory().main) {
            Item item = itemStack.getItem();

            if (!rangeMap.containsKey(item) || !itemStack.hasNbt() || !Objects.requireNonNull(itemStack.getNbt()).getBoolean("talkiewalkiemod.activate")) {
                continue;
            }

            return false;

            }
        return true;
    }

    private boolean hasTalkieWalkieMute(PlayerEntity player) {

        Map<Item, Integer> rangeMap = TalkieWalkieMod.RANGE_MAP;

        for (ItemStack itemStack : player.getInventory().main) {
            Item item = itemStack.getItem();

            if (!rangeMap.containsKey(item) || !itemStack.hasNbt() || !Objects.requireNonNull(itemStack.getNbt()).getBoolean("talkiewalkiemod.mute")) {
                continue;
            }
            return true;

        }
        return false;
    }

    private int getRange(PlayerEntity player) {

        Map<Item, Integer> rangeMap = TalkieWalkieMod.RANGE_MAP;

        int range = 0;

        for (ItemStack itemStack : player.getInventory().main) {
            Item item = itemStack.getItem();

            if (!rangeMap.containsKey(item) || !itemStack.hasNbt() || !Objects.requireNonNull(itemStack.getNbt()).getBoolean("talkiewalkiemod.activate") || Objects.requireNonNull(itemStack.getNbt()).getBoolean("talkiewalkiemod.mute")) {
                continue;
            }
            int itemRange = rangeMap.get(item);
            if (itemRange > range) {
                range = itemRange;
            }

        }

        return range;
    }
}