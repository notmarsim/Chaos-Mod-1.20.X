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
import net.marsim.chaosmod.recipe.DarklightRefinerRecipe;
import net.marsim.chaosmod.recipe.VoidRefinerRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class DarklightRefinerCategory implements IRecipeCategory<DarklightRefinerRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(ChaosMod.MOD_ID, "darklight_refiner");
    public static final ResourceLocation TEXTURE = new ResourceLocation(ChaosMod.MOD_ID,
            "textures/gui/void_refiner_gui.png");

    public static final RecipeType<DarklightRefinerRecipe> DARKLIGHT_REFINER_TYPE =
            new RecipeType<>(UID, DarklightRefinerRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public DarklightRefinerCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE,0,0,176,85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DARKLIGHT_REFINER.get()));
    }

    @Override
    public RecipeType<DarklightRefinerRecipe> getRecipeType() {
        return DARKLIGHT_REFINER_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.chaosmod.darklight_refiner");
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
    public void setRecipe(IRecipeLayoutBuilder builder, DarklightRefinerRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT,80,11).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT,80,59).addItemStack(recipe.getResultItem(null));

    }
}
