package net.marsim.chaosmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.entity.StellarGeneratorEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SideConfigScreen extends Screen {
    private static final ResourceLocation PANEL_TEXTURE = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/energy_configuration.png");
    private static final ResourceLocation CLOSE_BUTTON_TEXTURE = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/close.png");

    private final StellarGeneratorEntity blockEntity;
    private final Screen parentScreen;

    private final int panelWidth = 104;
    private final int panelHeight = 104;

    public SideConfigScreen(StellarGeneratorEntity be, Screen parent) {
        super(Component.literal("Side Configuration"));
        this.blockEntity = be;
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - this.panelWidth) / 2;
        int y = (this.height - this.panelHeight) / 2;

        this.addRenderableWidget(new ImageButton(x + this.panelWidth - 16, y, 16, 16, 0, 0, 0,
                CLOSE_BUTTON_TEXTURE, 16, 16, button -> this.onClose()));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

        this.parentScreen.render(guiGraphics, -1, -1, pPartialTick);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, 400.0F);


        guiGraphics.fill(0, 0, this.width, this.height, 0x80000000);


        renderPanel(guiGraphics);

        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

        guiGraphics.pose().popPose();
    }

    private void renderPanel(GuiGraphics guiGraphics) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();

        int x = (this.width - this.panelWidth) / 2;
        int y = (this.height - this.panelHeight) / 2;

        guiGraphics.blit(PANEL_TEXTURE, x, y, 0, 0, this.panelWidth, this.panelHeight, 256, 256);

    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(this.parentScreen);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
