package com.realismoverhaul.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static boolean allowBleeding = true;
    public static boolean enableCorpseLoot = true;
    public static float headshotMultiplier = 3.0F;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        allowBleeding = config.getBoolean("allowBleeding", "general", true, "Allow bleeding effects");
        enableCorpseLoot = config.getBoolean("corpseLoot", "general", true, "Drop corpses instead of items");
        headshotMultiplier = config.getFloat("headshotMultipler", "damage", 3.0F, 1.0F, 10.0F, "Headshot damage multiplier");

        config.save();
    }
}
