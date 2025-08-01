package net.marsim.chaosmod.datagen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ChaosMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.UNSTABLE_BLOCK);
        blockWithItem(ModBlocks.UNSTABLE_PARTICLE_ORE);
        blockWithItem(ModBlocks.STABLE_PARTICLE_ORE);
        simpleBlockWithItem(ModBlocks.VOID_REFINER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/void_refiner")));
        simpleBlockWithItem(ModBlocks.CHAOTIC_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/chaotic_station")));
        simpleBlockWithItem(ModBlocks.STELLAR_GENERATOR.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/chaotic_station")));

        simpleBlockWithItem(ModBlocks.STELLAR_FLOWER.get(), models().cross(blockTexture(ModBlocks.STELLAR_FLOWER.get()).getPath(),
                blockTexture(ModBlocks.STELLAR_FLOWER.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.POTTED_STELLAR_FLOWER.get(), models().singleTexture("potted_stellar_flower", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.STELLAR_FLOWER.get())).renderType("cutout"));

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
