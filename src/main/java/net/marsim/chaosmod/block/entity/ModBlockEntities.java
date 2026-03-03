package net.marsim.chaosmod.block.entity;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChaosMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<VoidRefinerEntity>> VOID_REFINER_BE =
            BLOCK_ENTITIES.register("void_refiner_be",()->
                    BlockEntityType.Builder.of(VoidRefinerEntity::new,
                            ModBlocks.VOID_REFINER.get()).build(null));

    public static final RegistryObject<BlockEntityType<DarklightRefinerEntity>> DARKLIGHT_REFINER_BE =
            BLOCK_ENTITIES.register("darklight_refiner_be",()->
                    BlockEntityType.Builder.of(DarklightRefinerEntity::new,
                            ModBlocks.DARKLIGHT_REFINER.get()).build(null));

    public static final RegistryObject<BlockEntityType<VoidStationEntity>> VOID_STATION_BE =
            BLOCK_ENTITIES.register("void_station_be",()->
                    BlockEntityType.Builder.of(VoidStationEntity::new,
                            ModBlocks.VOID_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<StellarGeneratorEntity>> STELLAR_GENERATOR_BE =
            BLOCK_ENTITIES.register("stellar_generator_be",()->
                    BlockEntityType.Builder.of(StellarGeneratorEntity::new,
                            ModBlocks.STELLAR_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<DarklightGeneratorEntity>> DARKLIGHT_GENERATOR_BE =
            BLOCK_ENTITIES.register("darklight_generator_be",()->
                    BlockEntityType.Builder.of(DarklightGeneratorEntity::new,
                            ModBlocks.DARKLIGHT_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<CableBlockEntity>> CABLE_BE =
            BLOCK_ENTITIES.register("cable_be", () ->
                    BlockEntityType.Builder.of(CableBlockEntity::new,
                            ModBlocks.VOID_CABLE.get()).build(null));
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}