package com.realismoverhaul.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.AttachCapabilitiesEvent;

public class BodyStatusProvider implements ICapabilitySerializable<NBTTagCompound> {

    public static final String KEY = "BodyStatus";
    @CapabilityInject(IBodyStatus.class)
    public static Capability<IBodyStatus> BODY_STATUS_CAP = null;

    private IBodyStatus instance = null;

    public BodyStatusProvider() {
        this.instance = new BodyStatus();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability == BODY_STATUS_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, net.minecraft.util.EnumFacing facing) {
        if (capability == BODY_STATUS_CAP) {
            return BODY_STATUS_CAP.<T>cast(this.instance);
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) BODY_STATUS_CAP.getStorage().writeNBT(BODY_STATUS_CAP, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        BODY_STATUS_CAP.getStorage().readNBT(BODY_STATUS_CAP, this.instance, null, nbt);
    }

    // Attach to player entity
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(new net.minecraft.util.ResourceLocation("realismoverhaul", KEY.toLowerCase()), new BodyStatusProvider());
        }
    }
}