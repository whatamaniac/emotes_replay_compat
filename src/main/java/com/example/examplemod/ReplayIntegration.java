package com.example.examplemod;

import com.replaymod.recording.ReplayModRecording;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraftforge.eventbus.api.Event;

@Mod.EventBusSubscriber(modid = "emotesreforgedplaycompat")
public class ReplayIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplayIntegration.class);

    @SubscribeEvent
    public static void onReplayEvent(Event event) {
        // Ensure ReplayModRecording instance is not null
        ReplayModRecording recording = ReplayModRecording.instance;
        if (recording == null) {
            LOGGER.warn("ReplayModRecording instance is null, skipping event handling");
            return;
        }


    }
}