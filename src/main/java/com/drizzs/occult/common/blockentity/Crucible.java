package com.drizzs.occult.common.blockentity;

import com.drizzs.occult.common.blockentity.base.InventoryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class Crucible extends InventoryBlockEntity {

    private boolean isActive;
    private int progress;



    public Crucible(BlockPos pos, BlockState state) {
        super(type, 5, pos, state);
    }



}
