package com.realismoverhaul.core;

import com.realismoverhaul.capability.BodyStatusProvider;
import com.realismoverhaul.capability.IBodyStatus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class DamageHandler {

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if (event.entityLiving == null || event.source == null) return;
        if (!(event.entityLiving instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) event.entityLiving;
        IBodyStatus status = player.getCapability(BodyStatusProvider.BODY_STATUS_CAP, null);
        if (status == null) return;

        float damage = event.ammount;
        String part = pickHitBodyPart();
        World world = player.worldObj;

        // Apply custom logic
        switch (part) {
            case "head":
                damage *= 3.0F;
                status.setHealth("head", status.getHealth("head") - damage);
                playSound(world, player.posX, player.posY, player.posZ, "realisticdamage:effects.bone_crack");
                break;

            case "arm":
                status.setHealth("right_arm", status.getHealth("right_arm") - damage); // could randomize left/right
                player.stopUsingItem();
                playSound(world, player.posX, player.posY, player.posZ, "realisticdamage:effects.bone_crack");
                break;

            case "leg":
                status.setHealth("right_leg", status.getHealth("right_leg") - damage); // could randomize left/right
                player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1));
                playSound(world, player.posX, player.posY, player.posZ, "realisticdamage:effects.bone_crack");
                break;

            case "torso":
                status.setHealth("torso", status.getHealth("torso") - damage);
                // Future: bleedout risk or organ damage
                break;
        }

        // Trigger sound for hard impacts
        if (damage > 6.0F) {
            playSound(world, player.posX, player.posY, player.posZ, "realisticdamage:effects.body_fall");
        }

        event.ammount = damage;
    }

    private String pickHitBodyPart() {
        String[] parts = {"head", "torso", "arm", "leg"};
        return parts[(int) (Math.random() * parts.length)];
    }

    private void playSound(World world, double x, double y, double z, String sound) {
        if (!world.isRemote) {
            world.playSoundEffect(x, y, z, sound, 1.0F, 1.0F);
        }
    }
}