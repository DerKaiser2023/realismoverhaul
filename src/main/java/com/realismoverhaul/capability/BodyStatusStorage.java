package com.realismoverhaul.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class BodyStatusStorage implements IStorage<IBodyStatus> {

    @Override
    public NBTTagCompound writeNBT(Capability<IBodyStatus> capability, IBodyStatus instance, net.minecraft.util.EnumFacing side) {
        NBTTagCompound tag = new NBTTagCompound();

        // Save health per part
        tag.setFloat("headHealth", instance.getHealth("head"));
        tag.setFloat("torsoHealth", instance.getHealth("torso"));
        tag.setFloat("leftArmHealth", instance.getHealth("left_arm"));
        tag.setFloat("rightArmHealth", instance.getHealth("right_arm"));
        tag.setFloat("leftLegHealth", instance.getHealth("left_leg"));
        tag.setFloat("rightLegHealth", instance.getHealth("right_leg"));

        // Save hunger, thirst, temperature, freezing, sleep, sickness
        if (instance instanceof BodyStatus) {
            BodyStatus status = (BodyStatus) instance;

            tag.setFloat("hunger", status.getHunger());
            tag.setFloat("thirst", status.getThirst());
            tag.setFloat("bodyTemperature", status.getBodyTemperature());
            tag.setBoolean("isFreezing", status.isFreezing());
            tag.setInt("sleepTicks", status.getSleepTicks());
            tag.setBoolean("isSick", status.isSick());
            tag.setString("diseaseName", status.getDiseaseName() == null ? "" : status.getDiseaseName());
        }

        return tag;
    }

    @Override
    public void readNBT(Capability<IBodyStatus> capability, IBodyStatus instance, net.minecraft.util.EnumFacing side, NBTTagCompound nbt) {
        instance.setHealth("head", nbt.getFloat("headHealth"));
        instance.setHealth("torso", nbt.getFloat("torsoHealth"));
        instance.setHealth("left_arm", nbt.getFloat("leftArmHealth"));
        instance.setHealth("right_arm", nbt.getFloat("rightArmHealth"));
        instance.setHealth("left_leg", nbt.getFloat("leftLegHealth"));
        instance.setHealth("right_leg", nbt.getFloat("rightLegHealth"));

        if (instance instanceof BodyStatus) {
            BodyStatus status = (BodyStatus) instance;

            status.setHunger(nbt.getFloat("hunger"));
            status.setThirst(nbt.getFloat("thirst"));
            status.setBodyTemprature(nbt.getFloat("bodyTemperature"));
            status.setFreezing(nbt.getBoolean("isFreezing"));
            status.setSleepTicks(nbt.getInt("sleepTicks"));
            status.setSick(nbt.getBoolean("isSick"));
            status.setDiseaseName(nbt.getString("diseaseName"));
        }
    }
}