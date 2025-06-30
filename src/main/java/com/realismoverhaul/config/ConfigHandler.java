package com.realismoverhaul.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static boolean allowBleeding = true;
    public static boolean enableCorpseLoot = true;
    public static float headshotMultiplier = 3.0F;

    // New feature toggles
    public static boolean enableHungerThirst = true;
    public static boolean enableInsomnia = true;
    public static boolean enableDiseases = true;
    public static boolean enableContamination = true;
    public static boolean enablePollution = true;

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        allowBleeding = config.getBoolean("allowBleeding", "general", true, "Allow bleeding effects");
        enableCorpseLoot = config.getBoolean("corpseLoot", "general", true, "Drop corpses instead of items");
        headshotMultiplier = config.getFloat("headshotMultiplier", "damage", 3.0F, 1.0F, 10.0F, "Headshot damage multiplier");

        // New feature configs
        enableHungerThirst = config.getBoolean("enableHungerThirst", "features", true, "Enable realistic hunger and thirst system");
        enableInsomnia = config.getBoolean("enableInsomnia", "features", true, "Enable insomnia effects and sleep system");
        enableDiseases = config.getBoolean("enableDiseases", "features", true, "Enable diseases and infections");
        enableContamination = config.getBoolean("enableContamination", "features", true, "Enable contamination system");
        enablePollution = config.getBoolean("enablePollution", "features", true, "Enable pollution effects and tracking");

        config.save();
    }
}
