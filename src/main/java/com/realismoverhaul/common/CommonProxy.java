package com.realismoverhaul.common;

import com.realismoverhaul.network.PacketHandler;

public class CommonProxy {
    public void init() {
        // Register server-side tile entities if needed

        PacketHandler.init(); // Set up networking

        System.out.println("[RealismOverhaul] Server-side CommonProxy initialized.");
    }

    // Stub methods for client-only calls
    public void registerRenderers() {
        // No-op on server
    }

    public void registerKeybinds() {
        // No-op on server
    }

    public void openBodyStatusGui() {
        // No GUI on server
    }
}