package net.marsim.chaosmod.energy.network;

import net.marsim.chaosmod.block.entity.CableBlockEntity;
import net.marsim.chaosmod.block.entity.ConnectionType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.*;

public class EnergyNetworkManager {

    private final EnergyNetworkSavedData data;
    private final Map<BlockPos, UUID> cableToNetwork = new HashMap<>();

    public EnergyNetworkManager(EnergyNetworkSavedData data) {
        this.data = data;
    }


    public void onCablePlaced(Level level, BlockPos pos) {
        EnergyNetwork network = data.createNetwork();
        network.addCable(pos);

        cableToNetwork.put(pos, network.getId());
        rebuildNetwork(level, pos);
        data.setDirty();
    }

    public void rebuildNetwork(Level level, BlockPos start) {
        UUID netId = cableToNetwork.get(start);
        if (netId == null) return;

        EnergyNetwork network = data.getNetwork(netId);
        if (network == null) return;

        Queue<BlockPos> queue = new ArrayDeque<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            if (!visited.add(current)) continue;

            for (Direction dir : Direction.values()) {
                BlockPos next = current.relative(dir);
                BlockEntity be = level.getBlockEntity(next);

                if (be instanceof CableBlockEntity) {
                    if (!cableToNetwork.containsKey(next)) {
                        cableToNetwork.put(next, netId);
                        network.addCable(next);
                        queue.add(next);
                    }
                } else if (be != null && level.getBlockEntity(current) instanceof CableBlockEntity cable) {

                    ConnectionType type = cable.getConnection(dir);

                    if (type == ConnectionType.NONE) continue;

                    be.getCapability(ForgeCapabilities.ENERGY, dir.getOpposite())
                            .ifPresent(storage -> {
                                if (type.canExtract() && storage.canExtract()) {
                                    network.addProducer(next);
                                }
                                if (type.canReceive() && storage.canReceive()) {
                                    network.addConsumer(next);
                                }
                            });
                }
            }
        }
    }
    private void rebuildAll(Level level) {
        cableToNetwork.clear();

        for (EnergyNetwork net : data.getNetworks().values()) {
            net.clear();
        }

        for (EnergyNetwork net : data.getNetworks().values()) {
            for (BlockPos cable : net.getCables()) {
                onCablePlaced(level, cable);
            }
        }
    }

    public void onCableRemoved(Level level, BlockPos pos) {
        UUID netId = cableToNetwork.remove(pos);
        if (netId == null) return;

        EnergyNetwork network = data.getNetwork(netId);
        if (network == null) return;

        network.removeCable(pos);


        rebuildAll(level);

        data.setDirty();
    }


    public void tick(ServerLevel level) {
        for (EnergyNetwork network : data.getNetworks().values()) {
            network.tick(level);
        }
    }
}
