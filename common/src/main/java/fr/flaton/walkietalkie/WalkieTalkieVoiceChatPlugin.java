package fr.flaton.walkietalkie;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ForgeVoicechatPlugin
public class WalkieTalkieVoiceChatPlugin implements VoicechatPlugin {

    public final static String SPEAKER_CATEGORY = "speakers";

    @Nullable
    public static VoicechatServerApi voiceChatAPI;

    public static OpusEncoder encoder;
    public static OpusDecoder decoder;

    private ExecutorService executorService;

    public WalkieTalkieVoiceChatPlugin() {
        executorService = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName("WalkieTalkieAudioThread");
            thread.setUncaughtExceptionHandler((t, e) -> Constants.LOGGER.error("Error in WalkieTalkieAudioThread: " + e));
            thread.setDaemon(true);
            return thread;
        });
    }

    @Override
    public String getPluginId() {
        return Constants.MOD_ID;
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted);
        registration.registerEvent(MicrophonePacketEvent.class, micPacket -> executorService.submit(() -> SoundManager.getInstance().onMicPacket(micPacket)));
    }

    private void onServerStarted(VoicechatServerStartedEvent event) {
        voiceChatAPI = event.getVoicechat();
        encoder = voiceChatAPI.createEncoder();
        decoder = voiceChatAPI.createDecoder();

        VolumeCategory speakers = voiceChatAPI.volumeCategoryBuilder()
                .setId(SPEAKER_CATEGORY)
                .setName("Speakers")
                .setDescription("The volume of all speakers")
                .setIcon(getIcon("assets/walkietalkie/textures/block/speaker.png"))
                .build();
        voiceChatAPI.registerVolumeCategory(speakers);
    }

    @Nullable
    private int[][] getIcon(String path) {
        try {
            Enumeration<URL> resources = WalkieTalkieVoiceChatPlugin.class.getClassLoader().getResources(path);
            while (resources.hasMoreElements()) {
                BufferedImage bufferedImage = ImageIO.read(resources.nextElement().openStream());
                if (bufferedImage.getWidth() != 16) {
                    continue;
                }
                if (bufferedImage.getHeight() != 16) {
                    continue;
                }
                int[][] image = new int[16][16];
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        image[x][y] = bufferedImage.getRGB(x, y);
                    }
                }
                return image;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/*
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

        if (!WalkieTalkieItem.isActivate(senderItemStack)) {
            return;
        }

        if (WalkieTalkieItem.isMute(senderItemStack)) {
            return;
        }

        int senderCanal = WalkieTalkieItem.getCanal(senderItemStack);

        byte[] opusEncodedData = event.getPacket().getOpusEncodedData();

        if (ModConfig.applyRadioEffect) {
            short[] rawData = decoder.decode(opusEncodedData);

            opusEncodedData = encoder.encode(radioFilter.apply(rawData));
        }

        byte[] finalAudioData = opusEncodedData;
        SpeakerBlockEntity.getSpeakersActivatedInRange(senderCanal, senderPlayer.getWorld(), senderPlayer.getPos(), WalkieTalkieItem.getRange(senderItemStack))
                .forEach(speakerBlockEntity -> speakerBlockEntity.playSound(event));

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

            int receiverRange = WalkieTalkieItem.getRange(receiverStack);
            int receiverCanal = WalkieTalkieItem.getCanal(receiverStack);

            if (!canBroadcastToReceiver(senderPlayer, receiverPlayer, receiverRange)) {
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

            api.sendStaticSoundPacketTo(connection, event.getPacket().staticSoundPacketBuilder().opusEncodedData(finalAudioData).build());
        }
    }
 */

}