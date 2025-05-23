package com.realismoverhaul;

import com.realismoverhaul.core.DamageHandler;
import com.realismoverhaul.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RealismOverhaul.MODID, name = RealismOverhaul.NAME, version = RealismOverhaul.VERSION)
public class RealismOverhaul {
    public static final String MODID = "realismoverhaul";
    public static final String NAME = "Realism Overhaul";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "com.realismoverhaul.proxy.ClientProxy", serverSide = "com.realismoverhaul.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static RealismOverhaul instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerRenderes(); // Register client renderers (models, particles, etc)
        MinecraftForge.EVENT_BUS.register(new OffhandEventHandler()); // offhand register

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new DamageHandler()); // Register damage handling system
        KeyHandler.init();  // Registers key input
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}
}