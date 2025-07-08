package net.marsim.chaosmod.datagen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
//    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPPHIRE.get(),
//            ModBlocks.SAPPHIRE_ORE.get(), ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModBlocks.NETHER_SAPPHIRE_ORE.get(),
//            ModBlocks.END_STONE_SAPPHIRE_ORE.get()); example

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
//        oreSmelting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
//        oreBlasting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire"); example


        //shaped

        //bar
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.UNSTABLE_BAR.get())
                .pattern("UUU")
                .pattern("UUU")
                .define('U', ModItems.UNSTABLE_PARTICLE.get())
                .unlockedBy(getHasName(ModItems.UNSTABLE_PARTICLE.get()), has(ModItems.UNSTABLE_PARTICLE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STABLE_BAR.get())
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.STABLE_PARTICLE.get())
                .unlockedBy(getHasName(ModItems.STABLE_PARTICLE.get()), has(ModItems.STABLE_PARTICLE.get()))
                .save(pWriter);

        // duality bar
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DUALITY_BAR.get())
                .pattern("UUU")
                .pattern("SSS")
                .define('U', ModItems.UNSTABLE_BAR.get())
                .define('S', ModItems.STABLE_BAR.get())
                .unlockedBy(getHasName(ModItems.UNSTABLE_BAR.get()), has(ModItems.UNSTABLE_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DUALITY_BAR.get())
                .pattern("SSS")
                .pattern("UUU")
                .define('U', ModItems.UNSTABLE_BAR.get())
                .define('S', ModItems.STABLE_BAR.get())
                .unlockedBy(getHasName(ModItems.STABLE_BAR.get()), has(ModItems.STABLE_BAR.get()))
                .save(pWriter, new ResourceLocation(ChaosMod.MOD_ID, "duality_bar_alt"));

        // armors
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUALITY_HELMET.get())
                .pattern("DDD")
                .pattern("D D")
                .define('D', ModItems.DUALITY_BAR.get())
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUALITY_CHESTPLATE.get())
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .define('D', ModItems.DUALITY_BAR.get())
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUALITY_LEGGINGS.get())
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.DUALITY_BAR.get())
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUALITY_BOOTS.get())
                .pattern("D D")
                .pattern("D D")
                .define('D', ModItems.DUALITY_BAR.get())
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);

        // Duality Tools
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DUALITY_SWORD.get())
                .pattern(" D ")
                .pattern(" D ")
                .pattern(" S ")
                .define('D', ModItems.DUALITY_BAR.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DUALITY_PICKAXE.get())
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', ModItems.DUALITY_BAR.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DUALITY_AXE.get())
                .pattern("DD ")
                .pattern("DS ")
                .pattern(" S ")
                .define('D', ModItems.DUALITY_BAR.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.DUALITY_BAR.get()), has(ModItems.DUALITY_BAR.get()))
                .save(pWriter);

        // block

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.UNSTABLE_BLOCK.get())
                .pattern("UUU")
                .pattern("UUU")
                .pattern("UUU")
                .define('U', ModItems.UNSTABLE_BAR.get())
                .unlockedBy(getHasName(ModItems.UNSTABLE_BAR.get()), has(ModItems.UNSTABLE_BAR.get()))
                .save(pWriter);

        // fuel

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHADOW_INFUSED_COAL.get())
                .pattern("VVV")
                .pattern("VCV")
                .pattern("VVV")
                .define('V', ModItems.VOID_DUST.get())
                .define('C', Items.COAL) //
                .unlockedBy(getHasName(ModItems.STABLE_PARTICLE.get()), has(ModItems.STABLE_PARTICLE.get()))
                .save(pWriter);





        // shapeless

        // particle
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.UNSTABLE_PARTICLE.get(), 6)
                .requires(ModItems.UNSTABLE_BAR.get())
                .unlockedBy(getHasName(ModItems.UNSTABLE_BAR.get()), has(ModItems.UNSTABLE_BAR.get()))
                .save(pWriter);

        // bar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.UNSTABLE_BAR.get(), 9)
                .requires(ModBlocks.UNSTABLE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.UNSTABLE_BLOCK.get()), has(ModBlocks.UNSTABLE_BLOCK.get()))
                .save(pWriter, new ResourceLocation(ChaosMod.MOD_ID, "unstable_bar_from_block"));

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  ChaosMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
