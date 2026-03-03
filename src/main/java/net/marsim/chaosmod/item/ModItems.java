package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.item.custom.ConfiguratorItem;
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
    public static final RegistryObject<Item> CHAOS_FRAGMENT = ITEMS.register("chaos_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DARKLIGHT_FRAGMENT= ITEMS.register("darklight_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_FRAGMENT= ITEMS.register("nova_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_FRAGMENT = ITEMS.register("void_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_ESSENCE = ITEMS.register("stellar_essence",
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
    public static final RegistryObject<Item> STELLAR_BAR = ITEMS.register("stellar_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DARKLIGHT_BAR = ITEMS.register("darklight_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_BAR = ITEMS.register("nova_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHAOS_BAR = ITEMS.register("chaos_bar",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STELLAR_GENERATOR = ITEMS.register("stellar_generator",
            () -> new EnergyBlockItem(ModBlocks.STELLAR_GENERATOR.get(), new Item.Properties(),
                    500_000_000, // capacity
                    500_000,   // maxReceive
                    100_000)); // maxExtract
    public static final RegistryObject<Item> DARKLIGHT_GENERATOR = ITEMS.register("darklight_generator",
            () -> new EnergyBlockItem(ModBlocks.DARKLIGHT_GENERATOR.get(), new Item.Properties(),
                    500_000_000, // capacity
                    500_000,   // maxReceive
                    100_000)); // maxExtract
    public static final RegistryObject<Item> VOID_REFINER = ITEMS.register("void_refiner",
            () -> new EnergyBlockItem(ModBlocks.VOID_REFINER.get(), new Item.Properties(),
                    1_000_000, // capacity
                    500_000,   // maxReceive
                    3_000)); // maxExtract
    public static final RegistryObject<Item> DARKLIGHT_REFINER = ITEMS.register("darklight_refiner",
            () -> new EnergyBlockItem(ModBlocks.DARKLIGHT_REFINER.get(), new Item.Properties(),
                    1_000_000, // capacity
                    500_000,   // maxReceive
                    3_000)); // maxExtract

    // fuel
    public static final RegistryObject<Item> SHADOW_INFUSED_COAL = ITEMS.register("shadow_infused_coal",
            () -> new FuelItem(new Item.Properties(),3000));

    // dusts
    public static final RegistryObject<Item> VOID_DUST = ITEMS.register("void_dust",
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


    // others
    public static final RegistryObject<Item> CONFIGURATOR = ITEMS.register("configurator",
            () -> new ConfiguratorItem(new Item.Properties()));
    public static final RegistryObject<Item> CHAOTIC_STAR = ITEMS.register("chaotic_star",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_STAR = ITEMS.register("void_star",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZENITH = ITEMS.register("zenith",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZENITH_SHARD = ITEMS.register("zenith_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_CAPACITOR = ITEMS.register("void_capacitor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VOID_CIRCUIT = ITEMS.register("void_circuit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_CAPACITOR = ITEMS.register("stellar_capacitor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_CIRCUIT = ITEMS.register("stellar_circuit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_CORE = ITEMS.register("stellar_core",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ANTIMATTER = ITEMS.register("antimatter",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACKHOLE = ITEMS.register("blackhole",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> DARKLIGHT_STAR = ITEMS.register("darklight_star",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NOVA_STAR = ITEMS.register("nova_star",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_STAR = ITEMS.register("stellar_star",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VOID_ROD = ITEMS.register("void_rod",
            () -> new Item(new Item.Properties()));
}