package com.example.examplemod;

import com.github.steveice10.netty.buffer.Unpooled;
import com.replaymod.recording.ReplayModRecording;
import com.replaymod.replaystudio.lib.viaversion.libs.mcstructs.core.Identifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import io.github.kosmx.emotes.api.proxy.INetworkInstance;
import io.github.kosmx.emotes.common.CommonData;
import io.github.kosmx.emotes.common.network.EmotePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "emotesreforgedplaycompat", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class PacketRecording {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketRecording.class);
    private static UUID currentId = null;
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(CommonData.MOD_ID, "main"),
            () -> "1.0",
            s -> true,
            s -> true
    );

    public static void saveC2SEmotePacket(KeyframeAnimation emoteData, UUID player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.getUUID().equals(player) && emoteData != null) {
            currentId = emoteData.getUuid();
            ReplayModRecording.instance.getConnectionEventHandler().getPacketListener().save(
                    new EmotePacket.Builder()
                            .configureToStreamEmote(emoteData, player)
                            .build().write().let(emotePacket -> {
                                byte[] data = INetworkInstance.safeGetBytesFromBuffer(emotePacket);
                                CHANNEL.sendToServer(new CustomPacket(data));
                                return null;
                            })
            );
        }
    }

    public static void saveC2SStopPacket() {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isLocalPlayer()) {
            UUID player = Minecraft.getInstance().player.getUUID();
            try {
                ByteBuffer buffer = new EmotePacket.Builder()
                        .configureToSendStop(currentId, player)
                        .build().write();
                // Use a custom method to handle the packet data
                handlePacketData(buffer);
            } catch (IOException e) {
                LOGGER.error("Failed to save C2S stop packet", e);
            }
        }
    }

    // Custom method to handle packet data
    private static void handlePacketData(ByteBuffer buffer) {
        // Convert ByteBuffer to byte array
        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        // Process the data using existing methods in ReplayModRecording or related classes
        // For example, you can use the existing methods to send the data to the server or handle it locally
        // ReplayModRecording.instance.getConnectionEventHandler().sendCustomPacket(data);
        // Or handle it locally
        // processPacketDataLocally(data);
    }

    public static class CustomPacket {
        private final byte[] data;

        public CustomPacket(byte[] data) {
            this.data = data;
        }

        public static void encode(CustomPacket packet, PacketBuffer buffer) {
            buffer.writeByteArray(packet.data);
        }

        public static CustomPacket decode(PacketBuffer buffer) {
            return new CustomPacket(buffer.readByteArray());
        }

        public static void handle(CustomPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                // Handle the packet data
            });
            context.setPacketHandled(true);
        }
    }
}