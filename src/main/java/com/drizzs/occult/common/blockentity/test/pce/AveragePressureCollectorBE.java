package com.drizzs.occult.common.blockentity.test.pce;

import com.drizzs.occult.common.blockentity.test.base.BasePressureCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.occult.register.OcBlockEntities.PRESSURE_COLLECTOR1_BE;

public class AveragePressureCollectorBE extends BasePressureCollector {

    public AveragePressureCollectorBE(BlockPos pos, BlockState state) {
        super(PRESSURE_COLLECTOR1_BE.get(), pos, state, 30);
    }

}
