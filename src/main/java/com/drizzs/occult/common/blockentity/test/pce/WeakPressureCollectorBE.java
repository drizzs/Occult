package com.drizzs.occult.common.blockentity.test.pce;

import com.drizzs.occult.common.blockentity.test.base.BasePressureCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.occult.register.OcBlockEntities.PRESSURE_COLLECTOR2_BE;

public class WeakPressureCollectorBE extends BasePressureCollector {

    public WeakPressureCollectorBE(BlockPos pos, BlockState state) {
        super(PRESSURE_COLLECTOR2_BE.get(), pos, state, 15);
    }

}
