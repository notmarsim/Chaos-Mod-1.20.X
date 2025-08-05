package net.marsim.chaosmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CableBlockEntity extends BlockEntity {
    private final Map<Direction, ConnectionType> connectionStates = new HashMap<>();

    private static final int CAPACITY = 10000;
    private static final int MAX_TRANSFER = 1000;

    private final EnergyStorage energyStorage = new EnergyStorage(CAPACITY, MAX_TRANSFER, MAX_TRANSFER);
    private final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> energyStorage);

    public CableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CABLE_BE.get(), pPos, pBlockState);
        for (Direction dir : Direction.values()) {
            connectionStates.put(dir, ConnectionType.INPUT_OUTPUT);
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side != null && connectionStates.get(side) != ConnectionType.NONE) {
            return lazyEnergy.cast();
        }
        return super.getCapability(cap, side);
    }

    public void cycleConnection(Direction side) {
        ConnectionType currentState = connectionStates.get(side);
        connectionStates.put(side, currentState.getNext());
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    public ConnectionType getConnectionState(Direction side) {
        return this.connectionStates.get(side);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("energy", energyStorage.getEnergyStored());
        for (Direction dir : Direction.values()) {
            pTag.putInt("conn_" + dir.getName(), connectionStates.get(dir).ordinal());
        }
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        energyStorage.deserializeNBT(pTag.get("energy"));
        for (Direction dir : Direction.values()) {
            connectionStates.put(dir, ConnectionType.values()[pTag.getInt("conn_" + dir.getName())]);
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) return;

        List<IEnergyStorage> outputs = new ArrayList<>();
        List<IEnergyStorage> inputs = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            ConnectionType type = connectionStates.get(dir);
            if (type == ConnectionType.NONE) continue;

            BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
            if (neighbor == null) continue;

            LazyOptional<IEnergyStorage> neighborEnergy = neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite());
            neighborEnergy.ifPresent(storage -> {
                if (storage.canExtract() && type.canReceive()) {
                    inputs.add(storage);
                }
                if (storage.canReceive() && type.canExtract()) {
                    outputs.add(storage);
                }
            });
        }

        for (IEnergyStorage source : inputs) {

            int canPull = source.extractEnergy(MAX_TRANSFER, true);
            if (canPull > 0) {
                int received = energyStorage.receiveEnergy(canPull, false);
                source.extractEnergy(received, false);
            }
        }

        if (energyStorage.getEnergyStored() > 0 && !outputs.isEmpty()) {

            int energyToSend = Math.min(energyStorage.getEnergyStored(), MAX_TRANSFER);
            int energyPerOutput = energyToSend / outputs.size();

            if (energyPerOutput > 0) {
                for (IEnergyStorage destination : outputs) {
                    int sent = destination.receiveEnergy(energyPerOutput, false);
                    energyStorage.extractEnergy(sent, false);
                }
            }
        }
    }
}
