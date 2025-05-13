package com.realisticdamage.realisticdamage.gui;

import net.minecraft.client.gui.GuiScreen;

public class GuiBodyStatus extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Body Damage", this.width / 2, 20, 0xFFFFFF);
        // Draw health bars here
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}