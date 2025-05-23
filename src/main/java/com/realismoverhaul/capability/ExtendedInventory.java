package com.realismoverhaul.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedInventory implements IExtendedEntityProperties {
    public static final String NAME = "ExtendedInventory";

    private ItemStack offhandItem;

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(NAME, new ExtendedInventory());
    }

    public static ExtendedInventory get(EntityPlayer player) {
        return (ExtendedInventory) player.getExtendedProperties(NAME);
    }

    public ItemStack getOffHandItem() {
        return offhandItem;
    }

    public void setOffhandItem(ItemStack itemStack) {
        this.offhandItem = itemStack;
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        if (offhandItem != null) {
            NBTTagCompound tag = new NBTTagCompound();
            offhandItem.writeToNBT(tag);
            compound.setTag("OffhandItem", tag);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey("OffhandItem")) {
            offhandItem = ItemStack.loadItemStackFromNBT(CommonProxy.getCompoundTag("OffhandItem"));
        }
    }

    @Override
    public void init(EntityPlayer player, net.minecraft.world.World world) {}
}