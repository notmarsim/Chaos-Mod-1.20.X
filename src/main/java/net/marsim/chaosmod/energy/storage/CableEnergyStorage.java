package net.marsim.chaosmod.energy.storage;

import net.minecraftforge.energy.IEnergyStorage;

public class CableEnergyStorage implements IEnergyStorage {

    private int energy;
    private final int capacity;

    public CableEnergyStorage(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int received = Math.min(capacity - energy, maxReceive);
        if (!simulate && received > 0) {
            energy += received;
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted = Math.min(energy, maxExtract);
        if (!simulate && extracted > 0) {
            energy -= extracted;
        }
        return extracted;
    }

    @Override
    public int getEnergyStored() {
        return energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

}