package net.marsim.chaosmod.compat;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.recipe.VoidRefinerRecipe;
import net.marsim.chaosmod.screen.VoidRefinerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIChaosModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ChaosMod.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new VoidRefinerCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<VoidRefinerRecipe> refinerRecipes = recipeManager.getAllRecipesFor(VoidRefinerRecipe.Type.INSTANCE);
        registration.addRecipes(VoidRefinerCategory.VOID_REFINER_TYPE, refinerRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(VoidRefinerScreen.class,80,30,28,30,
                VoidRefinerCategory.VOID_REFINER_TYPE);
    }
}
