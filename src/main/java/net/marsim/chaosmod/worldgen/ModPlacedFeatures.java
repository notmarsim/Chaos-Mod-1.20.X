package net.marsim.chaosmod.worldgen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;



import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> UNSTABLE_PARTICLE_ORE_PLACED_KEY = registerKey("unstable_particle_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_UNSTABLE_PARTICLE_ORE_PLACED_KEY = registerKey("nether_unstable_particle_ore_placed");
    public static final ResourceKey<PlacedFeature> END_UNSTABLE_PARTICLE_ORE_PLACED_KEY = registerKey("end_unstable_particle_ore_placed");
    public static final ResourceKey<PlacedFeature> STELLAR_FLOWER_PLACED_KEY = registerKey("stellar_flower_placed");
    public static final ResourceKey<PlacedFeature> STELLAR_FLOWER_BONEMEAL_KEY = registerKey("stellar_flower_bonemeal");


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, UNSTABLE_PARTICLE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_UNSTABLE_PARTICLE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-20))));
        register(context, NETHER_UNSTABLE_PARTICLE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_UNSTABLE_PARTICLE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-20))));
        register(context, END_UNSTABLE_PARTICLE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_UNSTABLE_PARTICLE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(5,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-20))));
        register(context, STELLAR_FLOWER_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.STELLAR_FLOWER_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                ));

        register(context, STELLAR_FLOWER_BONEMEAL_KEY,

                configuredFeatures.getOrThrow(ModConfiguredFeatures.STELLAR_FLOWER_BONEMEAL_CONFIG_KEY),
                List.of(
                        InSquarePlacement.spread(),
                        BiomeFilter.biome()
                ));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ChaosMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
