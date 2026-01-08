package net.marsim.chaosmod.block.entity;

import net.marsim.chaosmod.energy.network.EnergyNetworkSavedData;
import net.marsim.chaosmod.energy.storage.CableEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.marsim.chaosmod.block.entity.ModBlockEntities;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.EnumMap;
import java.util.Map;

public class CableBlockEntity extends BlockEntity {
    private final CableEnergyStorage energy = new CableEnergyStorage(10_000);
    private final LazyOptional<IEnergyStorage> energyCap =
            LazyOptional.of(() -> energy);
    private final Map<Direction, ConnectionType> connections =
            new EnumMap<>(Direction.class);

    public CableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CABLE_BE.get(), pos, state);

        for (Direction dir : Direction.values()) {
            connections.put(dir, ConnectionType.INPUT_OUTPUT);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap != ForgeCapabilities.ENERGY) {
            return super.getCapability(cap, side);
        }

        if (side == null) {
            return energyCap.cast();
        }

        ConnectionType type = connections.get(side);
        if (type != null && type != ConnectionType.NONE) {
            return energyCap.cast();
        }

        return LazyOptional.empty();
    }



    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyCap.invalidate();
    }

    public ConnectionType getConnection(Direction dir) {
        return connections.get(dir);
    }

    public void setConnection(Direction dir, ConnectionType type) {
        connections.put(dir, type);
        setChanged();
    }

    public void cycleConnection(Direction dir) {
        ConnectionType current = connections.get(dir);

        ConnectionType next = switch (current) {
            case NONE -> ConnectionType.INPUT;
            case INPUT -> ConnectionType.OUTPUT;
            case OUTPUT -> ConnectionType.INPUT_OUTPUT;
            case INPUT_OUTPUT -> ConnectionType.NONE;
        };

        connections.put(dir, next);
        setChanged();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
        if (level instanceof ServerLevel serverLevel) {
            EnergyNetworkSavedData.get(serverLevel)
                    .getManager()
                    .rebuildNetwork(level, worldPosition);
        }
    }








    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        for (Direction dir : Direction.values()) {
            tag.putString(
                    "Conn_" + dir.getName(),
                    connections.get(dir).name()
            );
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        for (Direction dir : Direction.values()) {
            String key = "Conn_" + dir.getName();
            if (tag.contains(key)) {
                connections.put(
                        dir,
                        ConnectionType.valueOf(tag.getString(key))
                );
            }
        }
    }
}
