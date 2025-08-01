package net.marsim.chaosmod.block.entity; // Ou seu pacote

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

import java.util.HashMap;
import java.util.Map;

public class CableBlockEntity extends BlockEntity {
    private final Map<Direction, ConnectionType> connectionStates = new HashMap<>();
    private final EnergyStorage energyStorage = new EnergyStorage(10000, 1000, 1000);
    private final LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> energyStorage);

    public CableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CABLE_BE.get(), pPos, pBlockState);
        for (Direction dir : Direction.values()) {
            connectionStates.put(dir, ConnectionType.NONE);
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
        if (currentState == ConnectionType.NONE) {
            connectionStates.put(side, ConnectionType.INPUT);
        } else if (currentState == ConnectionType.INPUT) {
            connectionStates.put(side, ConnectionType.OUTPUT);
        } else {
            connectionStates.put(side, ConnectionType.NONE);
        }
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

        for (Direction dir : Direction.values()) {
            ConnectionType type = connectionStates.get(dir);
            if (type == ConnectionType.NONE) continue;

            BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
            if (neighbor == null) continue;

            LazyOptional<IEnergyStorage> neighborEnergy = neighbor.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite());

            neighborEnergy.ifPresent(storage -> {
                if (type == ConnectionType.OUTPUT) {
                    int energyToSend = energyStorage.extractEnergy(1000, true);
                    if (energyToSend > 0) {
                        int received = storage.receiveEnergy(energyToSend, false);
                        energyStorage.extractEnergy(received, false);
                    }
                }
                if (type == ConnectionType.INPUT) {
                    int energyToReceive = storage.extractEnergy(1000, true);
                    if (energyToReceive > 0) {
                        int received = energyStorage.receiveEnergy(energyToReceive, false);
                        storage.extractEnergy(received, false);
                    }
                }
            });
        }
    }
}
