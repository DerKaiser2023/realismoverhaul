package com.realismoverhaul.event;

import com.realismoverhaul.capability.ExtendedInventory;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraft.entity.player.EntityPlayer;

public class OffhandEventHandler {

    @SubscribeEvent
    public void onConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (ExtendedInventory.get(player) == null) {
                ExtendedInventory.register(player);
            }
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        ExtendedInventory oldInv = ExtendedInventory.get(event.original);
        ExtendedInventory newInv = ExtendedInventory.get(event.entityPlayer);
        newInv.setOffhandItem(oldInv.getOffhandItem());
    }
}