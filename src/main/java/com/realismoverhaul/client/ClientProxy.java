package com.realismoverhaul.client;

import com.realismoverhaul.common.CommonProxy;
import com.realismoverhaul.gui.GuiBodyStatus;
import com.realismoverhaul.input.KeyHandler;
import com.realismoverhaul.tile.TileEntityRainCollector;
import com.realismoverhaul.client.renderer.RenderRainCollector;
import com.realismoverhaul.init.ROBlocks;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderes() {
        // Tile Entity Renderer
        TileEntitySpecialRenderer rainCollectorRenderer = new RenderRainCollector();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRainCollector.class, rainCollectorRenderer);
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ROBlocks.rainCollector), new RenderRainCollector());

        System.out.println("[RealismOverhaul] Client-side renderers initialized.");
    }

    @Override
    public void registerKeybinds() {
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    @Override
    public void openBodyStatusGui() {
        Minecraft.getMinecraft().displayGuiScreen(new GuiBodyStatus());
    }
}