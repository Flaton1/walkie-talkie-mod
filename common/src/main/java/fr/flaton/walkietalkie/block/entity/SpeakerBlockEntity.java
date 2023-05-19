package fr.flaton.walkietalkie.block.entity;

import de.maxhenkel.voicechat.api.Position;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.audiochannel.LocationalAudioChannel;
import de.maxhenkel.voicechat.api.packets.MicrophonePacket;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import fr.flaton.walkietalkie.config.ModConfig;
import fr.flaton.walkietalkie.screen.SpeakerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SpeakerBlockEntity extends BlockEntity implements ExtendedMenuProvider {

    private static final List<SpeakerBlockEntity> speakerBlockEntities = new ArrayList<>();

    public static final String NBT_KEY_ACTIVATE = "speaker.activate";
    public static final String NBT_KEY_CANAL = "speaker.canal";

    protected final PropertyDelegate propertyDelegate;

    int activate = 0;
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
                    case 0 -> SpeakerBlockEntity.this.activate;
                    case 1 -> SpeakerBlockEntity.this.canal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SpeakerBlockEntity.this.activate = value;
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
        return Text.translatable("gui.walkietalkie.speaker.title");
    }

    @Override
    public void saveExtraData(PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.activate = nbt.getInt(NBT_KEY_ACTIVATE);
        this.canal = nbt.getInt(NBT_KEY_CANAL);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(NBT_KEY_ACTIVATE, this.activate);
        nbt.putInt(NBT_KEY_CANAL, this.canal);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        return super.onSyncedBlockEvent(type, data);
    }

    public static List<SpeakerBlockEntity> getSpeakersActivateInRange(int canal, Vec3d pos, int range) {
        speakerBlockEntities.removeIf(BlockEntity::isRemoved);

        List<SpeakerBlockEntity> list = new ArrayList<>();

        for (SpeakerBlockEntity e : speakerBlockEntities) {

            if (!e.hasWorld()) {
                continue;
            }

            if (!pos.isInRange(new Vec3d(e.getPos().getX(), e.getPos().getY(), e.getPos().getX()), range)) {
                continue;
            }

            if (e.activate > 0) {
                if (e.canal == canal) {
                    list.add(e);
                }
            }
        }

        return list;
    }

    public void playSound(VoicechatServerApi api, MicrophonePacket packet) {
        Position pos = api.createPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());

        if (this.channel == null) {
            this.channel = api.createLocationalAudioChannel(this.channelId, api.fromServerLevel(this.world), pos);
            if (this.channel == null) {
                return;
            }
            this.channel.setDistance(ModConfig.speakerDistance + 1F);
        }

        this.channel.send(packet.getOpusEncodedData());
    }
}
