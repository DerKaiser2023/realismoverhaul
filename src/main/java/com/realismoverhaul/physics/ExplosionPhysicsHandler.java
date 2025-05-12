package com.realismoverhaul.physics;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExplosionPhysicsHandler {

    /**
     * Call this to simulate a realistic explosion at a given point.
     * @param world The world object
     * @param x Explosion center X
     * @param y Explosion center Y
     * @param z Explosion center Z
     * @param power Explosion size
     * @param direction Vector representing blast direction, normalized
     */
    public static void simulateExplosion(World world, double x, double y, double z, float power, Vec3 direction) {
        // 1. Trigger base explosion (no terrain damage here)
        Explosion explosion = new Explosion(world, null, x, y, z, power);
        explosion.isFlaming = false;
        explosion.isSmoking = false; // prevent vanilla block breaking
        explosion.doExplosionA(); // calculates affected entities

        // 2. Apply directional knockback to entities
        applyShockwave(world, x, y, z, power, direction);

        // 3. Destroy terrain in a cone based on direction
        if (!world.isRemote) {
            destroyBlocksDirectionally((WorldServer) world, x, y, z, power, direction);
        }
    }

    private static void applyShockwave(World world, double x, double y, double z, float power, Vec3 direction) {
        double radius = power * 2;
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class,
                net.minecraft.util.AxisAlignedBB.getBoundingBox(
                        x - radius, y - radius, z - radius,
                        x + radius, y + radius, z + radius
                )
        );

        for (Entity entity : entities) {
            Vec3 toEntity = Vec3.createVectorHelper(
                    entity.posX - x,
                    entity.posY - y,
                    entity.posZ - z
            ).normalize();

            double alignment = toEntity.dotProduct(direction);
            if (alignment > 0.3) { // only affect those in general direction
                double force = alignment * power * 2;
                entity.motionX += direction.xCoord * force;
                entity.motionY += direction.yCoord * force;
                entity.motionZ += direction.zCoord * force;
                entity.velocityChanged = true;
            }
        }
    }

    private static void destroyBlocksDirectionally(WorldServer world, double x, double y, double z, float power, Vec3 direction) {
        Set<Block> destructible = new HashSet<>();
        destructible.add(net.minecraft.init.Blocks.dirt);
        destructible.add(net.minecraft.init.Blocks.grass);
        destructible.add(net.minecraft.init.Blocks.stone);
        destructible.add(net.minecraft.init.Blocks.sand);

        int range = (int) (power * 3);
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                for (int dz = -range; dz <= range; dz++) {
                    double bx = x + dx;
                    double by = y + dy;
                    double bz = z + dz;

                    Vec3 dirToBlock = Vec3.createVectorHelper(dx, dy, dz).normalize();
                    double dot = dirToBlock.dotProduct(direction);
                    if (dot < 0.5) continue; // only destroy in direction of explosion

                    double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    if (dist > range) continue;

                    Block block = world.getBlock((int) bx, (int) by, (int) bz);
                    if (destructible.contains(block)) {
                        float chance = (float) (1.0 - (dist / range));
                        if (world.rand.nextFloat() < chance) {
                            world.setBlockToAir((int) bx, (int) by, (int) bz);
                        }
                    }
                }
            }
        }
    }
}