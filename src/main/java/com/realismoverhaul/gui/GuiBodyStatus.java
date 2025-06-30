package com.realismoverhaul.gui;

import com.realismoverhaul.capability.BodyStatusProvider;
import com.realismoverhaul.capability.IBodyStatus;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiBodyStatus extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Body Status", this.width / 2, 10, 0xFFFFFF);

        EntityPlayer player = this.mc.thePlayer;
        IBodyStatus status = player.getCapability(BodyStatusProvider.BODY_STATUS_CAP, null);
        if (status == null) return;

        int startX = this.width / 2 - 90;
        int y = 30;
        int barWidth = 100;
        int barHeight = 8;

        drawLabelBar("Head", status.getHealth("head"), 100f, startX, y); y += 12;
        drawLabelBar("Torso", status.getHealth("torso"), 100f, startX, y); y += 12;
        drawLabelBar("Left Arm", status.getHealth("left_arm"), 100f, startX, y); y += 12;
        drawLabelBar("Right Arm", status.getHealth("right_arm"), 100f, startX, y); y += 12;
        drawLabelBar("Left Leg", status.getHealth("left_leg"), 100f, startX, y); y += 12;
        drawLabelBar("Right Leg", status.getHealth("right_leg"), 100f, startX, y); y += 20;

        drawLabelBar("Hunger", status.getHunger(), 20f, startX, y); y += 12;
        drawLabelBar("Thirst", status.getThirst(), 20f, startX, y); y += 12;

        int sleep = status.getSleepTicks();
        drawLabelBar("Insomnia", Math.min(sleep, 24000), 24000f, startX, y); y += 20;

        if (status.isSick()) {
            drawCenteredString(this.fontRendererObj, "Sick: " + status.getDiseaseName(), this.width / 2, y, 0xFF5555);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawLabelBar(String label, float value, float max, int x, int y) {
        int barWidth = 100;
        int filled = (int)((value / max) * barWidth);

        drawString(this.fontRendererObj, label, x, y, 0xCCCCCC);

        // Background
        drawRect(x + 50, y, x + 50 + barWidth, y + 8, 0xFF333333);
        // Foreground
        int color = (value / max) < 0.3 ? 0xFFAA0000 : 0xFF00AA00;
        drawRect(x + 50, y, x + 50 + filled, y + 8, color);
    }
}