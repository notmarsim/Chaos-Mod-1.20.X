package net.marsim.chaosmod.block.custom;

import net.marsim.chaosmod.block.entity.CableBlockEntity;
import net.marsim.chaosmod.block.entity.ConnectionType;
import net.marsim.chaosmod.block.entity.ModBlockEntities;
import net.marsim.chaosmod.energy.network.EnergyNetworkSavedData;
import net.marsim.chaosmod.item.custom.ConfiguratorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

public class VoidCableBlock extends BaseEntityBlock {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST  = BooleanProperty.create("east");
    public static final BooleanProperty WEST  = BooleanProperty.create("west");
    public static final BooleanProperty UP    = BooleanProperty.create("up");
    public static final BooleanProperty DOWN  = BooleanProperty.create("down");

    private static final VoxelShape CORE  = Block.box(6, 6, 6, 10, 10, 10);
    private static final VoxelShape NORTH_SHAPE = Block.box(6, 6, 0, 10, 10, 6);
    private static final VoxelShape SOUTH_SHAPE = Block.box(6, 6, 10, 10, 10, 16);
    private static final VoxelShape EAST_SHAPE  = Block.box(10, 6, 6, 16, 10, 10);
    private static final VoxelShape WEST_SHAPE  = Block.box(0, 6, 6, 6, 10, 10);
    private static final VoxelShape UP_SHAPE    = Block.box(6, 10, 6, 10, 16, 10);
    private static final VoxelShape DOWN_SHAPE  = Block.box(6, 0, 6, 10, 6, 10);

    public VoidCableBlock(Properties props) {
        super(props);
        registerDefaultState(stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
        );
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return updateConnections(ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction dir,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        return updateConnections((Level) level, pos);
    }

    private BlockState updateConnections(Level level, BlockPos pos) {
        return defaultBlockState()
                .setValue(NORTH, canConnect(level, pos, Direction.NORTH))
                .setValue(SOUTH, canConnect(level, pos, Direction.SOUTH))
                .setValue(EAST,  canConnect(level, pos, Direction.EAST))
                .setValue(WEST,  canConnect(level, pos, Direction.WEST))
                .setValue(UP,    canConnect(level, pos, Direction.UP))
                .setValue(DOWN,  canConnect(level, pos, Direction.DOWN));
    }

    private boolean canConnect(Level level, BlockPos pos, Direction direction) {
        BlockEntity neighbor = level.getBlockEntity(pos.relative(direction));
        if (neighbor == null) return false;


        if (neighbor instanceof CableBlockEntity) {
            return true;
        }


        return neighbor
                .getCapability(ForgeCapabilities.ENERGY, direction.getOpposite())
                .isPresent();
    }




    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        VoxelShape shape = CORE;

        if (state.getValue(NORTH)) shape = Shapes.or(shape, NORTH_SHAPE);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, SOUTH_SHAPE);
        if (state.getValue(EAST))  shape = Shapes.or(shape, EAST_SHAPE);
        if (state.getValue(WEST))  shape = Shapes.or(shape, WEST_SHAPE);
        if (state.getValue(UP))    shape = Shapes.or(shape, UP_SHAPE);
        if (state.getValue(DOWN))  shape = Shapes.or(shape, DOWN_SHAPE);

        return shape;
    }



    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }




    @Override
    public void onPlace(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState oldState,
            boolean isMoving
    ) {
        super.onPlace(state, level, pos, oldState, isMoving);

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            EnergyNetworkSavedData
                    .get(serverLevel)
                    .getManager()
                    .onCablePlaced(level, pos);
        }
    }
    @Override
    public void onRemove(
            BlockState state,
            Level level,
            BlockPos pos,
            BlockState newState,
            boolean isMoving
    ) {
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            EnergyNetworkSavedData
                    .get(serverLevel)
                    .getManager()
                    .onCableRemoved(level, pos);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {

        return null;
    }


    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof ConfiguratorItem)) return InteractionResult.PASS;

        if (level.getBlockEntity(pos) instanceof CableBlockEntity cable) {
            Direction side = hit.getDirection();
            cable.cycleConnection(side);
            player.sendSystemMessage(
                    Component.literal("Side set to: " + cable.getConnection(side))
            );
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
