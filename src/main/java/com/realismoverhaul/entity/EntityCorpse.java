package com.realisticdamage.realisticdamage.entity;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityCorpse extends EntityArmorStand {

    private String playerUsername = null;

    public EntityCorpse(World world) {
        super(world);
        this.setInvisible(false); // visible armor stand
        this.setAlwaysRenderNameTag(false);
        this.setNoAI(true);
    }

    public EntityCorpse(World world, EntityPlayer player) {
        super(world, player.posX, player.posY, player.posZ);

        // Copy player name (used for skin)
        this.playerUsername = player.getCommandSenderName();

        // Copy armor contents
        for (int i = 0; i < 4; i++) {
            ItemStack armorPiece = player.getCurrentArmor(i); // boots = 0 ... helmet = 3
            this.setCurrentItemOrArmor(i + 1, armorPiece);
        }

        // Copy held item
        ItemStack heldItem = player.getHeldItem();
        this.setCurrentItemOrArmor(0, heldItem);

        // Optional: store full inventory (to open on interaction)
        // Custom inventory NBT storage could go here
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionX = this.motionY = this.motionZ = 0; // stay still
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setString("CorpseUsername", playerUsername == null ? "" : playerUsername);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.playerUsername = tag.getString("CorpseUsername");
    }

    public ResourceLocation getSkinTexture() {
        // Uses Minecraft skin server. Placeholder logic; client will need to fetch and render it.
        if (playerUsername != null && !playerUsername.isEmpty()) {
            return new ResourceLocation("http://skins.minecraft.net/MinecraftSkins/" + playerUsername + ".png");
        }
        return new ResourceLocation("textures/entity/steve.png");
    }
}