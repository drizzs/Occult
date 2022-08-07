package com.drizzs.occult.common.block.pce;

import com.drizzs.occult.common.block.base.PressureCollectorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.register.OcBlockEntities.PRESSURE_COLLECTOR4_BE;

public class ExtraStrongPressureCollector extends PressureCollectorBlock {

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return PRESSURE_COLLECTOR4_BE.get().create(pos,state);
    }

}
