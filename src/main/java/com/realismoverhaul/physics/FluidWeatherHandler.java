package com.realismoverhaul.physics;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Random;

public class FluidWeatherHandler {

    private static final float MAX_WIND_STRENGTH = 1.0f; // scale 0-1
    private static float windAngle = 0f; // 0 = East
    private static float windStrength = 0.5f;

    public static void updateWeatherPhysics(WorldServer world) {
        Random rand = world.rand;

        // 1. Update Wind Parameters Periodically
        if (world.getTotalWorldTime() % 600 == 0) { // Every 30 seconds (600 ticks)
            windAngle = rand.nextFloat() * 360f;
            windStrength = rand.nextFloat() * MAX_WIND_STRENGTH;
        }

        // 2. Simulate Fire Spread with Wind
        for (int x = 0; x < world.getWorldInfo().getSpawnX(); x += 16) {
            for (int z = 0; z < world.getWorldInfo().getSpawnZ(); z += 16) {
                for (int y = 0; y < 256; y++) {
                    Block block = world.getBlock(x, y, z);
                    if (block == Blocks.fire) {
                        trySpreadFire(world, x, y, z);
                    }
                }
            }
        }

        // 3. Simulate Directional Water Flow (very simple base logic)
        // Could replace with a fluid dynamics system later
        for (int x = 0; x < world.getWorldInfo().getSpawnX(); x += 16) {
            for (int z = 0; z < world.getWorldInfo().getSpawnZ(); z += 16) {
                for (int y = 0; y < 256; y++) {
                    Block block = world.getBlock(x, y, z);
                    if (block.getMaterial() == Material.water) {
                        flowWater(world, x, y, z);
                    }
                }
            }
        }
    }

    private static void trySpreadFire(World world, int x, int y, int z) {
        int[] dirX = {-1, 1, 0, 0};
        int[] dirZ = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int newX = x + dirX[i];
            int newZ = z + dirZ[i];
            int newY = y;

            Block target = world.getBlock(newX, newY, newZ);
            if (target.getMaterial().isReplaceable()) {
                float windDir = (float) Math.toDegrees(Math.atan2(dirZ[i], dirX[i]));
                float angleDiff = Math.abs(windDir - windAngle);
                if (angleDiff > 180f) angleDiff = 360f - angleDiff;

                float chance = windStrength * (1f - angleDiff / 180f); // More likely if wind points to fire
                if (world.rand.nextFloat() < chance) {
                    world.setBlock(newX, newY, newZ, Blocks.fire);
                }
            }
        }
    }

    private static void flowWater(World world, int x, int y, int z) {
        int windX = MathHelper.floor_double(Math.cos(Math.toRadians(windAngle)));
        int windZ = MathHelper.floor_double(Math.sin(Math.toRadians(windAngle)));

        int targetX = x + windX;
        int targetZ = z + windZ;

        Block targetBlock = world.getBlock(targetX, y, targetZ);
        if (targetBlock.isAir(world, targetX, y, targetZ)) {
            world.setBlock(targetX, y, targetZ, Blocks.water);
            world.setBlockToAir(x, y, z); // Optional: simulate pushing
        }
    }
}