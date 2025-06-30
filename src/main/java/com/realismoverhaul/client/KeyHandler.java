package com.realismoverhaul.client;

import com.realismoverhaul.RealismOverhaul;
import com.realismoverhaul.capability.ExtendedInventory;
import com.realismoverhaul.gui.GuiBodyStatus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

import org.lwjgl.input.Keyboard;

public class KeyHandler {
    public static KeyBinding useOffhandKey = new KeyBinding("Use Offhand", Keyboard.KEY_R, "Realism Overhaul");
    public static KeyBinding openBodyStatusKey = new KeyBinding("Open Body Status", Keyboard.KEY_B, "Realism Overhaul");

    public static void init() {
        ClientRegistry.registerKeyBinding(useOffhandKey);
        ClientRegistry.registerKeyBinding(openBodyStatusKey);
        cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;

        // Use offhand item
        if (useOffhandKey.isPressed()) {
            ItemStack offhand = ExtendedInventory.get(player).getOffhandItem();
            if (offhand != null) {
                Item item = offhand.getItem();
                World world = player.worldObj;
                item.onItemRightClick(offhand, world, player);
            }
        }

        // Open body status GUI
        if (openBodyStatusKey.isPressed()) {
            RealismOverhaul.proxy.openBodyStatusGui();
        }
    }
}