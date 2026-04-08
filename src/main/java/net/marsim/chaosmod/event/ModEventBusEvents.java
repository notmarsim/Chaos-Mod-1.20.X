package net.marsim.chaosmod.event;


import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.entity.ModEntities;
import net.marsim.chaosmod.entity.*;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ChaosMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CHAOS_ZOMBIE.get(), ChaosZombie.createAttributes().build());
        event.put(ModEntities.CHAOS_SKELETON.get(), ChaosSkeleton.createAttributes().build());
        event.put(ModEntities.CHAOS_CREEPER.get(), ChaosCreeper.createAttributes().build());
        event.put(ModEntities.CHAOS_SPIDER.get(), ChaosSpider.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CHAOS_ZOMBIE.get(), ChaosRenderers.Zombie::new);
        event.registerEntityRenderer(ModEntities.CHAOS_SKELETON.get(), ChaosRenderers.Skeleton::new);
        event.registerEntityRenderer(ModEntities.CHAOS_CREEPER.get(), ChaosRenderers.Creeper::new);
        event.registerEntityRenderer(ModEntities.CHAOS_SPIDER.get(), ChaosRenderers.Spider::new);
    }
}
