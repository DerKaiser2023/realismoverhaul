package com.realismoverhaul.physics;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;

import java.util.Random;

public class TerrainGenerator {

    private static NoiseGeneratorOctaves temperatureNoise;
    private static NoiseGeneratorOctaves rainfallNoise;
    private static NoiseGeneratorPerlin elevationNoise;

    public static void initClimateGenerators(World world, Random rand) {
        temperatureNoise = new NoiseGeneratorOctaves(rand, 4);
        rainfallNoise = new NoiseGeneratorOctaves(rand, 4);
        elevationNoise = new NoiseGeneratorPerlin(rand, 1);
    }

    public static void generateRealisticTerrain(World world, IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        initClimateGenerators(world, new Random(world.getSeed()));

        int worldX = chunkX * 16;
        int worldZ = chunkZ * 16;

        Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int globalX = worldX + x;
                int globalZ = worldZ + z;

                float temperature = getTemperature(globalX, globalZ);
                float rainfall = getRainfall(globalX, globalZ);
                float elevation = getElevation(globalX, globalZ);

                BiomeGenBase biome = getBiomeForClimate(temperature, rainfall, elevation);

                // Apply biome (you'd need to use reflection or a custom chunk provider for this)
                // world.getChunkProvider().loadChunk(chunkX, chunkZ).setBiomeArray(...) <-- simplified
            }
        }
    }

    private static float getTemperature(int x, int z) {
        return (float) temperatureNoise.generateNoiseOctaves(x, z, 1, 1, 0.01, 0.01, 0.5)[0];
    }

    private static float getRainfall(int x, int z) {
        return (float) rainfallNoise.generateNoiseOctaves(x, z, 1, 1, 0.01, 0.01, 0.5)[0];
    }

    private static float getElevation(int x, int z) {
        return (float) elevationNoise.func_151601_a(x * 0.005, z * 0.005);
    }

    private static BiomeGenBase getBiomeForClimate(float temperature, float rainfall, float elevation) {
        if (temperature < -0.2) {
            return BiomeGenBase.icePlains;
        } else if (rainfall > 0.5f && temperature > 0.3f) {
            return BiomeGenBase.jungle;
        } else if (rainfall < 0.2f) {
            return BiomeGenBase.desert;
        } else if (elevation > 0.6f) {
            return BiomeGenBase.extremeHills;
        } else {
            return BiomeGenBase.forest;
        }
    }
}