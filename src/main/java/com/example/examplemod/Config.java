package com.example.examplemod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = "emotesreforgedplaycompat", bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> magicNumberIntroduction;
    public static final ForgeConfigSpec.ConfigValue<Integer> magicNumber;
    public static final ForgeConfigSpec.ConfigValue<Boolean> logDirtBlock;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> items;

    static {
        BUILDER.push("General");
        magicNumberIntroduction = BUILDER.comment("What you want the introduction message to be for the magic number")
                .define("magicNumberIntroduction", "The magic number is... ");
        magicNumber = BUILDER.comment("The magic number")
                .define("magicNumber", 42);
        logDirtBlock = BUILDER.comment("Log the dirt block")
                .define("logDirtBlock", true);
        items = BUILDER.comment("List of items")
                .defineList("items", List.of("minecraft:stone", "minecraft:dirt"), item -> item instanceof String);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        // Handle config loading
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
        // Handle config reloading
    }
}