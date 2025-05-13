package com.realismoverhaul.common;

import com.realismoverhaul.tile.TileEntityRainCollector;
import com.realismoverhaul.tile.TileEntityWindTurbine;
import com.realismoverhaul.network.PacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void init() {
        // Register server-side tile entities
        GameRegistry.registerTileEntity(TileEntityRainCollector.class, "TileEntityRainCollector");
        GameRegistry.registerTileEntity(TileEntityWindTurbine.class, "TileEntityWindTurbine");

        // Register packet handler or server-only logic
        PacketHandler.init(); // Set up networking, if applicable

        // Any other server-side setup goes here
        System.out.println("[RealismOverhaul] Server-side CommonProxy initialized.");
    }
}