package net.marsim.chaosmod.item;

import net.minecraft.core.Direction;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnergyCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> lazyEnergy;

    public EnergyCapabilityProvider(ItemStack stack, int capacity, int maxReceive, int maxExtract) {


        EnergyStorage energyStorage = new EnergyStorage(capacity, maxReceive, maxExtract) {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                int received = super.receiveEnergy(maxReceive, simulate);
                if (!simulate && received > 0) {

                    stack.getOrCreateTag().put("energy", this.serializeNBT());
                }
                return received;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                int extracted = super.extractEnergy(maxExtract, simulate);
                if (!simulate && extracted > 0) {
                    stack.getOrCreateTag().put("energy", this.serializeNBT());
                }
                return extracted;
            }
        };


        if (stack.hasTag() && stack.getTag().contains("energy")) {

            energyStorage.deserializeNBT(stack.getTag().get("energy"));
        }

        this.lazyEnergy = LazyOptional.of(() -> energyStorage);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergy.cast();
        }
        return LazyOptional.empty();
    }
}