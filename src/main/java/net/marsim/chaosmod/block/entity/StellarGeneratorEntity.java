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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StellarGeneratorEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final EnergyStorage energyStorage = new EnergyStorage(500_000_000, 100_000, 100_000) {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return super.receiveEnergy(maxReceive, simulate);
        }
        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return super.extractEnergy(maxExtract, simulate);
        }
    };
    private LazyOptional<net.minecraftforge.energy.IEnergyStorage> lazyEnergy = LazyOptional.of(() -> energyStorage);
    protected final ContainerData data;
    public StellarGeneratorEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.STELLAR_GENERATOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                if (pIndex == 0) return energyStorage.getEnergyStored();
                if (pIndex == 1) return energyStorage.getMaxEnergyStored();
                return 0;
            }
            @Override
            public void set(int pIndex, int pValue) {}
            @Override
            public int getCount() { return 2; }
        };
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergy.cast();
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
        pTag.putInt("stellar_generator.energy", energyStorage.getEnergyStored());
        super.saveAdditional(pTag);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        if (pTag.contains("stellar_generator.energy")) {
            int energy = pTag.getInt("stellar_generator.energy");
            try {
                java.lang.reflect.Field f = EnergyStorage.class.getDeclaredField("energy");
                f.setAccessible(true);
                f.setInt(energyStorage, energy);
            } catch (Exception ignored) {}
        }
    }
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level == null || level.isClientSide) return;
        // geração solar: só de dia, céu visível, sem bloco sólido acima (exceto vidro)
        boolean canSeeSky = true;
        for (int y = pos.getY() + 1; y < level.getMaxBuildHeight(); y++) {
            BlockState above = level.getBlockState(new BlockPos(pos.getX(), y, pos.getZ()));
            if (!above.isAir() && !above.is(Blocks.GLASS) && !above.is(Blocks.GLASS_PANE)) {
                canSeeSky = false;
                break;
            }
        }
        if (level.isDay() && canSeeSky) {
            // 500000 FE/tick
            int fePerTick = 500000;
            energyStorage.receiveEnergy(fePerTick, false);
        }
        // transferir energia para item recarregável no slot 0
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            stack.getCapability(ForgeCapabilities.ENERGY).ifPresent(itemEnergy -> {
                int toTransfer = Math.min(1_000_000, energyStorage.getEnergyStored());
                if (toTransfer > 0) {
                    int transferred = itemEnergy.receiveEnergy(toTransfer, false);
                    if (transferred > 0) {
                        energyStorage.extractEnergy(transferred, false);
                    }
                }
            });
        }
    }
} 