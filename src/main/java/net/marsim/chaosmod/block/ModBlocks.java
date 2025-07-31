package net.marsim.chaosmod.block;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.custom.StarGeneratorBlock;
import net.marsim.chaosmod.block.custom.VoidRefinerBlock;
import net.marsim.chaosmod.block.custom.ChaoticStationBlock;
import net.marsim.chaosmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChaosMod.MOD_ID);




    public static final RegistryObject<Block> UNSTABLE_BLOCK = registerBlock("unstable_block",
            ()-> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> UNSTABLE_PARTICLE_ORE = registerBlock("unstable_particle_ore",
            ()-> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.GLASS).requiresCorrectToolForDrops(), UniformInt.of(5,10)));

    public static final RegistryObject<Block> STABLE_PARTICLE_ORE = registerBlock("stable_particle_ore",
            ()-> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.GLASS).requiresCorrectToolForDrops(), UniformInt.of(5,10)));

    public static final RegistryObject<Block> VOID_REFINER = registerBlock("void_refiner",
            ()-> new VoidRefinerBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> CHAOTIC_STATION = registerBlock("chaotic_station",
            ()-> new ChaoticStationBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> STAR_GENERATOR = registerBlock("star_generator",
            ()-> new StarGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).requiresCorrectToolForDrops().noOcclusion()));


    public static final RegistryObject<Block> CATMINT = registerBlock("catmint",
            () -> new FlowerBlock(() -> MobEffects.LUCK, 5,
                    BlockBehaviour.Properties.copy(Blocks.ALLIUM).noOcclusion().noCollission()));
    public static final RegistryObject<Block> POTTED_CATMINT = registerBlock("potted_catmint",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.CATMINT,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_ALLIUM).noOcclusion()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () ->new BlockItem(block.get(), new Item.Properties()));
    }



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }

}
