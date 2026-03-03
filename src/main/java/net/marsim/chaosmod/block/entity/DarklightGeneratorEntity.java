package net.marsim.chaosmod.block.entity;

import net.marsim.chaosmod.energy.GeneratorEnergyStorage;
import net.marsim.chaosmod.energy.IEnergyExporter;
import net.marsim.chaosmod.screen.DarklightGeneratorMenu;
import net.marsim.chaosmod.screen.StellarGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DarklightGeneratorEntity extends BlockEntity implements MenuProvider, IEnergyExporter {
    private static final int ENERGY_TRANSFER_RATE = 1_000_000;
    private static final int CAPACITY = 500_000_000;
    private static final int MAX_RECEIVE = 10_000_000;
    private static final float EXPORT_THRESHOLD = 0.95f;

    private boolean isExporting = false;


    private final GeneratorEnergyStorage energyStorage =
            new GeneratorEnergyStorage(this, CAPACITY, MAX_RECEIVE, ENERGY_TRANSFER_RATE);

    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> energyStorage);

    protected final ContainerData data;

    public DarklightGeneratorEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DARKLIGHT_GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DarklightGeneratorEntity.this.energyStorage.getEnergyStored();
                    case 1 -> DarklightGeneratorEntity.this.energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {}
            @Override
            public int getCount() { return 2; }
        };
    }

    public boolean isExportingNow() { return isExporting; }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergy = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergy.invalidate();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) return lazyEnergy.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("energy", energyStorage.serializeNBT());
        pTag.putBoolean("isExporting", isExporting);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("energy")) {
            energyStorage.deserializeNBT(pTag.get("energy"));
        }
        this.isExporting = pTag.getBoolean("isExporting");
    }


    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) return;


        if (level.isDay() && level.canSeeSky(pos.above())) {
            energyStorage.generate(MAX_RECEIVE);
        }

        int stored = energyStorage.getEnergyStored();
        int capacity = energyStorage.getMaxEnergyStored();


        if (!isExporting && stored >= capacity) {
            isExporting = true;
        } else if (isExporting && stored <= (int)(capacity * EXPORT_THRESHOLD)) {
            isExporting = false;
        }



        setChanged(level, pos, state);
    }

    private void distributeEnergy(Level level, BlockPos pos) {
        int energyToExport = energyStorage.extractEnergy(ENERGY_TRANSFER_RATE, true);
        if (energyToExport <= 0) return;

        List<IEnergyStorage> receivers = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockEntity neighbor = level.getBlockEntity(pos.relative(direction));
            if (neighbor != null && neighbor != this) {
                neighbor.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    if (storage.canReceive()) receivers.add(storage);
                });
            }
        }

        if (!receivers.isEmpty()) {
            int totalSent = 0;
            int energyPerReceiver = energyToExport / receivers.size();

            for (IEnergyStorage receiver : receivers) {
                int sent = receiver.receiveEnergy(energyPerReceiver, false);
                totalSent += sent;
            }
            energyStorage.extractEnergy(totalSent, false);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.chaosmod.darklight_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player player) {
        return new DarklightGeneratorMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
