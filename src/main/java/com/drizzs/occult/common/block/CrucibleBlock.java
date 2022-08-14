package com.drizzs.occult.common.block;

import com.drizzs.occult.common.block.base.BaseHorizontalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.register.OcBlockEntities.CRUCIBLE_BE;

public class CrucibleBlock extends BaseHorizontalBlockEntity {

    public CrucibleBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5,6).requiresCorrectToolForDrops().sound(SoundType.METAL));
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CRUCIBLE_BE.get().create(pos,state);
    }

}
