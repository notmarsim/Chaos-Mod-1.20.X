package net.marsim.chaosmod.energy;

import net.marsim.chaosmod.block.entity.StellarGeneratorEntity;
import net.minecraftforge.energy.EnergyStorage;

public class GeneratorEnergyStorage extends EnergyStorage {

    private final IEnergyExporter entity;


    public GeneratorEnergyStorage(IEnergyExporter entity, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.entity = entity;
    }

    public void generate(int amount) {
        this.energy = Math.min(this.energy + amount, this.capacity);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {

        if (!entity.isExportingNow()) {
            return 0;
        }


        int toExtract = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            this.energy -= toExtract;
        }
        return toExtract;
    }
}