package net.marsim.chaosmod.entity;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.*;

public class ChaosRenderers {

    public static class Zombie extends ZombieRenderer {
        public Zombie(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(net.minecraft.world.entity.monster.Zombie entity) {
            return new ResourceLocation(ChaosMod.MOD_ID, "textures/entity/chaos_zombie.png");
        }
    }

    public static class Skeleton extends SkeletonRenderer {
        public Skeleton(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(AbstractSkeleton entity) {
            return new ResourceLocation(ChaosMod.MOD_ID, "textures/entity/chaos_skeleton.png");
        }
    }

    public static class Creeper extends CreeperRenderer {
        public Creeper(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(net.minecraft.world.entity.monster.Creeper entity) {
            return new ResourceLocation(ChaosMod.MOD_ID, "textures/entity/chaos_creeper.png");
        }
    }

    public static class Spider extends SpiderRenderer<net.minecraft.world.entity.monster.Spider> {
        public Spider(EntityRendererProvider.Context ctx) { super(ctx); }
        @Override
        public ResourceLocation getTextureLocation(net.minecraft.world.entity.monster.Spider entity) {
            return new ResourceLocation(ChaosMod.MOD_ID, "textures/entity/chaos_spider.png");
        }
    }
}
