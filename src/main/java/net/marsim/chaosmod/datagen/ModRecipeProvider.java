package net.marsim.chaosmod.datagen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.item.ModItems;
import net.marsim.chaosmod.recipe.VoidStationRecipe;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
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

        // other

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VOID_ROD.get())
                .pattern("V")
                .pattern("V")
                .define('V', ModItems.VOID_BAR.get())
                .unlockedBy(getHasName(ModItems.VOID_BAR.get()), has(ModItems.VOID_BAR.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FUSION_CORE.get())
                .pattern(" O ")
                .pattern("ODO")
                .pattern(" O ")
                .define('O', Items.OBSIDIAN)
                .define('D', Items.DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.VOID_FRAGMENT.get())
                .pattern("VVV")
                .pattern("VVV")
                .pattern("VVV")
                .define('V', ModItems.VOID_DUST.get())
                .unlockedBy(getHasName(ModItems.VOID_DUST.get()), has(ModItems.VOID_DUST.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.VOID_BAR.get())
                .pattern("VVV")
                .pattern("VVV")
                .pattern("VVV")
                .define('V', ModItems.VOID_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.VOID_FRAGMENT.get()), has(ModItems.VOID_FRAGMENT.get()))
                .save(pWriter);



        // block

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.UNSTABLE_BLOCK.get())
                .pattern("UUU")
                .pattern("UUU")
                .pattern("UUU")
                .define('U', ModItems.UNSTABLE_BAR.get())
                .unlockedBy(getHasName(ModItems.UNSTABLE_BAR.get()), has(ModItems.UNSTABLE_BAR.get()))
                .save(pWriter);
        // void station
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VOID_STATION.get())
                .pattern("UUU")
                .pattern("OFO")
                .pattern("UUU")
                .define('U', ModItems.VOID_BAR.get())
                .define('O', Items.OBSIDIAN)
                .define('F', ModItems.FUSION_CORE.get())
                .unlockedBy(getHasName(ModItems.VOID_BAR.get()), has(ModItems.VOID_BAR.get()))
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STABLE_PARTICLE.get(), 6)
                .requires(ModItems.STABLE_BAR.get())
                .unlockedBy(getHasName(ModItems.STABLE_BAR.get()), has(ModItems.STABLE_BAR.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.STELLAR_ESSENCE.get(), 1)
                .requires(ModBlocks.STELLAR_FLOWER.get())
                .unlockedBy(getHasName(ModBlocks.STELLAR_FLOWER.get()), has(ModBlocks.STELLAR_FLOWER.get()))
                .save(pWriter);

        // bar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.UNSTABLE_BAR.get(), 9)
                .requires(ModBlocks.UNSTABLE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.UNSTABLE_BLOCK.get()), has(ModBlocks.UNSTABLE_BLOCK.get()))
                .save(pWriter, new ResourceLocation(ChaosMod.MOD_ID, "unstable_bar_from_block"));

        // this makes an infinite loop (void fragment > void dust > void bar > void fragment)
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VOID_FRAGMENT.get(), 9)
//                .requires(ModItems.VOID_BAR.get())
//                .unlockedBy(getHasName(ModItems.VOID_BAR.get()), has(ModItems.VOID_BAR.get()))
//                .save(pWriter, new ResourceLocation(ChaosMod.MOD_ID, "void_fragment_from_void_bar"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VOID_DUST.get(), 9)
                .requires(ModItems.VOID_FRAGMENT.get())
                .unlockedBy(getHasName(ModItems.VOID_FRAGMENT.get()), has(ModItems.VOID_FRAGMENT.get()))
                .save(pWriter, new ResourceLocation(ChaosMod.MOD_ID, "void_dust_from_void_fragment"));

        // void station:
        // duality sword
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
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "duality_sword_void_station");

            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
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
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // unstable sword
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(56, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(60, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(67, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.UNSTABLE_BAR.get());
            inputs.set(49, targetBar);
            inputs.set(40, targetBar);
            inputs.set(31, targetBar);
            inputs.set(22, targetBar);
            inputs.set(13, targetBar);
            inputs.set(4, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "unstable_sword_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.UNSTABLE_SWORD.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // stable sword
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(56, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(60, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(67, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.STABLE_BAR.get());
            inputs.set(49, targetBar);
            inputs.set(40, targetBar);
            inputs.set(31, targetBar);
            inputs.set(22, targetBar);
            inputs.set(13, targetBar);
            inputs.set(4, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stable_sword_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STABLE_SWORD.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // darklight sword
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(56, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(60, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(67, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.DARKLIGHT_BAR.get());
            inputs.set(49, targetBar);
            inputs.set(40, targetBar);
            inputs.set(31, targetBar);
            inputs.set(22, targetBar);
            inputs.set(13, targetBar);
            inputs.set(4, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_sword_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_SWORD.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // nova sword
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(56, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(60, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(67, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.NOVA_BAR.get());
            inputs.set(49, targetBar);
            inputs.set(40, targetBar);
            inputs.set(31, targetBar);
            inputs.set(22, targetBar);
            inputs.set(13, targetBar);
            inputs.set(4, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "nova_sword_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.NOVA_SWORD.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // zenith sword
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(56, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(60, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(67, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.ZENITH_BAR.get());
            inputs.set(49, targetBar);
            inputs.set(40, targetBar);
            inputs.set(31, targetBar);
            inputs.set(22, targetBar);
            inputs.set(13, targetBar);
            inputs.set(4, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "zenith_sword_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.ZENITH_SWORD.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // duality axe
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
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "duality_axe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
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
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // unstable axe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(31, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(40, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.UNSTABLE_BAR.get());
            inputs.set(2, targetBar);
            inputs.set(3, targetBar);
            inputs.set(10, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(21, targetBar);
            inputs.set(22, targetBar);
            inputs.set(23, targetBar);
            inputs.set(29, targetBar);
            inputs.set(30, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "unstable_axe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.UNSTABLE_AXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // stable axe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(31, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(40, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.STABLE_BAR.get());
            inputs.set(2, targetBar);
            inputs.set(3, targetBar);
            inputs.set(10, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(21, targetBar);
            inputs.set(22, targetBar);
            inputs.set(23, targetBar);
            inputs.set(29, targetBar);
            inputs.set(30, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stable_axe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STABLE_AXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // darklight axe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(31, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(40, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.DARKLIGHT_BAR.get());
            inputs.set(2, targetBar);
            inputs.set(3, targetBar);
            inputs.set(10, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(21, targetBar);
            inputs.set(22, targetBar);
            inputs.set(23, targetBar);
            inputs.set(29, targetBar);
            inputs.set(30, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_axe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_AXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // nova axe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(31, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(40, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.NOVA_BAR.get());
            inputs.set(2, targetBar);
            inputs.set(3, targetBar);
            inputs.set(10, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(21, targetBar);
            inputs.set(22, targetBar);
            inputs.set(23, targetBar);
            inputs.set(29, targetBar);
            inputs.set(30, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "nova_axe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.NOVA_AXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // zenith axe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(31, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(40, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.ZENITH_BAR.get());
            inputs.set(2, targetBar);
            inputs.set(3, targetBar);
            inputs.set(10, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(21, targetBar);
            inputs.set(22, targetBar);
            inputs.set(23, targetBar);
            inputs.set(29, targetBar);
            inputs.set(30, targetBar);
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "zenith_axe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.ZENITH_AXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // stellar star
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient netherStar = Ingredient.of(Items.NETHER_STAR);
            inputs.set(40, netherStar );

            Ingredient stellarEssence = Ingredient.of(ModItems.STELLAR_ESSENCE.get());
            inputs.set(13, stellarEssence);
            inputs.set(21, stellarEssence);
            inputs.set(22, stellarEssence);
            inputs.set(23, stellarEssence);
            inputs.set(29, stellarEssence);
            inputs.set(30, stellarEssence);
            inputs.set(31, stellarEssence);
            inputs.set(32, stellarEssence);
            inputs.set(33, stellarEssence);
            inputs.set(37, stellarEssence);
            inputs.set(38, stellarEssence);
            inputs.set(39, stellarEssence);
            inputs.set(41, stellarEssence);
            inputs.set(42, stellarEssence);
            inputs.set(43, stellarEssence);
            inputs.set(47, stellarEssence);
            inputs.set(48, stellarEssence);
            inputs.set(49, stellarEssence);
            inputs.set(50, stellarEssence);
            inputs.set(51, stellarEssence);
            inputs.set(57, stellarEssence);
            inputs.set(58, stellarEssence);
            inputs.set(59, stellarEssence);
            inputs.set(67, stellarEssence);

            ItemStack output = new ItemStack(ModItems.STELLAR_STAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stellar_star_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STELLAR_STAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // blackhole
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient stellarStar = Ingredient.of(ModItems.STELLAR_STAR.get());
            Ingredient antimatter = Ingredient.of(ModItems.ANTIMATTER.get());

            inputs.set(4, antimatter);
            inputs.set(12, antimatter); inputs.set(13, antimatter); inputs.set(14, antimatter);
            inputs.set(20, antimatter); inputs.set(21, antimatter); inputs.set(22, antimatter); inputs.set(23, antimatter); inputs.set(24, antimatter);
            inputs.set(28, antimatter); inputs.set(29, antimatter); inputs.set(30, antimatter); inputs.set(31, antimatter); inputs.set(32, antimatter); inputs.set(33, antimatter); inputs.set(34, antimatter);
            inputs.set(36, antimatter); inputs.set(37, antimatter); inputs.set(38, antimatter); inputs.set(39, antimatter);
            inputs.set(40, stellarStar);
            inputs.set(41, antimatter); inputs.set(42, antimatter); inputs.set(43, antimatter); inputs.set(44, antimatter);
            inputs.set(46, antimatter); inputs.set(47, antimatter); inputs.set(48, antimatter); inputs.set(49, antimatter); inputs.set(50, antimatter); inputs.set(51, antimatter); inputs.set(52, antimatter);
            inputs.set(56, antimatter); inputs.set(57, antimatter); inputs.set(58, antimatter); inputs.set(59, antimatter); inputs.set(60, antimatter);
            inputs.set(66, antimatter); inputs.set(67, antimatter); inputs.set(68, antimatter);
            inputs.set(76, antimatter);
            ItemStack output = new ItemStack(ModItems.BLACKHOLE.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "blackhole_void_station");

            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);

                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.BLACKHOLE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // zenith
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient netherStar = Ingredient.of(Items.NETHER_STAR);
            Ingredient zenithShard = Ingredient.of(ModItems.ZENITH_SHARD.get());

            inputs.set(13, zenithShard);
            inputs.set(21, netherStar); inputs.set(22, zenithShard); inputs.set(23, netherStar);
            inputs.set(29, netherStar); inputs.set(30, netherStar); inputs.set(31, zenithShard); inputs.set(32, netherStar); inputs.set(33, netherStar);
            inputs.set(37, zenithShard); inputs.set(38, zenithShard); inputs.set(39, zenithShard);
            inputs.set(40, zenithShard);
            inputs.set(41, zenithShard); inputs.set(42, zenithShard); inputs.set(43, zenithShard);
            inputs.set(47, netherStar); inputs.set(48, netherStar); inputs.set(49, zenithShard); inputs.set(50, netherStar); inputs.set(51, netherStar);
            inputs.set(57, netherStar); inputs.set(58, zenithShard); inputs.set(59, netherStar);
            inputs.set(67, zenithShard);

            ItemStack output = new ItemStack(ModItems.ZENITH.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "zenith_void_station");

            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);

                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.ZENITH.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // chaotic catalyst
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient darklight = Ingredient.of(ModItems.DARKLIGHT_STAR.get());
            Ingredient stellarStar = Ingredient.of(ModItems.STELLAR_STAR.get());
            Ingredient voidStar = Ingredient.of(ModItems.VOID_STAR  .get());
            Ingredient novaStar = Ingredient.of(ModItems.NOVA_STAR.get());
            Ingredient zenith = Ingredient.of(ModItems.ZENITH.get());

            inputs.set(31, stellarStar);
            inputs.set(39, voidStar);
            inputs.set(40, zenith);
            inputs.set(41, darklight);
            inputs.set(49, novaStar);

            ItemStack output = new ItemStack(ModItems.CHAOTIC_CATALYST.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "chaotic_catalyst_void_station");

            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);

                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.CHAOTIC_CATALYST.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }


        // void star
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient netherStar = Ingredient.of(Items.NETHER_STAR);
            inputs.set(40, netherStar );

            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(13, voidBar);
            inputs.set(21, voidBar);
            inputs.set(22, voidBar);
            inputs.set(23, voidBar);
            inputs.set(29, voidBar);
            inputs.set(30, voidBar);
            inputs.set(31, voidBar);
            inputs.set(32, voidBar);
            inputs.set(33, voidBar);
            inputs.set(37, voidBar);
            inputs.set(38, voidBar);
            inputs.set(39, voidBar);
            inputs.set(41, voidBar);
            inputs.set(42, voidBar);
            inputs.set(43, voidBar);
            inputs.set(47, voidBar);
            inputs.set(48, voidBar);
            inputs.set(49, voidBar);
            inputs.set(50, voidBar);
            inputs.set(51, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(67, voidBar);

            ItemStack output = new ItemStack(ModItems.VOID_STAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "void_star_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.VOID_STAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // darklight star
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient stellarStar = Ingredient.of(ModItems.STELLAR_STAR.get());
            inputs.set(40, stellarStar );
            Ingredient voidDust = Ingredient.of(ModItems.VOID_DUST.get());
            Ingredient darklightFragment = Ingredient.of(ModItems.DARKLIGHT_FRAGMENT.get());
            inputs.set(13, voidDust);
            inputs.set(21, voidDust);
            inputs.set(22, darklightFragment);
            inputs.set(23, voidDust);
            inputs.set(29, voidDust);
            inputs.set(30, darklightFragment);
            inputs.set(31, darklightFragment);
            inputs.set(32, darklightFragment);
            inputs.set(33, voidDust);
            inputs.set(37, voidDust);
            inputs.set(38, darklightFragment);
            inputs.set(39, darklightFragment);
            inputs.set(41, darklightFragment);
            inputs.set(42, darklightFragment);
            inputs.set(43, voidDust);
            inputs.set(47, voidDust);
            inputs.set(48, darklightFragment);
            inputs.set(49, darklightFragment);
            inputs.set(50, darklightFragment);
            inputs.set(51, voidDust);
            inputs.set(57, voidDust);
            inputs.set(58, darklightFragment);
            inputs.set(59, voidDust);
            inputs.set(67, voidDust);

            ItemStack output = new ItemStack(ModItems.DARKLIGHT_STAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_star_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_STAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // nova star
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient darklightStar = Ingredient.of(ModItems.DARKLIGHT_STAR.get());
            inputs.set(40, darklightStar );
            Ingredient voidDust = Ingredient.of(ModItems.VOID_DUST.get());
            Ingredient novaFragment = Ingredient.of(ModItems.NOVA_FRAGMENT.get());
            inputs.set(13, voidDust);
            inputs.set(21, voidDust);
            inputs.set(22, novaFragment);
            inputs.set(23, voidDust);
            inputs.set(29, voidDust);
            inputs.set(30, novaFragment);
            inputs.set(31, novaFragment);
            inputs.set(32, novaFragment);
            inputs.set(33, voidDust);
            inputs.set(37, voidDust);
            inputs.set(38, novaFragment);
            inputs.set(39, novaFragment);
            inputs.set(41, novaFragment);
            inputs.set(42, novaFragment);
            inputs.set(43, voidDust);
            inputs.set(47, voidDust);
            inputs.set(48, novaFragment);
            inputs.set(49, novaFragment);
            inputs.set(50, novaFragment);
            inputs.set(51, voidDust);
            inputs.set(57, voidDust);
            inputs.set(58, novaFragment);
            inputs.set(59, voidDust);
            inputs.set(67, voidDust);

            ItemStack output = new ItemStack(ModItems.NOVA_STAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "nova_star_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.NOVA_STAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // chaotic star
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient novaStar = Ingredient.of(ModItems.NOVA_STAR.get());
            inputs.set(40, novaStar );
            Ingredient voidDust = Ingredient.of(ModItems.VOID_DUST.get());
            Ingredient chaosFragment = Ingredient.of(ModItems.CHAOS_FRAGMENT.get());
            inputs.set(13, voidDust);
            inputs.set(21, voidDust);
            inputs.set(22, chaosFragment);
            inputs.set(23, voidDust);
            inputs.set(29, voidDust);
            inputs.set(30, chaosFragment);
            inputs.set(31, chaosFragment);
            inputs.set(32, chaosFragment);
            inputs.set(33, voidDust);
            inputs.set(37, voidDust);
            inputs.set(38, chaosFragment);
            inputs.set(39, chaosFragment);
            inputs.set(41, chaosFragment);
            inputs.set(42, chaosFragment);
            inputs.set(43, voidDust);
            inputs.set(47, voidDust);
            inputs.set(48, chaosFragment);
            inputs.set(49, chaosFragment);
            inputs.set(50, chaosFragment);
            inputs.set(51, voidDust);
            inputs.set(57, voidDust);
            inputs.set(58, chaosFragment);
            inputs.set(59, voidDust);
            inputs.set(67, voidDust);

            ItemStack output = new ItemStack(ModItems.CHAOTIC_STAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "chaotic_star_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.CHAOTIC_STAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // void capacitor
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient voidDust = Ingredient.of(ModItems.VOID_DUST.get());
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            Ingredient voidRod = Ingredient.of(ModItems.VOID_ROD.get());
            Ingredient stellarBar = Ingredient.of(ModItems.STELLAR_BAR.get());
            inputs.set(30, voidDust);
            inputs.set(31, voidDust);
            inputs.set(32, voidDust);
            inputs.set(39, voidDust);
            inputs.set(40, voidDust);
            inputs.set(41, voidDust);
            inputs.set(48, voidDust);
            inputs.set(49, voidDust);
            inputs.set(50, voidDust);

            inputs.set(21, voidBar);
            inputs.set(22, voidBar);
            inputs.set(23, voidBar);
            inputs.set(33, voidBar);
            inputs.set(42, voidBar);
            inputs.set(51, voidBar);
            inputs.set(29, voidBar);
            inputs.set(38, voidBar);
            inputs.set(47, voidBar);
            inputs.set(57, voidBar);
            inputs.set(58, voidBar);
            inputs.set(59, voidBar);
            inputs.set(66, voidRod);
            inputs.set(68, voidRod);

            inputs.set(75, stellarBar);
            inputs.set(77, stellarBar);
            ItemStack output = new ItemStack(ModItems.VOID_CAPACITOR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "void_capacitor_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.VOID_CAPACITOR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // stellar capacitor
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient stellarEssence = Ingredient.of(ModItems.STELLAR_ESSENCE.get());
            Ingredient voidRod = Ingredient.of(ModItems.VOID_ROD.get());
            Ingredient stellarBar = Ingredient.of(ModItems.STELLAR_BAR.get());
            inputs.set(30, stellarEssence);
            inputs.set(31, stellarEssence);
            inputs.set(32, stellarEssence);
            inputs.set(39, stellarEssence);

            inputs.set(40, stellarEssence);

            inputs.set(41, stellarEssence);
            inputs.set(48, stellarEssence);
            inputs.set(49, stellarEssence);
            inputs.set(50, stellarEssence);

            inputs.set(21, stellarBar);
            inputs.set(22, stellarBar);
            inputs.set(23, stellarBar);
            inputs.set(33, stellarBar);
            inputs.set(42, stellarBar);
            inputs.set(51, stellarBar);
            inputs.set(29, stellarBar);
            inputs.set(38, stellarBar);
            inputs.set(47, stellarBar);
            inputs.set(57, stellarBar);
            inputs.set(58, stellarBar);
            inputs.set(59, stellarBar);
            inputs.set(66, voidRod);
            inputs.set(68, voidRod);

            inputs.set(75, stellarBar);
            inputs.set(77, stellarBar);
            ItemStack output = new ItemStack(ModItems.STELLAR_CAPACITOR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stellar_capacitor_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STELLAR_CAPACITOR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // stellar core
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            Ingredient stellarStar = Ingredient.of(ModItems.STELLAR_STAR.get());
            Ingredient obsidian = Ingredient.of(Items.OBSIDIAN);

            int[] obsidianIndices = {
                    10, 11, 12, 13, 14, 15, 16,
                    19, 20, 21, 23, 24, 25,
                    28, 29, 33, 34,
                    37, 43,
                    46, 47, 51, 52,
                    55, 56, 57, 59, 60, 61,
                    64, 65, 66, 67, 68, 69, 70
            };
            for (int i : obsidianIndices) inputs.set(i, obsidian);


            int[] sStarIndices = {
                    22,
                    30, 32,
                    38, 42,
                    48, 50,
                    58
            };
            for (int i : sStarIndices) inputs.set(i, stellarStar);


            int[] fusionIndices = {
                    31,
                    39, 40, 41,
                    49
            };
            for (int i : fusionIndices) inputs.set(i, fusionCore);
            ItemStack output = new ItemStack(ModItems.STELLAR_CORE.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stellar_core_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STELLAR_CORE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // void circuit
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient voidDust = Ingredient.of(ModItems.VOID_DUST.get());
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(30, voidDust);
            inputs.set(31, voidDust);
            inputs.set(32, voidDust);
            inputs.set(39, voidDust);
            inputs.set(40, voidDust);
            inputs.set(41, voidDust);
            inputs.set(48, voidDust);
            inputs.set(49, voidDust);
            inputs.set(50, voidDust);
            inputs.set(57, voidDust);
            inputs.set(58, voidDust);
            inputs.set(59, voidDust);


            inputs.set(13, voidBar);
            inputs.set(14, voidBar);

            inputs.set(20, voidBar);
            inputs.set(21, voidBar);
            inputs.set(22, voidBar);
            inputs.set(23, voidBar);
            inputs.set(24, voidBar);

            inputs.set(29, voidBar);
            inputs.set(33, voidBar);

            inputs.set(38, voidBar);
            inputs.set(42, voidBar);

            inputs.set(47, voidBar);
            inputs.set(56, voidBar);
            inputs.set(51, voidBar);
            inputs.set(60, voidBar);

            inputs.set(65, voidBar);
            inputs.set(66, voidBar);
            inputs.set(67, voidBar);
            inputs.set(68, voidBar);
            inputs.set(69, voidBar);
            inputs.set(75, voidBar);
            inputs.set(76, voidBar);
            inputs.set(28, voidBar);
            inputs.set(37, voidBar);


            ItemStack output = new ItemStack(ModItems.VOID_CIRCUIT.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "void_circuit_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.VOID_CIRCUIT.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // stellar circuit
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient stellarEssence = Ingredient.of(ModItems.STELLAR_ESSENCE.get());
            Ingredient stellarBar = Ingredient.of(ModItems.STELLAR_BAR.get());
            inputs.set(30, stellarEssence);
            inputs.set(31, stellarEssence);
            inputs.set(32, stellarEssence);
            inputs.set(39, stellarEssence);
            inputs.set(40, stellarEssence);
            inputs.set(41, stellarEssence);
            inputs.set(48, stellarEssence);
            inputs.set(49, stellarEssence);
            inputs.set(50, stellarEssence);
            inputs.set(57, stellarEssence);
            inputs.set(58, stellarEssence);
            inputs.set(59, stellarEssence);


            inputs.set(13, stellarBar);
            inputs.set(14, stellarBar);

            inputs.set(20, stellarBar);
            inputs.set(21, stellarBar);
            inputs.set(22, stellarBar);
            inputs.set(23, stellarBar);
            inputs.set(24, stellarBar);

            inputs.set(29, stellarBar);
            inputs.set(33, stellarBar);

            inputs.set(38, stellarBar);
            inputs.set(42, stellarBar);

            inputs.set(47, stellarBar);
            inputs.set(56, stellarBar);
            inputs.set(51, stellarBar);
            inputs.set(60, stellarBar);

            inputs.set(65, stellarBar);
            inputs.set(66, stellarBar);
            inputs.set(67, stellarBar);
            inputs.set(68, stellarBar);
            inputs.set(69, stellarBar);
            inputs.set(75, stellarBar);
            inputs.set(76, stellarBar);
            inputs.set(28, stellarBar);
            inputs.set(37, stellarBar);


            ItemStack output = new ItemStack(ModItems.STELLAR_CIRCUIT.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stellar_circuit_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STELLAR_CIRCUIT.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }


        // stellar generator
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);


            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            Ingredient voidCircuit = Ingredient.of(ModItems.VOID_CIRCUIT.get());
            Ingredient stellarBar = Ingredient.of(ModItems.STELLAR_BAR.get());
            Ingredient voidCapacitor = Ingredient.of(ModItems.VOID_CAPACITOR.get());

            inputs.set(40, voidCircuit);
            inputs.set(22, voidCircuit);
            inputs.set(31, voidCircuit);
            inputs.set(49, voidCircuit);
            inputs.set(58, voidCircuit);

            inputs.set(19, voidCircuit);
            inputs.set(20, voidCircuit);
            inputs.set(21, voidCircuit);
            inputs.set(23, voidCircuit);
            inputs.set(24, voidCircuit);
            inputs.set(25, voidCircuit);
            inputs.set(55, voidCircuit);
            inputs.set(56, voidCircuit);
            inputs.set(57, voidCircuit);
            inputs.set(59, voidCircuit);
            inputs.set(60, voidCircuit);
            inputs.set(61, voidCircuit);

            inputs.set(28, stellarBar);
            inputs.set(29, stellarBar);
            inputs.set(30, stellarBar);
            inputs.set(37, stellarBar);
            inputs.set(39, stellarBar);
            inputs.set(46, stellarBar);
            inputs.set(47, stellarBar);
            inputs.set(48, stellarBar);

            inputs.set(32, stellarBar);
            inputs.set(33, stellarBar);
            inputs.set(34, stellarBar);
            inputs.set(41, stellarBar);
            inputs.set(43, stellarBar);
            inputs.set(50, stellarBar);
            inputs.set(51, stellarBar);
            inputs.set(52, stellarBar);

            inputs.set(38, voidCapacitor);
            inputs.set(42, voidCapacitor);

            inputs.set(9, voidBar);
            inputs.set(10, voidBar);
            inputs.set(11, voidBar);
            inputs.set(12, voidBar);
            inputs.set(13, voidBar);
            inputs.set(14, voidBar);
            inputs.set(15, voidBar);
            inputs.set(16, voidBar);
            inputs.set(17, voidBar);
            inputs.set(18, voidBar);

            inputs.set(26, voidBar);
            inputs.set(35, voidBar);
            inputs.set(44, voidBar);
            inputs.set(53, voidBar);
            inputs.set(62, voidBar);


            inputs.set(63, voidBar);
            inputs.set(64, voidBar);
            inputs.set(65, voidBar);
            inputs.set(66, voidBar);
            inputs.set(67, voidBar);
            inputs.set(68, voidBar);
            inputs.set(69, voidBar);
            inputs.set(70, voidBar);
            inputs.set(71, voidBar);

            inputs.set(27, voidBar);
            inputs.set(36, voidBar);
            inputs.set(45, voidBar);
            inputs.set(54, voidBar);
            inputs.set(63, voidBar);








            ItemStack output = new ItemStack(ModItems.STELLAR_GENERATOR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stellar_generator_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STELLAR_GENERATOR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // darklight generator
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);


            Ingredient darklightBar = Ingredient.of(ModItems.DARKLIGHT_BAR.get());
            Ingredient stellarCircuit = Ingredient.of(ModItems.STELLAR_CIRCUIT.get());
            Ingredient stellarBar = Ingredient.of(ModItems.STELLAR_BAR.get());
            Ingredient voidCapacitor = Ingredient.of(ModItems.VOID_CAPACITOR.get());

            inputs.set(40, stellarCircuit);
            inputs.set(22, stellarCircuit);
            inputs.set(31, stellarCircuit);
            inputs.set(49, stellarCircuit);
            inputs.set(58, stellarCircuit);

            inputs.set(19, stellarCircuit);
            inputs.set(20, stellarCircuit);
            inputs.set(21, stellarCircuit);
            inputs.set(23, stellarCircuit);
            inputs.set(24, stellarCircuit);
            inputs.set(25, stellarCircuit);
            inputs.set(55, stellarCircuit);
            inputs.set(56, stellarCircuit);
            inputs.set(57, stellarCircuit);
            inputs.set(59, stellarCircuit);
            inputs.set(60, stellarCircuit);
            inputs.set(61, stellarCircuit);

            inputs.set(28, stellarBar);
            inputs.set(29, stellarBar);
            inputs.set(30, stellarBar);
            inputs.set(37, stellarBar);
            inputs.set(39, stellarBar);
            inputs.set(46, stellarBar);
            inputs.set(47, stellarBar);
            inputs.set(48, stellarBar);

            inputs.set(32, stellarBar);
            inputs.set(33, stellarBar);
            inputs.set(34, stellarBar);
            inputs.set(41, stellarBar);
            inputs.set(43, stellarBar);
            inputs.set(50, stellarBar);
            inputs.set(51, stellarBar);
            inputs.set(52, stellarBar);

            inputs.set(38, voidCapacitor);
            inputs.set(42, voidCapacitor);

            inputs.set(9, darklightBar);
            inputs.set(10, darklightBar);
            inputs.set(11, darklightBar);
            inputs.set(12, darklightBar);
            inputs.set(13, darklightBar);
            inputs.set(14, darklightBar);
            inputs.set(15, darklightBar);
            inputs.set(16, darklightBar);
            inputs.set(17, darklightBar);
            inputs.set(18, darklightBar);

            inputs.set(26, darklightBar);
            inputs.set(35, darklightBar);
            inputs.set(44, darklightBar);
            inputs.set(53, darklightBar);
            inputs.set(62, darklightBar);


            inputs.set(63, darklightBar);
            inputs.set(64, darklightBar);
            inputs.set(65, darklightBar);
            inputs.set(66, darklightBar);
            inputs.set(67, darklightBar);
            inputs.set(68, darklightBar);
            inputs.set(69, darklightBar);
            inputs.set(70, darklightBar);
            inputs.set(71, darklightBar);

            inputs.set(27, darklightBar);
            inputs.set(36, darklightBar);
            inputs.set(45, darklightBar);
            inputs.set(54, darklightBar);
            inputs.set(63, darklightBar);

            ItemStack output = new ItemStack(ModItems.DARKLIGHT_GENERATOR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_generator_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_GENERATOR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // void refiner
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);


            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            Ingredient voidCircuit = Ingredient.of(ModItems.VOID_CIRCUIT.get());
            Ingredient obsidian = Ingredient.of(Items.OBSIDIAN);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());

            inputs.set(40, voidCircuit);
            inputs.set(22, voidCircuit);
            inputs.set(31, voidCircuit);
            inputs.set(49, voidCircuit);
            inputs.set(58, voidCircuit);

            inputs.set(19, voidCircuit);
            inputs.set(20, voidCircuit);
            inputs.set(21, voidCircuit);
            inputs.set(23, voidCircuit);
            inputs.set(24, voidCircuit);
            inputs.set(25, voidCircuit);
            inputs.set(55, voidCircuit);
            inputs.set(56, voidCircuit);
            inputs.set(57, voidCircuit);
            inputs.set(59, voidCircuit);
            inputs.set(60, voidCircuit);
            inputs.set(61, voidCircuit);

            inputs.set(28, obsidian);
            inputs.set(29, obsidian);
            inputs.set(30, obsidian);
            inputs.set(37, obsidian);
            inputs.set(39, obsidian);
            inputs.set(46, obsidian);
            inputs.set(47, obsidian);
            inputs.set(48, obsidian);

            inputs.set(32, obsidian);
            inputs.set(33, obsidian);
            inputs.set(34, obsidian);
            inputs.set(41, obsidian);
            inputs.set(43, obsidian);
            inputs.set(50, obsidian);
            inputs.set(51, obsidian);
            inputs.set(52, obsidian);

            inputs.set(38, fusionCore);
            inputs.set(42, fusionCore);

            inputs.set(9, voidBar);
            inputs.set(10, voidBar);
            inputs.set(11, voidBar);
            inputs.set(12, voidBar);
            inputs.set(13, voidBar);
            inputs.set(14, voidBar);
            inputs.set(15, voidBar);
            inputs.set(16, voidBar);
            inputs.set(17, voidBar);
            inputs.set(18, voidBar);

            inputs.set(26, voidBar);
            inputs.set(35, voidBar);
            inputs.set(44, voidBar);
            inputs.set(53, voidBar);
            inputs.set(62, voidBar);


            inputs.set(63, voidBar);
            inputs.set(64, voidBar);
            inputs.set(65, voidBar);
            inputs.set(66, voidBar);
            inputs.set(67, voidBar);
            inputs.set(68, voidBar);
            inputs.set(69, voidBar);
            inputs.set(70, voidBar);
            inputs.set(71, voidBar);

            inputs.set(27, voidBar);
            inputs.set(36, voidBar);
            inputs.set(45, voidBar);
            inputs.set(54, voidBar);
            inputs.set(63, voidBar);


            ItemStack output = new ItemStack(ModItems.VOID_REFINER.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "void_refiner_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.VOID_REFINER.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // darklight refiner
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);


            Ingredient darklightBar = Ingredient.of(ModItems.DARKLIGHT_BAR.get());
            Ingredient stellarCircuit = Ingredient.of(ModItems.STELLAR_CIRCUIT.get());
            Ingredient obsidian = Ingredient.of(Items.OBSIDIAN);
            Ingredient stellarCore = Ingredient.of(ModItems.STELLAR_CORE.get());

            inputs.set(40, stellarCircuit);
            inputs.set(22, stellarCircuit);
            inputs.set(31, stellarCircuit);
            inputs.set(49, stellarCircuit);
            inputs.set(58, stellarCircuit);

            inputs.set(19, stellarCircuit);
            inputs.set(20, stellarCircuit);
            inputs.set(21, stellarCircuit);
            inputs.set(23, stellarCircuit);
            inputs.set(24, stellarCircuit);
            inputs.set(25, stellarCircuit);
            inputs.set(55, stellarCircuit);
            inputs.set(56, stellarCircuit);
            inputs.set(57, stellarCircuit);
            inputs.set(59, stellarCircuit);
            inputs.set(60, stellarCircuit);
            inputs.set(61, stellarCircuit);

            inputs.set(28, obsidian);
            inputs.set(29, obsidian);
            inputs.set(30, obsidian);
            inputs.set(37, obsidian);
            inputs.set(39, obsidian);
            inputs.set(46, obsidian);
            inputs.set(47, obsidian);
            inputs.set(48, obsidian);

            inputs.set(32, obsidian);
            inputs.set(33, obsidian);
            inputs.set(34, obsidian);
            inputs.set(41, obsidian);
            inputs.set(43, obsidian);
            inputs.set(50, obsidian);
            inputs.set(51, obsidian);
            inputs.set(52, obsidian);

            inputs.set(38, stellarCore);
            inputs.set(42, stellarCore);

            inputs.set(9, darklightBar);
            inputs.set(10, darklightBar);
            inputs.set(11, darklightBar);
            inputs.set(12, darklightBar);
            inputs.set(13, darklightBar);
            inputs.set(14, darklightBar);
            inputs.set(15, darklightBar);
            inputs.set(16, darklightBar);
            inputs.set(17, darklightBar);
            inputs.set(18, darklightBar);

            inputs.set(26, darklightBar);
            inputs.set(35, darklightBar);
            inputs.set(44, darklightBar);
            inputs.set(53, darklightBar);
            inputs.set(62, darklightBar);


            inputs.set(63, darklightBar);
            inputs.set(64, darklightBar);
            inputs.set(65, darklightBar);
            inputs.set(66, darklightBar);
            inputs.set(67, darklightBar);
            inputs.set(68, darklightBar);
            inputs.set(69, darklightBar);
            inputs.set(70, darklightBar);
            inputs.set(71, darklightBar);

            inputs.set(27, darklightBar);
            inputs.set(36, darklightBar);
            inputs.set(45, darklightBar);
            inputs.set(54, darklightBar);
            inputs.set(63, darklightBar);


            ItemStack output = new ItemStack(ModItems.DARKLIGHT_REFINER.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_refiner_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_REFINER.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // stellar bar
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);


            Ingredient stellarEssence = Ingredient.of(ModItems.STELLAR_ESSENCE.get());
            Ingredient stellarStar = Ingredient.of(ModItems.STELLAR_STAR.get());
            Ingredient netherStar = Ingredient.of(Items.NETHER_STAR);


            int[] essenceIndices = {
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 35,
                    36, 44,
                    45, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, 62
            };
            for (int i : essenceIndices) inputs.set(i, stellarEssence);


            int[] sStarIndices = {
                    28, 30, 32, 34,
                    38, 40, 42,
                    46, 48, 50, 52
            };
            for (int i : sStarIndices) inputs.set(i, stellarStar);


            int[] nStarIndices = {
                    29, 31, 33,
                    37, 39, 41, 43,
                    47, 49, 51
            };
            for (int i : nStarIndices) inputs.set(i, netherStar);

            ItemStack output = new ItemStack(ModItems.STELLAR_BAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stellar_bar_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STELLAR_BAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // darklight bar
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);


            Ingredient stellarBar = Ingredient.of(ModItems.STELLAR_BAR.get());
            Ingredient voidStar = Ingredient.of(ModItems.VOID_STAR.get());
            Ingredient darklightStar = Ingredient.of(ModItems.DARKLIGHT_STAR.get());


            int[] stellarBarIndices = {
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 35,
                    36, 44,
                    45, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, 62
            };
            for (int i : stellarBarIndices) inputs.set(i, stellarBar);


            int[] voidStarIndices = {
                    28, 30, 32, 34,
                    38, 40, 42,
                    46, 48, 50, 52
            };
            for (int i : voidStarIndices) inputs.set(i, voidStar);


            int[] darklightStarIndices = {
                    29, 31, 33,
                    37, 39, 41, 43,
                    47, 49, 51
            };
            for (int i : darklightStarIndices) inputs.set(i, darklightStar);

            ItemStack output = new ItemStack(ModItems.DARKLIGHT_BAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_bar_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_BAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // nova bar
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient darklightBar = Ingredient.of(ModItems.DARKLIGHT_BAR.get());
            Ingredient stellarStar = Ingredient.of(ModItems.STELLAR_STAR.get());
            Ingredient novaStar = Ingredient.of(ModItems.NOVA_STAR.get());

            int[] darklightBarIndices = {
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 35,
                    36, 44,
                    45, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, 62
            };
            for (int i : darklightBarIndices) inputs.set(i, darklightBar);

            int[] stellarStarIndices = {
                    28, 30, 32, 34,
                    38, 40, 42,
                    46, 48, 50, 52
            };
            for (int i : stellarStarIndices) inputs.set(i, stellarStar);

            int[] novaStarIndices = {
                    29, 31, 33,
                    37, 39, 41, 43,
                    47, 49, 51
            };
            for (int i : novaStarIndices) inputs.set(i, novaStar);

            ItemStack output = new ItemStack(ModItems.NOVA_BAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "nova_bar_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.NOVA_BAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // zenith bar
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient novaBar = Ingredient.of(ModItems.NOVA_BAR.get());
            Ingredient zenith = Ingredient.of(ModItems.ZENITH.get());
            Ingredient netherStar = Ingredient.of(Items.NETHER_STAR);

            int[] novaBarIndices = {
                    18, 19, 20, 21, 22, 23, 24, 25, 26,
                    27, 35,
                    36, 44,
                    45, 53,
                    54, 55, 56, 57, 58, 59, 60, 61, 62
            };
            for (int i : novaBarIndices) inputs.set(i, novaBar);

            int[] zenithIndices = {
                    28, 30, 32, 34,
                    38, 40, 42,
                    46, 48, 50, 52
            };
            for (int i : zenithIndices) inputs.set(i, zenith);

            int[] netherStarIndices = {
                    29, 31, 33,
                    37, 39, 41, 43,
                    47, 49, 51
            };
            for (int i : netherStarIndices) inputs.set(i, netherStar);

            ItemStack output = new ItemStack(ModItems.ZENITH_BAR.get());
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "zenith_bar_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.ZENITH_BAR.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // duality pickaxe
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
            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "duality_pickaxe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
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
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // unstable pickaxe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);

            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(40, voidBar);
            inputs.set(22, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(31, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.UNSTABLE_BAR.get());
            inputs.set(3, targetBar);
            inputs.set(5, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(15, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(24, targetBar);
            inputs.set(25, targetBar);
            inputs.set(28, targetBar);
            inputs.set(34, targetBar);

            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "unstable_pickaxe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.UNSTABLE_PICKAXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // stable pickaxe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(40, voidBar);
            inputs.set(22, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(31, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.STABLE_BAR.get());
            inputs.set(3, targetBar);
            inputs.set(5, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(15, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(24, targetBar);
            inputs.set(25, targetBar);
            inputs.set(28, targetBar);
            inputs.set(34, targetBar);

            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "stable_pickaxe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.STABLE_PICKAXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // darklight pickaxe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(40, voidBar);
            inputs.set(22, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(31, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.DARKLIGHT_BAR.get());
            inputs.set(3, targetBar);
            inputs.set(5, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(15, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(24, targetBar);
            inputs.set(25, targetBar);
            inputs.set(28, targetBar);
            inputs.set(34, targetBar);

            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "darklight_pickaxe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.DARKLIGHT_PICKAXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }

        // nova pickaxe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(40, voidBar);
            inputs.set(22, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(31, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.NOVA_BAR.get());
            inputs.set(3, targetBar);
            inputs.set(5, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(15, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(24, targetBar);
            inputs.set(25, targetBar);
            inputs.set(28, targetBar);
            inputs.set(34, targetBar);

            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "nova_pickaxe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.NOVA_PICKAXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
                @Override
                public JsonObject serializeAdvancement() { return null; }
                @Override
                public ResourceLocation getAdvancementId() { return null; }
            });
        }
        // zenith pickaxe
        {
            NonNullList<Ingredient> inputs = NonNullList.withSize(81, Ingredient.EMPTY);
            Ingredient voidBar = Ingredient.of(ModItems.VOID_BAR.get());
            inputs.set(76, voidBar);
            inputs.set(67, voidBar);
            inputs.set(49, voidBar);
            inputs.set(40, voidBar);
            inputs.set(22, voidBar);
            inputs.set(4, voidBar);
            Ingredient fusionCore = Ingredient.of(ModItems.FUSION_CORE.get());
            inputs.set(58, fusionCore);
            inputs.set(31, fusionCore);
            Ingredient targetBar = Ingredient.of(ModItems.ZENITH_BAR.get());
            inputs.set(3, targetBar);
            inputs.set(5, targetBar);
            inputs.set(11, targetBar);
            inputs.set(12, targetBar);
            inputs.set(13, targetBar);
            inputs.set(14, targetBar);
            inputs.set(15, targetBar);
            inputs.set(19, targetBar);
            inputs.set(20, targetBar);
            inputs.set(24, targetBar);
            inputs.set(25, targetBar);
            inputs.set(28, targetBar);
            inputs.set(34, targetBar);

            ResourceLocation id = new ResourceLocation(ChaosMod.MOD_ID, "zenith_pickaxe_void_station");
            pWriter.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject json) {
                    json.addProperty("type", "chaosmod:void_station");
                    JsonArray ingredients = new JsonArray();
                    for (Ingredient ingredient : inputs) {
                        ingredients.add(ingredient.toJson());
                    }
                    json.add("ingredients", ingredients);
                    JsonObject outputObj = new JsonObject();
                    outputObj.addProperty("item", ForgeRegistries.ITEMS.getKey(ModItems.ZENITH_PICKAXE.get()).toString());
                    outputObj.addProperty("count", 1);
                    json.add("output", outputObj);
                }
                @Override
                public ResourceLocation getId() { return id; }
                @Override
                public RecipeSerializer<?> getType() { return VoidStationRecipe.Serializer.INSTANCE; }
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