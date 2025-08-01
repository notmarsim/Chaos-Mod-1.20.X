package net.marsim.chaosmod.item;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.Nullable;
import java.util.List;

public class EnergyBlockItem extends BlockItem {
    private final int capacity;
    private final int maxReceive;
    private final int maxExtract;

    public EnergyBlockItem(Block pBlock, Properties pProperties, int capacity, int maxReceive, int maxExtract) {
        super(pBlock, pProperties);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new EnergyCapabilityProvider(stack, this.capacity, this.maxReceive, this.maxExtract);
    }




    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pStack.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.ENERGY).ifPresent(energy -> {
            pTooltipComponents.add(Component.literal(String.format("Energy: %d / %d FE", energy.getEnergyStored(), energy.getMaxEnergyStored())));
        });
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return pStack.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.ENERGY).map(energy ->
                (int) (((double) energy.getEnergyStored() / energy.getMaxEnergyStored()) * 13.0)
        ).orElse(0);
    }

    @Override
    public int getBarColor(ItemStack pStack) {

        return 0xFF0000;
    }
}