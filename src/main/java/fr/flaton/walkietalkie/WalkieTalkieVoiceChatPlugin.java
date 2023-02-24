package fr.flaton.walkietalkie;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.packets.StaticSoundPacket;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class WalkieTalkieVoiceChatPlugin implements VoicechatPlugin {

    @Override
    public String getPluginId() {
        return WalkieTalkie.MOD_ID;
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

        if (getWalkieTalkieActivate(senderPlayer) == null) {
            return;
        }


        if (hasWalkieTalkieMute(senderPlayer)) {
            return;
        }

        int senderCanal = getCanal(senderPlayer);
        int senderRange = getRange(senderPlayer);

        VoicechatServerApi api = event.getVoicechat();



        for (PlayerEntity receiverPlayer : Objects.requireNonNull(senderPlayer.getServer()).getPlayerManager().getPlayerList()) {

            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid())) {
                continue;
            }

            if (getWalkieTalkieActivate(receiverPlayer) == null) {
                continue;
            }

            if (!receiverPlayer.getPos().isInRange(senderPlayer.getPos(), senderRange)) {
                continue;
            }

            int receiverCanal = getCanal(receiverPlayer);

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

    private ItemStack getWalkieTalkieActivate(PlayerEntity player) {

        ItemStack itemStack = null;

        int range = 0;

        PlayerInventory playerInventory = player.getInventory();



        ArrayList<ItemStack> inventory = new ArrayList<>();
        inventory.addAll(playerInventory.main);
        inventory.addAll(playerInventory.armor);
        inventory.addAll(playerInventory.offHand);

        for (ItemStack item : inventory) {

            if (item.getItem().getClass().equals(WalkieTalkieItem.class) && item.hasNbt()) {

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

    private int getCanal(PlayerEntity player) {
        return Objects.requireNonNull(Objects.requireNonNull(getWalkieTalkieActivate(player)).getNbt()).getInt(WalkieTalkieItem.NBT_KEY_CANAL);
    }

    private int getRange(PlayerEntity player) {
        WalkieTalkieItem item = (WalkieTalkieItem) Objects.requireNonNull(getWalkieTalkieActivate(player)).getItem();
        return item.getRange();
    }

    private boolean hasWalkieTalkieMute(PlayerEntity player) {
        return Objects.requireNonNull(Objects.requireNonNull(getWalkieTalkieActivate(player)).getNbt()).getBoolean(WalkieTalkieItem.NBT_KEY_MUTE);
    }
}