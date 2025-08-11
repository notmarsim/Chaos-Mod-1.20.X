package net.marsim.chaosmod.recipe;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChaosMod.MOD_ID);


    public static final RegistryObject<RecipeSerializer<VoidRefinerRecipe>> VOID_REFINER_SERIALIZER =
            SERIALIZERS.register("void_refiner", () -> VoidRefinerRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<VoidStationRecipe>> VOID_STATION_SERIALIZER =
            SERIALIZERS.register("void_station", () -> VoidStationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
