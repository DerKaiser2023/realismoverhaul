package com.realisticdamage.realisticdamage.core;

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

        float damage = event.ammount;
        String part = pickHitBodyPart(); // Placeholder logic
        World world = event.entityLiving.worldObj;

        switch (part) {
            case "head":
                damage *= 3.0F;
                playSound(world, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "realisticdamage:effects.bone_crack");
                break;

            case "arm":
                if (event.entityLiving instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) event.entityLiving;
                    player.stopUsingItem(); // forcibly stop using held item
                    // may eventually flag in capability to fully disable item usage
                }
                playSound(world, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "realisticdamage:effects.bone_crack");
                break;

            case "leg":
                if (event.entityLiving instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) event.entityLiving;
                    player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 1)); // 5 seconds slowness
                }
                playSound(world, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "realisticdamage:effects.bone_crack");
                break;

            case "torso":
                // No specific torso logic yet, but might trigger bleedout or internal damage later
                break;
        }

        // Blood splatter/body fall sound on high damage
        if (damage > 6.0F) {
            playSound(world, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, "realisticdamage:effects.body_fall");
        }

        event.amount = damage;
    }

    private String pickHitBodyPart() {
        // Later replace with real raytrace/hitbox
        String[] parts = {"head", "torso", "arm", "leg"};
        return parts[(int) (Math.random() * parts.length)];
    }

    private void playSound(World world, double x, double y, double z, String sound) {
        if (!world.isRemote) {
            world.playSoundEffect(x, y, z, sound, 1.0F, 1.0F);
        }
    }
}