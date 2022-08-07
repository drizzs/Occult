package com.drizzs.occult.common.blockentity.test;

import com.drizzs.occult.common.blockentity.test.base.BasePressureMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.occult.register.OcBlockEntities.CRUSHER_BE;

public class CrusherBE extends BasePressureMachine {

    public CrusherBE(BlockPos pos, BlockState state) {
        super(CRUSHER_BE.get(), 6, pos, state);

    }


}
