package com.realismoverhaul.client;

import com.realismoverhaul.capability.ExtendedInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyHandler {
    public static KeyBinding useOffhandKey = new KeyBinding("Use Offhand", Keyboard.KEY_R, "Realism Overhaul");

    public static void init() {
        ClientRegistry.registerKeyBinding(useOffhandKey);
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (useOffhandKey.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player == null) return;

            ItemStack offhand = ExtendedInventory.get(player).getOffhandItem();
            if (offhand != null) {
                Item item = offhand.getItem();
                World world = player.worldObj;

                // Simulate item use
                item.onItemRightClick(offhand, world, player);
            }
        }
    }
}