package net.marsim.chaosmod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ChaosSavedData extends SavedData {
    public boolean chaosModeActive = false;


    public static ChaosSavedData get(Level level) {
        if (level.isClientSide) throw new RuntimeException("Must not be called in Client");

        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(ChaosSavedData::load, ChaosSavedData::new, "chaos_mode_data");
    }

    public static ChaosSavedData load(CompoundTag nbt) {
        ChaosSavedData data = new ChaosSavedData();
        data.chaosModeActive = nbt.getBoolean("chaosModeActive");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.putBoolean("chaosModeActive", chaosModeActive);
        return nbt;
    }
}