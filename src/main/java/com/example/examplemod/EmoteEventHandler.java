package com.example.examplemod;

import com.replaymod.recording.ReplayModRecording;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod.EventBusSubscriber(modid = "emotesreforgedplaycompat")
public class EmoteEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmoteEventHandler.class);

    @SubscribeEvent
    public static void onPlayerEmote(PlayerEvent event) {
        // Capture the emote action
        LOGGER.info("Player {} performed an emote", event.getEntity().getName().getString());

        // Add logic to record the emote action in the replay data
        ReplayModRecording recording = ReplayModRecording.instance;
        if (recording != null) {
            recording.getConnectionEventHandler().getPacketListener().addMarker("Emote: " + event.getEntity().getName().getString());
            LOGGER.info("Recorded emote action for replay");
        }
    }
}