package net.marsim.chaosmod.datagen.loot;

import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.UNSTABLE_BLOCK.get());
        this.dropSelf(ModBlocks.VOID_REFINER.get());
        this.dropSelf(ModBlocks.CHAOTIC_STATION.get());
        this.dropSelf(ModBlocks.STELLAR_GENERATOR.get());
        this.add(ModBlocks.UNSTABLE_PARTICLE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.UNSTABLE_PARTICLE_ORE.get(), ModItems.UNSTABLE_PARTICLE.get()));
        this.add(ModBlocks.STABLE_PARTICLE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.STABLE_PARTICLE_ORE.get(), ModItems.STABLE_PARTICLE.get()));



        this.dropSelf(ModBlocks.STELLAR_FLOWER.get());
        this.add(ModBlocks.POTTED_STELLAR_FLOWER.get(), createPotFlowerItemTable(ModBlocks.STELLAR_FLOWER.get()));

    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
