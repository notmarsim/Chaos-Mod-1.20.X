package net.marsim.chaosmod.datagen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static net.marsim.chaosmod.block.custom.VoidCableBlock.*;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ChaosMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        generateCable(ModBlocks.VOID_CABLE, "void_cable");

        blockWithItem(ModBlocks.UNSTABLE_BLOCK);
        blockWithItem(ModBlocks.UNSTABLE_PARTICLE_ORE);
        blockWithItem(ModBlocks.STABLE_PARTICLE_ORE);
        simpleBlockWithItem(ModBlocks.VOID_REFINER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/void_refiner")));
        simpleBlockWithItem(ModBlocks.DARKLIGHT_REFINER.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/darklight_refiner")));
        simpleBlockWithItem(ModBlocks.VOID_STATION.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/void_station")));
        simpleBlockWithItem(ModBlocks.STELLAR_GENERATOR.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/void_station")));

        simpleBlockWithItem(ModBlocks.STELLAR_FLOWER.get(), models().cross(blockTexture(ModBlocks.STELLAR_FLOWER.get()).getPath(),
                blockTexture(ModBlocks.STELLAR_FLOWER.get())).renderType("cutout"));
        simpleBlockWithItem(ModBlocks.POTTED_STELLAR_FLOWER.get(), models().singleTexture("potted_stellar_flower", new ResourceLocation("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.STELLAR_FLOWER.get())).renderType("cutout"));
    }


    private void generateCable(RegistryObject<Block> block, String textureName) {

        ModelFile core = models().getBuilder("block/" + textureName + "_core")
                .parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", modLoc("block/" + textureName))
                .texture("texture", modLoc("block/" + textureName))
                .element().from(6, 6, 6).to(10, 10, 10)
                .allFaces((dir, face) -> face.uvs(6, 6, 10, 10).texture("#texture")).end();


        ModelFile arm = models().getBuilder("block/" + textureName + "_arm")
                .parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", modLoc("block/" + textureName))
                .texture("texture", modLoc("block/" + textureName))
                .element().from(6, 6, 0).to(10, 10, 6)
                .allFaces((dir, face) -> face.texture("#texture")).end();


        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get())
                .part().modelFile(core).addModel().end();


        builder.part().modelFile(arm).uvLock(true).addModel().condition(NORTH, true).end();
        builder.part().modelFile(arm).rotationY(180).uvLock(true).addModel().condition(SOUTH, true).end();
        builder.part().modelFile(arm).rotationY(90).uvLock(true).addModel().condition(EAST, true).end();
        builder.part().modelFile(arm).rotationY(270).uvLock(true).addModel().condition(WEST, true).end();
        builder.part().modelFile(arm).rotationX(90).uvLock(true).addModel().condition(DOWN, true).end();
        builder.part().modelFile(arm).rotationX(270).uvLock(true).addModel().condition(UP, true).end();


        itemModels().getBuilder(block.getId().getPath())
                .parent(core)
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
                .rotation(0, 90, -35)
                .translation(0, 1.25F, -3.5F)
                .scale(1.5F, 1.5F, 1.5F)
                .end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
                .rotation(0, 45, 0)
                .translation(0, 0, 0)
                .scale(1.5F, 1.5F, 1.5F)
                .end()
                .transform(ItemDisplayContext.GUI)
                .rotation(30, 225, 0)
                .translation(0, 0, 0)
                .scale(1.5F, 1.5F, 1.5F)
                .end()
                .transform(ItemDisplayContext.GROUND)
                .translation(0, 2, 0)
                .scale(1.0F, 1.0F, 1.0F)
                .end()
                .end();


    }


    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
