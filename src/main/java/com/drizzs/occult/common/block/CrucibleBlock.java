package com.drizzs.occult.common.block;

import com.drizzs.occult.common.block.base.BaseHorizontalBlockEntity;
import com.drizzs.occult.common.blockentity.CrucibleBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.register.OcBlockEntities.CRUCIBLE_BE;

public class CrucibleBlock extends BaseHorizontalBlockEntity {

    public CrucibleBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5,6).requiresCorrectToolForDrops().sound(SoundType.METAL).noOcclusion().dynamicShape());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof CrucibleBE be) {
            return be.insertExtractItem(player, hand);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        BlockState above = level.getBlockState(pos.above());
        if (above.isAir()) {
           // level.setBlock(pos.above(), CRUCIBLETOP.get().getDefaultState());
        } else {
            //level.destroyBlock(pos, true);
        }
        super.setPlacedBy(level, pos, state, entity, stack);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        BlockState under = level.getBlockState(pos.above());
        if (under.getBlock() instanceof CrucibleTop ce) {

        } else {
           // LevelData newWorld = level.getChunk(pos).getWorldForge().getLevelData();
           // level..block.destroyBlock(pos, true);
        }
        super.onNeighborChange(state, level, pos, neighbor);
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CRUCIBLE_BE.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((CrucibleBE) blockEntity).tick();
    }
}
