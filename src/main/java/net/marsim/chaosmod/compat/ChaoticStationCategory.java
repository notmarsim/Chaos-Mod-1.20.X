package net.marsim.chaosmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.recipe.ChaoticStationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ChaoticStationCategory implements IRecipeCategory<ChaoticStationRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(ChaosMod.MOD_ID, "chaotic_station");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ChaosMod.MOD_ID,
            "textures/gui/chaotic_station_gui.png");

    public static final RecipeType<ChaoticStationRecipe> CHAOTIC_STATION_TYPE =
            new RecipeType<>(UID, ChaoticStationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ChaoticStationCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,256,256);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CHAOTIC_STATION.get()));
    }

    @Override
    public RecipeType<ChaoticStationRecipe> getRecipeType() {
        return CHAOTIC_STATION_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.chaosmod.chaotic_station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChaoticStationRecipe recipe, IFocusGroup focuses) {
        
        for (int i = 0; i < 81; i++) {
            int x = 11 + (i % 9) * 18;
            int y = 7 + (i / 9) * 18;
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(recipe.getIngredients().get(i));
        }
        // Output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 210, 80).addItemStack(recipe.getResultItem(null));
    }
} 