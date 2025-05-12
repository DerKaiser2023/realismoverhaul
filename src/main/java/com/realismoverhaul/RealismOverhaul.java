package com.realismoverhaul;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RealismOverhaul.MODID, name = RealismOverhaul.NAME, version = RealismOverhaul.VERSION)
public class RealismOverhaul {
    public static final String MODID = "realismoverhaul";
    public static final String NAME = "Realism Overhaul";
    public static final String VERSION = "1.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Load config, register physics handlers
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Register events, world gen, physics logic
    }
}