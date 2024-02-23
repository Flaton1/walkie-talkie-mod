package fr.flaton.walkietalkie.block.entity;

import de.maxhenkel.voicechat.api.Position;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.audiochannel.LocationalAudioChannel;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import fr.flaton.walkietalkie.Util;
import fr.flaton.walkietalkie.WalkieTalkieVoiceChatPlugin;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.screen.SpeakerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SpeakerBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {

    private static final List<SpeakerBlockEntity> speakerBlockEntities = new ArrayList<>();

    public static final String NBT_KEY_ACTIVATE = "speaker.activate";
    public static final String NBT_KEY_CANAL = "speaker.canal";

    protected final PropertyDelegate propertyDelegate;

    boolean activated;
    int canal = 1;

    private final UUID channelId;
    private LocationalAudioChannel channel = null;

    public SpeakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SPEAKER.get(), pos, state);
        speakerBlockEntities.add(this);

        channelId = UUID.randomUUID();

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SpeakerBlockEntity.this.activated ? 1 : 0;
                    case 1 -> SpeakerBlockEntity.this.canal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SpeakerBlockEntity.this.activated = value == 1;
                    case 1 -> SpeakerBlockEntity.this.canal = value;
                    default -> {
                    }
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SpeakerScreenHandler(syncId, this.propertyDelegate, ScreenHandlerContext.create(this.world, this.getPos()));
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("gui.walkietalkie.speaker.title");
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.activated = nbt.getBoolean(NBT_KEY_ACTIVATE);
        this.canal = nbt.getInt(NBT_KEY_CANAL);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean(NBT_KEY_ACTIVATE, this.activated);
        nbt.putInt(NBT_KEY_CANAL, this.canal);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        return super.onSyncedBlockEvent(type, data);
    }

    public static List<SpeakerBlockEntity> getSpeakersActivatedInRange(int canal, World world, Vec3d pos, int range) {
        speakerBlockEntities.removeIf(BlockEntity::isRemoved);

        List<SpeakerBlockEntity> list = new ArrayList<>();

        for (SpeakerBlockEntity speaker : speakerBlockEntities) {

            if (!speaker.hasWorld()) {
                continue;
            }

            if (!ModConfig.crossDimensionsEnabled
                    && !world.getRegistryKey().getValue().equals(speaker.getWorld().getRegistryKey().getValue())) {
                continue;
            }

            if (!speaker.canBroadcastToSpeaker(world, pos, speaker, range)) {
                continue;
            }

            if (speaker.activated) {
                if (speaker.canal == canal) {
                    list.add(speaker);
                }
            }
        }

        return list;
    }

    public void playSound(VoicechatServerApi api, MicrophonePacketEvent event) {
        Position pos = api.createPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());

        if (this.channel == null) {
            this.channel = api.createLocationalAudioChannel(this.channelId, api.fromServerLevel(this.world), pos);
            if (this.channel == null) {
                return;
            }
            this.channel.setCategory(WalkieTalkieVoiceChatPlugin.SPEAKER_CATEGORY);
            this.channel.setDistance(ModConfig.speakerDistance + 1F);
            if (!ModConfig.voiceDuplication) {
                this.channel.setFilter(serverPlayer -> !serverPlayer.getEntity().equals(event.getSenderConnection().getPlayer().getEntity()));
            }
        }
        this.channel.send(event.getPacket().getOpusEncodedData());
    }

    private boolean canBroadcastToSpeaker(World senderWorld, Vec3d senderPos, SpeakerBlockEntity speaker, int range) {
        World receiverWorld = speaker.getWorld();

        if (receiverWorld == null) {
            return false;
        }

        return Util.canBroadcastToReceiver(senderWorld, receiverWorld, senderPos, Vec3d.ofCenter(speaker.pos), range);
    }
}
