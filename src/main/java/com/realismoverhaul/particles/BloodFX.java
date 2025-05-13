package com.realisticdamage.realisticdamage.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class BloodFX {

    /**
     * Spawns red particles to simulate blood.
     * @param world The world to spawn particles in.
     * @param x The x position.
     * @param y The y position.
     * @param z The z position.
     */
    public static void spawnBlood(World world, double x, double y, double z) {
        if (world.isRemote) {
            for (int i = 0; i < 10 + world.rand.nextInt(10); i++) {
                double dx = (world.rand.nextDouble() - 0.5) * 0.2;
                double dy = (world.rand.nextDouble()) * 0.2;
                double dz = (world.rand.nextDouble() - 0.5) * 0.2;

                Minecraft.getMinecraft().effectRenderer.addEffect(
                        new net.minecraft.client.particle.EntityFX(
                                world, x, y, z, dx, dy, dz
                        ) {
                            {
                                this.particleRed = 0.6F + rand.nextFloat() * 0.3F;
                                this.particleGreen = 0.0F;
                                this.particleBlue = 0.0F;
                                this.particleScale = 0.1F + rand.nextFloat() * 0.2F;
                                this.particleMaxAge = 20 + rand.nextInt(10);
                            }

                            @Override
                            public void onUpdate() {
                                super.onUpdate();
                                motionY -= 0.01D; // gravity
                            }
                        }
                );
            }
        }
    }
}