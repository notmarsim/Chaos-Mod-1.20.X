package net.marsim.chaosmod.datagen.loot;

import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.UNSTABLE_BLOCK.get());
        this.dropSelf(ModBlocks.VOID_STATION.get());
        this.dropSelf(ModBlocks.VOID_CABLE.get());

        this.add(ModBlocks.UNSTABLE_PARTICLE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.UNSTABLE_PARTICLE_ORE.get(), ModItems.UNSTABLE_PARTICLE.get()));
        this.add(ModBlocks.STABLE_PARTICLE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.STABLE_PARTICLE_ORE.get(), ModItems.STABLE_PARTICLE.get()));

        this.add(ModBlocks.ANTIMATTER_BLOCK.get(),
                block -> createCopperLikeOreDrops(ModBlocks.ANTIMATTER_BLOCK.get(), ModItems.ANTIMATTER.get()));

        this.add(ModBlocks.ZENITH_CRYSTAL_BLOCK.get(),
                block -> createCopperLikeOreDrops(ModBlocks.ZENITH_CRYSTAL_BLOCK.get(), ModItems.ZENITH_SHARD.get()));

        this.add(ModBlocks.VOID_REFINER.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(ModBlocks.VOID_REFINER.get().asItem())
                                                        .apply(

                                                                CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                                        .copy("energy", "BlockEntityTag.energy")
                                                                        .copy("inventory", "BlockEntityTag.inventory")
                                                                        .copy("void_refiner.progress", "BlockEntityTag.void_refiner.progress")
                                                        )
                                        )
                        )
        );

        this.add(ModBlocks.DARKLIGHT_REFINER.get(),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .add(
                                                LootItem.lootTableItem(ModBlocks.DARKLIGHT_REFINER.get().asItem())
                                                        .apply(

                                                                CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                                        .copy("energy", "BlockEntityTag.energy")
                                                                        .copy("inventory", "BlockEntityTag.inventory")
                                                                        .copy("darklight_refiner.progress", "BlockEntityTag.darklight_refiner.progress")
                                                        )
                                        )
                        )
        );

        this.dropSelf(ModBlocks.STELLAR_FLOWER.get());
        this.add(ModBlocks.POTTED_STELLAR_FLOWER.get(), createPotFlowerItemTable(ModBlocks.STELLAR_FLOWER.get()));


        this.add(ModBlocks.STELLAR_GENERATOR.get(), block -> createBlockEntityTable(ModBlocks.STELLAR_GENERATOR.get(), "energy", "inventory"));
        this.add(ModBlocks.DARKLIGHT_GENERATOR.get(), block -> createBlockEntityTable(ModBlocks.DARKLIGHT_GENERATOR.get(), "energy", "inventory"));

    }


    protected LootTable.Builder createBlockEntityTable(Block pBlock, String... nbtTags) {
        CopyNbtFunction.Builder builder = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY);
        for(String tag : nbtTags) {
            builder.copy(tag, "BlockEntityTag." + tag);
        }
        return LootTable.lootTable().withPool(this.applyExplosionCondition(pBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pBlock).apply(builder))));
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
