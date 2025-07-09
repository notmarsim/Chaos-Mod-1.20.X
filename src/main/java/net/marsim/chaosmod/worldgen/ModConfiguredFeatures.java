package net.marsim.chaosmod.worldgen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_UNSTABLE_PARTICLE_ORE_KEY = registerKey("unstable_particle_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_UNSTABLE_PARTICLE_ORE_KEY = registerKey("nether_unstable_particle_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_UNSTABLE_PARTICLE_ORE_KEY = registerKey("end_unstable_particle_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>>context){
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);


        List<OreConfiguration.TargetBlockState> overworldUnstableParticleOres = List.of(OreConfiguration.target(stoneReplaceable,
                ModBlocks.UNSTABLE_PARTICLE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.UNSTABLE_PARTICLE_ORE.get().defaultBlockState()));

        register(context,OVERWORLD_UNSTABLE_PARTICLE_ORE_KEY,Feature.ORE,new OreConfiguration(overworldUnstableParticleOres,6));
        register(context, NETHER_UNSTABLE_PARTICLE_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplacables,
                ModBlocks.UNSTABLE_PARTICLE_ORE.get().defaultBlockState(),6));
        register(context, END_UNSTABLE_PARTICLE_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
                ModBlocks.UNSTABLE_PARTICLE_ORE.get().defaultBlockState(),8));

    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ChaosMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }


}
