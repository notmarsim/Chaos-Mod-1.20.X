package net.marsim.chaosmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.marsim.chaosmod.ChaosMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DarklightRefinerScreen extends AbstractContainerScreen<DarklightRefinerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/void_refiner_gui.png");

    private static final ResourceLocation ENERGY_EMPTY =
            new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/empty_energy_bar.png");
    private static final ResourceLocation ENERGY_FULL =
            new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/full_energy_bar.png");


    private static final ResourceLocation CONFIG_BUTTON_TEXTURE =
            new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/configuration.png");

    public DarklightRefinerScreen(DarklightRefinerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;


        this.addRenderableWidget(new ImageButton(x, y, 16, 16, 0, 0, 0, CONFIG_BUTTON_TEXTURE, 16, 16,
                (button) -> Minecraft.getInstance().setScreen(new SideConfigScreen(this.menu.blockEntity, this))));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
        renderEnergyBar(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
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
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
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
