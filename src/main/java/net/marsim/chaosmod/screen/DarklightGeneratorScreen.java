package net.marsim.chaosmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.marsim.chaosmod.ChaosMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DarklightGeneratorScreen extends AbstractContainerScreen<DarklightGeneratorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/energy.png");
    private static final ResourceLocation ENERGY_EMPTY = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/empty_energy_bar.png");
    private static final ResourceLocation ENERGY_FULL = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/full_energy_bar.png");

    public DarklightGeneratorScreen(DarklightGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderEnergyBar(guiGraphics, x, y);
    }

    private void renderEnergyBar(GuiGraphics guiGraphics, int x, int y) {
        int barX = x + 154;
        int barY = y + 27;
        int barWidth = 6;
        int barHeight = 32;
        int energy = menu.getEnergyStored();
        int maxEnergy = menu.getMaxEnergyStored();
        if (maxEnergy == 0) return;
        int filled = (int)((double)energy / maxEnergy * barHeight);

        guiGraphics.blit(ENERGY_EMPTY, barX, barY, 0, 0, barWidth, barHeight, barWidth, barHeight);

        if (filled > 0) {
            guiGraphics.blit(ENERGY_FULL, barX, barY + (barHeight - filled), 0, barHeight - filled, barWidth, filled, barWidth, barHeight);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int barX = (width - imageWidth) / 2 + 154;
        int barY = (height - imageHeight) / 2 + 27;
        int barWidth = 6;
        int barHeight = 32;

        if (mouseX >= barX && mouseX < barX + barWidth && mouseY >= barY && mouseY < barY + barHeight) {
            int energy = menu.getEnergyStored();
            int maxEnergy = menu.getMaxEnergyStored();

            Component tooltip = Component.translatable("tooltip.chaosmod.energy_bar",
                    String.format("%,d", energy), String.format("%,d", maxEnergy));
            guiGraphics.renderTooltip(font, tooltip, mouseX, mouseY);
        }

        super.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
