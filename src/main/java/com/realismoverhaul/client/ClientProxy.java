package com.realismoverhaul.client;

import com.realismoverhaul.common.CommonProxy;
import com.realismoverhaul.init.ROBlocks;
import com.realismoverhaul.init.ROItems;
import com.realismoverhaul.tile.TileEntityRainCollector;
import com.realismoverhaul.client.renderer.RenderRainCollector;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void initRenderers() {
        // Example: Bind a custom renderer to a tile entity
        TileEntitySpecialRenderer rainCollectorRenderer = new RenderRainCollector();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRainCollector.class, rainCollectorRenderer);

        // Register item renderers (example only)
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ROBlocks.rainCollector), new RenderRainCollector());

        // Log successful renderer setup
        System.out.println("[RealismOverhaul] Client-side renderers initialized.");
    }
}