package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ChaosMod.MOD_ID);


    public static void register (IEventBus eventBus){
        ITEMS.register(eventBus);
    }


    // particles
    public static final RegistryObject<Item> STABLE_PARTICLE = ITEMS.register("stable_particle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> UNSTABLE_PARTICLE = ITEMS.register("unstable_particle",
            () -> new Item(new Item.Properties()));


    // bars
    public static final RegistryObject<Item> UNSTABLE_BAR = ITEMS.register("unstable_bar",
            () -> new Item(new Item.Properties()));
}
