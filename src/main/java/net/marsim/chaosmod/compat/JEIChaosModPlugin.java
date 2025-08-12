package net.marsim.chaosmod.compat;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.recipe.VoidRefinerRecipe;
import net.marsim.chaosmod.screen.VoidRefinerMenu;
import net.marsim.chaosmod.screen.VoidRefinerScreen;
import net.marsim.chaosmod.screen.VoidStationMenu;
import net.marsim.chaosmod.screen.VoidStationScreen;
import net.marsim.chaosmod.recipe.VoidStationRecipe;
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
        registration.addRecipeCategories(new VoidStationCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<VoidRefinerRecipe> refinerRecipes = recipeManager.getAllRecipesFor(VoidRefinerRecipe.Type.INSTANCE);
        registration.addRecipes(VoidRefinerCategory.VOID_REFINER_TYPE, refinerRecipes);

        List<VoidStationRecipe> chaoticRecipes = recipeManager.getAllRecipesFor(VoidStationRecipe.Type.INSTANCE);
        registration.addRecipes(VoidStationCategory.VOID_STATION_TYPE, chaoticRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(VoidRefinerScreen.class,80,30,28,30,
                VoidRefinerCategory.VOID_REFINER_TYPE);
        registration.addRecipeClickArea(VoidStationScreen.class,176,80,21,14,
                VoidStationCategory.VOID_STATION_TYPE);
    }
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        // Handler para o Void Refiner
        registration.addRecipeTransferHandler(VoidRefinerMenu.class, null,
                VoidRefinerCategory.VOID_REFINER_TYPE,
                36, // first slot index (input)
                1,  // slots quantity
                0,
                36);

        // Handler para a Void Station
        registration.addRecipeTransferHandler(VoidStationMenu.class, null,
                VoidStationCategory.VOID_STATION_TYPE,
                36, // first slot index (input)
                81,   // slots quantity
                0,
                36);
    }
}