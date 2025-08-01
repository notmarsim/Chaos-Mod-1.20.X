package net.marsim.chaosmod.block.custom;

import net.marsim.chaosmod.block.entity.ModBlockEntities;
import net.marsim.chaosmod.block.entity.StellarGeneratorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class StellarGeneratorBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);
    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }
    @Override
    public boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }
    public StellarGeneratorBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof StellarGeneratorEntity) {
                ((StellarGeneratorEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof StellarGeneratorEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (StellarGeneratorEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StellarGeneratorEntity(pPos,pState);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.STELLAR_GENERATOR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> ((StellarGeneratorEntity)pBlockEntity).tick(pLevel1, pPos, pState1));
    }
} 