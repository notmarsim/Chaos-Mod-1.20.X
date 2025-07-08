package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.item.custom.FuelItem;
import net.minecraft.world.item.*;
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
    public static final RegistryObject<Item> STABLE_BAR = ITEMS.register("stable_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DUALITY_BAR = ITEMS.register("duality_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_BAR = ITEMS.register("void_bar",
            () -> new Item(new Item.Properties()));


    // fuel
    public static final RegistryObject<Item> SHADOW_INFUSED_COAL = ITEMS.register("shadow_infused_coal",
            () -> new FuelItem(new Item.Properties(),3000));

    // dusts
    public static final RegistryObject<Item> VOID_DUST = ITEMS.register("void_dust",
            () -> new Item(new Item.Properties()));

    // ingots
    public static final RegistryObject<Item> VOID_INGOT = ITEMS.register("void_ingot",
            () -> new Item(new Item.Properties()));

    // cores
    public static final RegistryObject<Item> FUSION_CORE = ITEMS.register("fusion_core",
            () -> new Item(new Item.Properties()));

    // tools
    public static final RegistryObject<Item> DUALITY_SWORD = ITEMS.register("duality_sword",
            () -> new SwordItem(ModToolTiers.DUALITY, 5, 3F, new Item.Properties()));
    public static final RegistryObject<Item> DUALITY_PICKAXE = ITEMS.register("duality_pickaxe",
            () -> new PickaxeItem(ModToolTiers.DUALITY, 2, 2F, new Item.Properties()));
    public static final RegistryObject<Item> DUALITY_AXE = ITEMS.register("duality_axe",
            () -> new AxeItem(ModToolTiers.DUALITY, 9F, 1F, new Item.Properties()));

    // armor
    public static final RegistryObject<Item> DUALITY_HELMET = ITEMS.register("duality_helmet",
            () -> new ArmorItem(ModArmorMaterials.DUALITY, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DUALITY_CHESTPLATE = ITEMS.register("duality_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DUALITY, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> DUALITY_LEGGINGS = ITEMS.register("duality_leggings",
            () -> new ArmorItem(ModArmorMaterials.DUALITY, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> DUALITY_BOOTS = ITEMS.register("duality_boots",
            () -> new ArmorItem(ModArmorMaterials.DUALITY, ArmorItem.Type.BOOTS, new Item.Properties()));
}
