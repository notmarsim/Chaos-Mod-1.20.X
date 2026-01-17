package net.marsim.chaosmod.datagen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ChaosMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.UNSTABLE_BLOCK.get(),
                ModBlocks.UNSTABLE_PARTICLE_ORE.get(),
                ModBlocks.STABLE_PARTICLE_ORE.get(),
                ModBlocks.VOID_REFINER.get(),
                ModBlocks.DARKLIGHT_REFINER.get(),
                ModBlocks.VOID_STATION.get(),
                ModBlocks.STELLAR_GENERATOR.get(),
                ModBlocks.VOID_CABLE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL);


        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.UNSTABLE_BLOCK.get(),
                        ModBlocks.UNSTABLE_PARTICLE_ORE.get(),
                        ModBlocks.STABLE_PARTICLE_ORE.get());


        this.tag(BlockTags.NEEDS_STONE_TOOL);


        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL);


        this.tag(ModTags.NEEDS_DUALITY_TOOL);

    }
}
