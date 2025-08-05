package net.marsim.chaosmod.block.entity;

import net.marsim.chaosmod.recipe.VoidRefinerRecipe;
import net.marsim.chaosmod.screen.VoidRefinerMenu;
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
import net.minecraft.world.item.Item;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VoidRefinerEntity extends BlockEntity implements MenuProvider, ISideConfigurable {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2);
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(100_000, 2000, 3000);
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private static final int ENERGY_REQ_PER_TICK = 20; // Mantendo o valor alto para ser visível

    private final Map<Direction, IOSide> sideConfig = new HashMap<>();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 600;

    private class CustomEnergyStorage extends EnergyStorage {
        public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
            super(capacity, maxReceive, maxExtract);
        }

        protected void onEnergyChanged() {
            setChanged(); // Salva a alteração
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3); // Notifica o cliente
            }
        }

        public void setEnergy(int energy) {
            this.energy = energy;
            onEnergyChanged(); // Garante sincronização
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int extracted = super.extractEnergy(maxExtract, simulate);
            if (!simulate && extracted > 0) {
                onEnergyChanged();
            }
            return extracted;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int received = super.receiveEnergy(maxReceive, simulate);
            if (!simulate && received > 0) {
                onEnergyChanged();
            }
            return received;
        }
    }

    public VoidRefinerEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VOID_REFINER_BE.get(), pPos, pBlockState);

        for (Direction dir : Direction.values()) {
            sideConfig.put(dir, IOSide.INPUT);
        }

        this.data = new ContainerData(){
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> VoidRefinerEntity.this.progress;
                    case 1 -> VoidRefinerEntity.this.maxProgress;
                    case 2 -> VoidRefinerEntity.this.energyStorage.getEnergyStored();
                    case 3 -> VoidRefinerEntity.this.energyStorage.getMaxEnergyStored();
                    default -> 0;
                };
            }
            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> VoidRefinerEntity.this.progress = pValue;
                    case 1 -> VoidRefinerEntity.this.maxProgress = pValue;
                    case 2 -> VoidRefinerEntity.this.energyStorage.setEnergy(pValue);
                    case 3 -> {}
                }
            }
            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY && side != null) {
            if (sideConfig.get(side) == IOSide.INPUT) {
                return lazyEnergyHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    public IOSide getSideConfig(Direction side) {
        return this.sideConfig.get(side);
    }

    @Override
    public void setSideConfig(Direction side, IOSide ioSide) {
        this.sideConfig.put(side, ioSide);
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("void_refiner.progress", progress);
        pTag.put("energy", energyStorage.serializeNBT());

        for (Map.Entry<Direction, IOSide> entry : sideConfig.entrySet()) {
            pTag.putInt("side_config." + entry.getKey().getName(), entry.getValue().ordinal());
        }

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("void_refiner.progress");
        energyStorage.deserializeNBT(pTag.get("energy"));

        for (Direction dir : Direction.values()) {
            String key = "side_config." + dir.getName();
            if (pTag.contains(key)) {
                sideConfig.put(dir, IOSide.values()[pTag.getInt(key)]);
            }
        }
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        System.out.println("Tick chamado!");
        System.out.println("Energia atual: " + energyStorage.getEnergyStored());
        System.out.println("Tem receita? " + hasRecipe());

        if (hasRecipe()) {
            if (hasEnoughEnergy()) {
                int before = energyStorage.getEnergyStored();
                int extracted = energyStorage.extractEnergy(ENERGY_REQ_PER_TICK, false);
                int after = energyStorage.getEnergyStored();
                System.out.println("Consumido: " + extracted + " | Antes: " + before + " | Depois: " + after);

                increaseCraftingProgress();
                setChanged(pLevel, pPos, pState);

                if (hasProgressFinished()) {
                    craftItem();
                    resetProgress();
                }
            }
            // Se não tem energia, apenas espera — não reseta
        } else {
            // A receita não é mais válida: provavelmente o item foi removido ou alterado
            resetProgress();
        }

    }



    private boolean hasEnoughEnergy() {
        return energyStorage.getEnergyStored() >= ENERGY_REQ_PER_TICK;
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
        return Component.translatable("block.chaosmod.void_refiner");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player player) {
        return new VoidRefinerMenu(pContainerId, pPlayerInventory,this,this.data);
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        Optional<VoidRefinerRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        Optional<VoidRefinerRecipe> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());
        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<VoidRefinerRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i< itemHandler.getSlots(); i++){
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(VoidRefinerRecipe.Type.INSTANCE, inventory,level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }
}