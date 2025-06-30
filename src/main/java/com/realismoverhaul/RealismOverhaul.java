package com.realismoverhaul;

import com.realismoverhaul.core.DamageHandler;
import com.realismoverhaul.event.OffHandEventHandler;
import com.realismoverhaul.input.KeyHandler;
import com.realismoverhaul.proxy.CommonProxy;
import com.realismoverhaul.capability.BodyStatusProvider;
import com.realismoverhaul.capability.BodyStatusStorage;
import com.realismoverhaul.capability.BodyStatus;
import com.realismoverhaul.capability.IBodyStatus;
import com.realismoverhaul.tick.PlayerTickHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

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
    proxy.registerRenderes();

    // Register capability
    CapabilityManager.INSTANCE.register(
        IBodyStatus.class,
        new BodyStatusStorage(),
        BodyStatus::new
    );

    // Register event handlers
    MinecraftForge.EVENT_BUS.register(new OffHandEventHandler());

    // Register capability attachment event handler
    MinecraftForge.EVENT_BUS.register(BodyStatusProvider.class);
}

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new DamageHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerTickHandler()); // NEW: Register tick handler
        KeyHandler.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // Future: load diseases, pollution config, etc
    }
}