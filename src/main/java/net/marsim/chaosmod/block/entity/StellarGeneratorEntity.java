package net.marsim.chaosmod.block.entity;

import net.marsim.chaosmod.screen.StellarGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StellarGeneratorEntity extends BlockEntity implements MenuProvider {
    private static final int ENERGY_TRANSFER_RATE = 1_000_000;
    private static final int CAPACITY = 500_000_000;
    private static final int MAX_RECEIVE = 500_000;

    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final EnergyStorage energyStorage = new EnergyStorage(CAPACITY, MAX_RECEIVE, ENERGY_TRANSFER_RATE);
    private LazyOptional<IEnergyStorage> lazyEnergy = LazyOptional.of(() -> energyStorage);
    protected final ContainerData data;

    public StellarGeneratorEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.STELLAR_GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> StellarGeneratorEntity.this.energyStorage.getEnergyStored();
                    case 1 -> StellarGeneratorEntity.this.energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {}
            @Override
            public int getCount() { return 2; }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergy.cast();
        }
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

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

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i =0; i < itemHandler.getSlots();i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.chaosmod.stellar_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player player) {
        return new StellarGeneratorMenu(pContainerId, pPlayerInventory,this,this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("energy", energyStorage.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("energy")) {
            energyStorage.deserializeNBT(pTag.get("energy"));
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            return;
        }

        int energyGeneratedThisTick = 0;
        if (level.isDay() && level.canSeeSky(pos.above())) {
            energyGeneratedThisTick = MAX_RECEIVE;
        }

        int energyTransferredThisTick = 0;
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            IEnergyStorage itemEnergy = stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);

            if (itemEnergy != null && itemEnergy.canReceive()) {
                int energyAvailable = energyStorage.getEnergyStored() + energyGeneratedThisTick;
                int maxEnergyToSend = Math.min(ENERGY_TRANSFER_RATE, energyAvailable);

                if (maxEnergyToSend > 0) {
                    energyTransferredThisTick = itemEnergy.receiveEnergy(maxEnergyToSend, false);
                }
            }
        }

        int netEnergyChange = energyGeneratedThisTick - energyTransferredThisTick;

        if (netEnergyChange > 0) {
            energyStorage.receiveEnergy(netEnergyChange, false);
        } else if (netEnergyChange < 0) {
            energyStorage.extractEnergy(Math.abs(netEnergyChange), false);
        }

        setChanged(level, pos, state);
    }
}