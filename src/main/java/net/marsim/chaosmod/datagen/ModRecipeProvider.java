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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraftforge.registries.ForgeRegistries;

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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STAR_GENERATOR.get())
                .pattern("UUU")
                .pattern("UUU")
                .pattern("UUU")
                .define('U', Items.OBSIDIAN)
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

        // Receita customizada da Duality Sword na Chaotic Station
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            // void bar
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(56, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(60, voidBar);
            // fusion core
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(67, fusionCore);
            // duality bar
            Ingredient dualityBar = Ingredient.of(ModItems.DUALITY_BAR.get());
            inputs.set(49, dualityBar);
            inputs.set(40, dualityBar);
            inputs.set(31, dualityBar);
            inputs.set(22, dualityBar);
            inputs.set(13, dualityBar);
            inputs.set(4, dualityBar);

            ItemStack output = new ItemStack(ModItems.DUALITY_SWORD.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "duality_sword_chaotic_station");

            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:chaotic_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DUALITY_SWORD.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return net.marsim.chaosmod.recipe.ChaoticStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // Receita customizada da Duality Axe na Chaotic Station
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            // void bar
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(31, voidBar);
            inputs.set(4, voidBar);
            // fusion core
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(40, fusionCore);
            // duality bar
            Ingredient dualityBar = Ingredient.of(ModItems.DUALITY_BAR.get());
            inputs.set(2, dualityBar);
            inputs.set(3, dualityBar);
            inputs.set(10, dualityBar);
            inputs.set(11, dualityBar);
            inputs.set(12, dualityBar);
            inputs.set(13, dualityBar);
            inputs.set(14, dualityBar);
            inputs.set(19, dualityBar);
            inputs.set(20, dualityBar);
            inputs.set(21, dualityBar);
            inputs.set(22, dualityBar);
            inputs.set(23, dualityBar);
            inputs.set(29, dualityBar);
            inputs.set(30, dualityBar);
            ItemStack output = new ItemStack(ModItems.DUALITY_AXE.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "duality_axe_chaotic_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:chaotic_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DUALITY_AXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return net.marsim.chaosmod.recipe.ChaoticStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // Receita customizada da Duality Pickaxe na Chaotic Station
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            // void bar
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(40, voidBar);
            inputs.set(22, voidBar);
            inputs.set(4, voidBar);
            // fusion core
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(31, fusionCore);
            // duality bar
            Ingredient dualityBar = Ingredient.of(ModItems.DUALITY_BAR.get());
            inputs.set(3, dualityBar);
            inputs.set(5, dualityBar);
            inputs.set(11, dualityBar);
            inputs.set(12, dualityBar);
            inputs.set(13, dualityBar);
            inputs.set(14, dualityBar);
            inputs.set(15, dualityBar);
            inputs.set(19, dualityBar);
            inputs.set(20, dualityBar);
            inputs.set(24, dualityBar);
            inputs.set(25, dualityBar);
            inputs.set(28, dualityBar);
            inputs.set(34, dualityBar);
            ItemStack output = new ItemStack(ModItems.DUALITY_PICKAXE.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "duality_pickaxe_chaotic_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:chaotic_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DUALITY_PICKAXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return net.marsim.chaosmod.recipe.ChaoticStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
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
