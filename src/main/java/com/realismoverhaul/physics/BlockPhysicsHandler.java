package com.realismoverhaul.physics;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockPhysicsHandler {

    private static final Set<Block> gravityAffectedBlocks = new HashSet<>();

    static {
        // Add more as desired
        gravityAffectedBlocks.add(Blocks.cobblestone);
        gravityAffectedBlocks.add(Blocks.stonebrick);
        gravityAffectedBlocks.add(Blocks.brick_block);
        gravityAffectedBlocks.add(Blocks.planks);
        gravityAffectedBlocks.add(Blocks.stone);
    }

    public static void handleBlockGravity() {
        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new BlockPhysicsHandler());
    }

    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event) {
        World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;

        // Check nearby blocks for unsupported gravity
        checkBlockStability(world, x + 1, y, z);
        checkBlockStability(world, x - 1, y, z);
        checkBlockStability(world, x, y + 1, z);
        checkBlockStability(world, x, y - 1, z);
        checkBlockStability(world, x, y, z + 1);
        checkBlockStability(world, x, y, z - 1);
    }

    private void checkBlockStability(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);

        if (!gravityAffectedBlocks.contains(block)) return;

        // Check if block below is air or unstable
        if (!isBlockSupported(world, x, y, z)) {
            makeBlockFall(world, x, y, z, block);
        }
    }

    private boolean isBlockSupported(World world, int x, int y, int z) {
        // Simple support rule: block is supported if block below is solid or surrounded on 2+ sides
        Block below = world.getBlock(x, y - 1, z);
        if (below.getMaterial().isSolid()) return true;

        int supports = 0;
        if (world.getBlock(x + 1, y, z).getMaterial().isSolid()) supports++;
        if (world.getBlock(x - 1, y, z).getMaterial().isSolid()) supports++;
        if (world.getBlock(x, y, z + 1).getMaterial().isSolid()) supports++;
        if (world.getBlock(x, y, z - 1).getMaterial().isSolid()) supports++;

        return supports >= 2;
    }

    private void makeBlockFall(World world, int x, int y, int z, Block block) {
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockToAir(x, y, z);

        for (int fallY = y - 1; fallY >= 0; fallY--) {
            Block target = world.getBlock(x, fallY, z);
            if (target.isAir(world, x, fallY, z)) continue;

            // Found ground; place block above it
            world.setBlock(x, fallY + 1, z, block, meta, 2);
            return;
        }

        // Fell to bottom
        world.setBlock(x, 0, z, block, meta, 2);
    }
}