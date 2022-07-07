package com.drizzs.occult.common.blockentity;

import com.drizzs.occult.api.PressureType;
import com.drizzs.occult.api.capability.PressureCap;
import com.drizzs.occult.register.OcBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PressureCollectorBlockEntity extends BlockEntity {

    public PressureCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(OcBlockEntities.PRESSURE_COLLECTOR_BE.get(), pos, state);
    }

    private void collectPressure(){
        PressureCap.getAllPressureFromChunk(level.getChunkAt(getBlockPos())).forEach((pressureType, integer) -> {
            PressureCap.addTilePressure(this,pressureType,1);
            PressureCap.removeChunkPressure(level.getChunkAt(getBlockPos()),pressureType,1);
        });
    }


}
