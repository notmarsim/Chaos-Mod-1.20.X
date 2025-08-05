package net.marsim.chaosmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.entity.ISideConfigurable;
import net.marsim.chaosmod.block.entity.IOSide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents; // IMPORT ADICIONADO
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;

public class SideConfigScreen extends Screen {
    private static final ResourceLocation PANEL_TEXTURE = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/energy_configuration.png");
    private static final ResourceLocation CLOSE_BUTTON_TEXTURE = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/close.png");
    private static final ResourceLocation OVERLAYS_TEXTURE = new ResourceLocation(ChaosMod.MOD_ID, "textures/gui/config_io_overlays.png");

    private final ISideConfigurable blockEntity;
    private final Screen parentScreen;

    private final int panelWidth = 104;
    private final int panelHeight = 104;
    private int x, y;


    private final Map<Direction, int[]> buttonLayout = Map.of(
            Direction.UP,    new int[]{44, 24, 16, 16}, // x, y, width, height
            Direction.NORTH, new int[]{44, 44, 16, 16},
            Direction.WEST,  new int[]{24, 44, 16, 16},
            Direction.EAST,  new int[]{64, 44, 16, 16},
            Direction.DOWN,  new int[]{44, 64, 16, 16},
            Direction.SOUTH, new int[]{24, 64, 16, 16}
    );

    public SideConfigScreen(BlockEntity be, Screen parent) {
        super(Component.literal("Side Configuration"));

        if (!(be instanceof ISideConfigurable)) {
            throw new IllegalArgumentException("BlockEntity must implement ISideConfigurable!");
        }
        this.blockEntity = (ISideConfigurable) be;
        this.parentScreen = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.x = (this.width - this.panelWidth) / 2;
        this.y = (this.height - this.panelHeight) / 2;

        this.addRenderableWidget(new ImageButton(this.x + this.panelWidth - 16, this.y, 16, 16, 0, 0, 0,
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

        renderTooltips(guiGraphics, pMouseX, pMouseY);

        guiGraphics.pose().popPose();
    }

    private void renderPanel(GuiGraphics guiGraphics) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();

        guiGraphics.blit(PANEL_TEXTURE, this.x, this.y, 0, 0, this.panelWidth, this.panelHeight, 256, 256);


        for (Map.Entry<Direction, int[]> entry : buttonLayout.entrySet()) {
            Direction dir = entry.getKey();
            int[] layout = entry.getValue();
            IOSide sideState = blockEntity.getSideConfig(dir);

            int u = (sideState == IOSide.INPUT) ? 16 : 0; // Se for INPUT, usa a parte vermelha da textura. Senão, a cinza.

            guiGraphics.blit(OVERLAYS_TEXTURE, this.x + layout[0], this.y + layout[1], u, 0, layout[2], layout[3], 32, 16);
        }
    }

    private void renderTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        for (Map.Entry<Direction, int[]> entry : buttonLayout.entrySet()) {
            Direction dir = entry.getKey();
            int[] layout = entry.getValue();


            if (isMouseOver(pMouseX, pMouseY, this.x + layout[0], this.y + layout[1], layout[2], layout[3])) {
                IOSide sideState = blockEntity.getSideConfig(dir);
                List<Component> tooltip = List.of(
                        Component.translatable("tooltip.chaosmod.side." + dir.getName()),
                        Component.translatable("tooltip.chaosmod.side_state." + sideState.name().toLowerCase())
                );

                guiGraphics.renderComponentTooltip(this.font, tooltip, pMouseX, pMouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {

        if (super.mouseClicked(pMouseX, pMouseY, pButton)) {
            return true;
        }

        for (Map.Entry<Direction, int[]> entry : buttonLayout.entrySet()) {
            Direction dir = entry.getKey();
            int[] layout = entry.getValue();

            if (isMouseOver(pMouseX, pMouseY, this.x + layout[0], this.y + layout[1], layout[2], layout[3])) {

                Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    private boolean isMouseOver(double pMouseX, double pMouseY, int x, int y, int width, int height) {
        return pMouseX >= x && pMouseY >= y && pMouseX < x + width && pMouseY < y + height;
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
