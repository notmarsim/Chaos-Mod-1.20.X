package net.marsim.chaosmod.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.IEnergyStorage;

public class BigEnergyStorage implements IEnergyStorage {
    private long energy;
    private long capacity;
    private int maxReceive;
    private int maxExtract;

    public BigEnergyStorage(long capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }


    public void setEnergy(long energy) { this.energy = Math.min(energy, capacity); }
    public long getLongEnergyStored() { return energy; }
    public long getLongMaxEnergyStored() { return capacity; }

    public boolean consumeInternal(long amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
            return true;
        }
        return false;
    }


    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = (int) Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) energy += received;
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = (int) Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) energy -= extracted;
        return extracted;
    }

    @Override
    public int getEnergyStored() { return (int) Math.min(energy, Integer.MAX_VALUE); }

    @Override
    public int getMaxEnergyStored() { return (int) Math.min(capacity, Integer.MAX_VALUE); }

    @Override public boolean canExtract() { return false; }
    @Override public boolean canReceive() { return true; }


    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("energy", energy);
        return tag;
    }

    public void deserializeNBT(Tag tag) {
        if (tag instanceof CompoundTag compound) {
            this.energy = compound.getLong("energy");
        }
    }
}
