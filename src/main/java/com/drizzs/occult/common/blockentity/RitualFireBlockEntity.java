package com.drizzs.occult.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.drizzs.occult.register.OcBlockEntities.RITUAL_FIRE_BE;

public class RitualFireBlockEntity extends BlockEntity {

    public RitualFireBlockEntity(BlockPos pos, BlockState state) {
        super(RITUAL_FIRE_BE.get(), pos, state);
    }


}
