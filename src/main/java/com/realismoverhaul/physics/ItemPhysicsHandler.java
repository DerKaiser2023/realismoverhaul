package com.realismoverhaul.physics;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ItemPhysicsHandler {

    // Example weights (mass in kg or abstract units)
    private static final Map<String, Float> itemWeights = new HashMap<>();

    static {
        itemWeights.put("minecraft:feather", 0.05f);
        itemWeights.put("minecraft:stone", 1.5f);
        itemWeights.put("minecraft:gold_ingot", 2.0f);
        itemWeights.put("minecraft:iron_sword", 1.0f);
        itemWeights.put("minecraft:diamond", 0.6f);
        // Add more or pull from config
    }

    /**
     * Call this during init to register physics event handlers
     */
    public static void applyItemPhysics() {
        // You must register this class as an event handler from your mod init class:
        // MinecraftForge.EVENT_BUS.register(new ItemPhysicsHandler());
    }

    @SubscribeEvent
    public void onItemSpawn(EntityJoinWorldEvent event) {
        if (!(event.entity instanceof EntityItem)) return;

        EntityItem itemEntity = (EntityItem) event.entity;
        applyPhysicsToItem(itemEntity);
    }

    private void applyPhysicsToItem(EntityItem item) {
        ItemStack stack = item.getEntityItem();
        if (stack == null || stack.getItem() == null) return;

        float weight = getItemWeight(stack);
        float resistance = MathHelper.clamp_float(weight / 2.0f, 0.1f, 5.0f);

        // Apply drag and rotational slowdown
        item.motionX *= 0.9f / resistance;
        item.motionZ *= 0.9f / resistance;

        // Heavy items fall faster
        item.motionY -= 0.03f * weight;

        // Set no bounce if heavy
        if (weight > 1.5f && item.onGround) {
            item.motionX = 0;
            item.motionZ = 0;
        }

        // Optional: mark item as "affected" so it's not modified every tick
        if (!item.getEntityData().hasKey("RealismPhysicsApplied")) {
            item.setEntityItemStack(stack); // Refresh stack state
            item.getEntityData().setBoolean("RealismPhysicsApplied", true);
        }
    }

    private float getItemWeight(ItemStack stack) {
        String name = stack.getItem().getUnlocalizedName().toLowerCase();
        return itemWeights.getOrDefault(name, 1.0f); // default to 1.0f
    }
}