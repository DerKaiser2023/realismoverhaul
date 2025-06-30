package com.realismoverhaul.event;

import com.realismoverhaul.capability.ExtendedInventory;
import com.realismoverhaul.capability.BodyStatus;
import com.realismoverhaul.capability.BodyStatusProvider;
import com.realismoverhaul.capability.IBodyStatus;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class OffHandEventHandler {

    @SubscribeEvent
    public void onConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            // Offhand setup
            if (ExtendedInventory.get(player) == null) {
                ExtendedInventory.register(player);
            }

            // Attach BodyStatus capability manually for early initialization (safe fallback)
            if (!player.hasCapability(BodyStatusProvider.BODY_STATUS_CAP, null)) {
                player.registerExtendedProperties(BodyStatusProvider.KEY, new BodyStatusProvider.BodyStatusExtended(player));
            }
        }
    }

    @SubscribeEvent
    public void onClone(PlayerEvent.Clone event) {
        // Copy offhand
        ExtendedInventory oldInv = ExtendedInventory.get(event.original);
        ExtendedInventory newInv = ExtendedInventory.get(event.entityPlayer);
        newInv.setOffhandItem(oldInv.getOffhandItem());

        // Copy body status
        IBodyStatus oldStatus = event.original.getCapability(BodyStatusProvider.BODY_STATUS_CAP, null);
        IBodyStatus newStatus = event.entityPlayer.getCapability(BodyStatusProvider.BODY_STATUS_CAP, null);

        if (oldStatus != null && newStatus != null) {
            newStatus.setHunger(oldStatus.getHunger());
            newStatus.setThirst(oldStatus.getThirst());
            newStatus.setBodyTemprature(oldStatus.getBodyTemperature());
            newStatus.setFreezing(oldStatus.isFreezing());
            newStatus.setSick(oldStatus.isSick());
            newStatus.setDiseaseName(oldStatus.getDiseaseName());
            newStatus.addSleepTicks(oldStatus.getSleepTicks());
        }
    }
}