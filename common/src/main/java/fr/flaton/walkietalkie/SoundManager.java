package fr.flaton.walkietalkie;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.item.WalkieTalkieItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static fr.flaton.walkietalkie.WalkieTalkieVoiceChatPlugin.voiceChatAPI;

public class SoundManager {

    private static SoundManager instance;

    SoundManager() {
        instance = this;
    }

    public void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection senderConnection = event.getSenderConnection();

        if (senderConnection == null)
            return;

        if (!(senderConnection.getPlayer().getPlayer() instanceof PlayerEntity senderPlayer))
            return;

        ItemStack senderWalkie = Util.getWalkieTalkieInHand(senderPlayer);

        if (senderWalkie == null)
            return;

        if (!WalkieTalkieItem.isActivate(senderWalkie) || WalkieTalkieItem.isMute(senderWalkie))
            return;

        // TODO: CHANGE CANAL SYSTEM

        int senderCanal = WalkieTalkieItem.getCanal(senderWalkie);

        for (PlayerEntity receiverPlayer : senderPlayer.getServer().getPlayerManager().getPlayerList()) {

            if (receiverPlayer.getUuid().equals(senderPlayer.getUuid()))
                continue;

            if (!ModConfig.crossDimensionsEnabled && !receiverPlayer.getWorld().getDimension().equals(senderPlayer.getWorld().getDimension()))
                continue;

            ItemStack receiverWalkie = Util.getWalkieTalkieActivated(receiverPlayer);

            if (receiverWalkie == null)
                continue;

            int receiverRange = WalkieTalkieItem.getRange(receiverWalkie);
            int receiverCanal = WalkieTalkieItem.getCanal(receiverWalkie);

            if (receiverCanal != senderCanal)
                continue;

            if (!canBroadcastToReceiver(senderPlayer, receiverPlayer, receiverRange))
                continue;

            VoicechatConnection connection = voiceChatAPI.getConnectionOf(receiverPlayer.getUuid());
            if (connection == null)
                continue;
            voiceChatAPI.sendStaticSoundPacketTo(connection, event.getPacket().staticSoundPacketBuilder().build());
        }
    }

    private boolean canBroadcastToReceiver(PlayerEntity senderPlayer, PlayerEntity receiverPlayer, int receiverRange) {
        World senderWorld = senderPlayer.getWorld();
        World receiverWorld = receiverPlayer.getWorld();

        return Util.canBroadcastToReceiver(senderWorld, receiverWorld, senderPlayer.getPos(), receiverPlayer.getPos(), receiverRange);
    }

    public static SoundManager getInstance() {
        return instance;
    }

}
