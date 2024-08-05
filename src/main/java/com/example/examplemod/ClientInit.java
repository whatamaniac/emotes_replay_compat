package com.example.examplemod;

import io.github.kosmx.emotes.api.events.client.ClientEmoteEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod.EventBusSubscriber(modid = "emotesreforgedplaycompat", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    private static final String MODID = "emotes-compat-rp";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        initializeClient();
    }

    public static void initializeClient() {
        LOGGER.info("Hello Forge");

        // Save play emote c2s packet as s2c
        ClientEmoteEvents.EMOTE_PLAY.register((emoteData, userID) -> PacketRecording.saveC2SEmotePacket(emoteData, userID));
        ClientEmoteEvents.LOCAL_EMOTE_STOP.register(PacketRecording.saveC2SStopPacket());
    }

}