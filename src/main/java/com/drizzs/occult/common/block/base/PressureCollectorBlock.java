package com.drizzs.occult.common.block.base;

import com.drizzs.occult.common.blockentity.test.base.IPressureCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PressureCollectorBlock extends BaseEntityBlock {

    protected static final VoxelShape EASTSHAPE = Block.box(9.0, 7.0, 7.0, 16.0, 9.0, 9.0);
    protected static final VoxelShape WESTSHAPE = Block.box(0.0, 7.0, 7.0, 7.0, 9.0, 9.0);
    protected static final VoxelShape NORTHSHAPE = Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 7.0 );
    protected static final VoxelShape SOUTHSHAPE = Block.box( 7.0, 7.0, 9.0, 9.0, 9.0, 16.0);

    public static final DirectionProperty FACING;

    public PressureCollectorBlock(){
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter get, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape shape = EASTSHAPE;
        if(state.getValue(FACING).equals(Direction.WEST))
            shape = WESTSHAPE;
        else if(state.getValue(FACING).equals(Direction.NORTH))
            shape = NORTHSHAPE;
        else if(state.getValue(FACING).equals(Direction.SOUTH))
            shape = SOUTHSHAPE;
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.stateDefinition.any().setValue(FACING,context.getHorizontalDirection());
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((IPressureCollector) blockEntity).tick();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }



    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }

}
