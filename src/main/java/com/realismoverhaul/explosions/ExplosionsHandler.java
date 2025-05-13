package com.realisticdamage.realisticdamage.explosions;

import com.realisticdamage.realisticdamage.capability.IBodyStatus;
import com.realisticdamage.realisticdamage.capability.BodyStatusProvider;
import com.realisticdamage.realisticdamage.particles.BloodFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.common.network.NetworkRegistry;

import java.util.List;

public class ExplosionHandler {

    public static void createRealisticExplosion(World world, double x, double y, double z, float strength) {
        // Actual explosion
        world.createExplosion(null, x, y, z, strength, true);

        // Range of effect
        double radius = strength * 2.5;
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class,
                AxisAlignedBB.getBoundingBox(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));

        for (Entity entity : entities) {
            if (!(entity instanceof EntityLivingBase)) continue;

            double distance = entity.getDistance(x, y, z);
            if (distance > radius) continue;

            double exposure = 1.0 - (distance / radius); // 1 = close, 0 = far
            float baseDamage = strength * exposure * 4F;
            float shrapnelChance = 0.25F + (exposure * 0.5F); // 25%–75% chance

            EntityLivingBase living = (EntityLivingBase) entity;

            // Directional knockback
            double dx = entity.posX - x;
            double dy = entity.posY - y;
            double dz = entity.posZ - z;
            double mag = Math.sqrt(dx * dx + dy * dy + dz * dz) + 0.01;
            double knockback = exposure * 1.2;
            entity.motionX += dx / mag * knockback;
            entity.motionY += dy / mag * knockback;
            entity.motionZ += dz / mag * knockback;

            // Damage + shrapnel
            if (world.rand.nextFloat() < shrapnelChance) {
                // Pick random limb
                int limb = world.rand.nextInt(4); // 0=Head, 1=Arm, 2=Leg, 3=Torso
                IBodyStatus status = living.getCapability(BodyStatusProvider.BODY_STATUS_CAP, null);
                if (status != null) {
                    switch (limb) {
                        case 0: // Headshot
                            living.attackEntityFrom(DamageSource.generic, baseDamage * 2F);
                            status.setBleeding(true);
                            break;
                        case 1: // Arm
                            status.setLeftArmDisabled(true);
                            break;
                        case 2: // Leg
                            status.setRightLegDisabled(true);
                            break;
                        case 3: // Torso
                            living.attackEntityFrom(DamageSource.explosion, baseDamage);
                            status.setBleeding(true);
                            break;
                    }

                    // Spawn blood effect
                    BloodFX.spawnBlood(world, living.posX, living.posY + 1, living.posZ);
                }
            }
        }

        // TODO: add custom debris entity if i decide it would be cool)
    }
}