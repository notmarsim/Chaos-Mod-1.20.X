package net.marsim.chaosmod.energy.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnergyNetworkSavedData extends SavedData {

    private static final String DATA_NAME = "chaosmod_energy_networks";

    private final Map<UUID, EnergyNetwork> networks = new HashMap<>();
    private final EnergyNetworkManager manager = new EnergyNetworkManager(this);



    public EnergyNetworkManager getManager() {
        return manager;
    }

    public static EnergyNetworkSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                EnergyNetworkSavedData::load,
                EnergyNetworkSavedData::new,
                DATA_NAME
        );
    }


    public EnergyNetwork createNetwork() {
        EnergyNetwork network = new EnergyNetwork(UUID.randomUUID());

        networks.put(network.getId(), network);
        setDirty();
        return network;
    }

    public void removeNetwork(UUID id) {
        networks.remove(id);
        setDirty();
    }

    public EnergyNetwork getNetwork(UUID id) {
        return networks.get(id);
    }

    public Map<UUID, EnergyNetwork> getNetworks() {
        return networks;
    }



    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag networksTag = new CompoundTag();

        for (var entry : networks.entrySet()) {
            networksTag.put(
                    entry.getKey().toString(),
                    entry.getValue().save()
            );
        }

        tag.put("Networks", networksTag);
        return tag;
    }

    public static EnergyNetworkSavedData load(CompoundTag tag) {
        EnergyNetworkSavedData data = new EnergyNetworkSavedData();

        CompoundTag networksTag = tag.getCompound("Networks");

        for (String key : networksTag.getAllKeys()) {
            UUID id = UUID.fromString(key);
            EnergyNetwork network =
                    EnergyNetwork.load(networksTag.getCompound(key));
            data.networks.put(id, network);
        }

        return data;
    }
}
