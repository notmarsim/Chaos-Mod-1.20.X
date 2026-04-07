package net.marsim.chaosmod.entity;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ChaosMod.MOD_ID);


    public static final RegistryObject<EntityType<ChaosZombie>> CHAOS_ZOMBIE =
            ENTITY_TYPES.register("chaos_zombie",
                    () -> EntityType.Builder.of(ChaosZombie::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f)
                            .build("chaos_zombie"));

    public static final RegistryObject<EntityType<ChaosSkeleton>> CHAOS_SKELETON =
            ENTITY_TYPES.register("chaos_skeleton",
                    () -> EntityType.Builder.of(ChaosSkeleton::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.99f)
                            .build("chaos_skeleton"));

    public static final RegistryObject<EntityType<ChaosCreeper>> CHAOS_CREEPER =
            ENTITY_TYPES.register("chaos_creeper",
                    () -> EntityType.Builder.of(ChaosCreeper::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.7f)
                            .build("chaos_creeper"));

    public static final RegistryObject<EntityType<ChaosSpider>> CHAOS_SPIDER =
            ENTITY_TYPES.register("chaos_spider",
                    () -> EntityType.Builder.of(ChaosSpider::new, MobCategory.MONSTER)
                            .sized(1.4f, 0.9f)
                            .build("chaos_spider"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
