package net.marsim.chaosmod.energy.network;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EnergyNetwork {

    private final UUID id;
    private static final int MAX_PULL = 10_000;
    private final Set<BlockPos> cables = new HashSet<>();
    private final Set<BlockPos> producers = new HashSet<>();
    private final Set<BlockPos> consumers = new HashSet<>();

    public EnergyNetwork(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }



    public void addCable(BlockPos pos) {
        cables.add(pos);
    }

    public void addProducer(BlockPos pos) {
        producers.add(pos);
    }

    public void addConsumer(BlockPos pos) {
        consumers.add(pos);
    }


    public void tick(ServerLevel level) {
        if (producers.isEmpty() || consumers.isEmpty()) return;


        int totalDemand = 0;
        for (BlockPos pos : consumers) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                totalDemand += be.getCapability(ForgeCapabilities.ENERGY)
                        .map(s -> s.receiveEnergy(MAX_PULL, true)).orElse(0);
            }
        }

        if (totalDemand <= 0) return;


        int totalExtracted = 0;
        for (BlockPos pos : producers) {
            if (totalExtracted >= totalDemand) break;

            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                int toPull = Math.min(totalDemand - totalExtracted, MAX_PULL);
                totalExtracted += be.getCapability(ForgeCapabilities.ENERGY)
                        .map(s -> s.extractEnergy(toPull, false)).orElse(0);
            }
        }


        if (totalExtracted > 0) {
            int share = totalExtracted / consumers.size();
            int remainder = totalExtracted % consumers.size();

            for (BlockPos pos : consumers) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be != null) {
                    be.getCapability(ForgeCapabilities.ENERGY).ifPresent(s -> {
                        int amount = share + (remainder > 0 ? 1 : 0);
                        s.receiveEnergy(amount, false);
                    });
                }
            }
        }
    }
    public void clear() {
        cables.clear();
        producers.clear();
        consumers.clear();
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();

        tag.putUUID("Id", id);

        tag.putLongArray("Cables",
                cables.stream().map(BlockPos::asLong).toList());

        tag.putLongArray("Producers",
                producers.stream().map(BlockPos::asLong).toList());

        tag.putLongArray("Consumers",
                consumers.stream().map(BlockPos::asLong).toList());

        return tag;
    }
    public void removeCable(BlockPos pos) {
        cables.remove(pos);
    }
    public Set<BlockPos> getCables() {
        return Collections.unmodifiableSet(cables);
    }

    public Set<BlockPos> getProducers() {
        return Collections.unmodifiableSet(producers);
    }

    public Set<BlockPos> getConsumers() {
        return Collections.unmodifiableSet(consumers);
    }

    public static EnergyNetwork load(CompoundTag tag) {
        EnergyNetwork net = new EnergyNetwork(tag.getUUID("Id"));

        for (long l : tag.getLongArray("Cables"))
            net.cables.add(BlockPos.of(l));

        for (long l : tag.getLongArray("Producers"))
            net.producers.add(BlockPos.of(l));

        for (long l : tag.getLongArray("Consumers"))
            net.consumers.add(BlockPos.of(l));

        return net;
    }

}
