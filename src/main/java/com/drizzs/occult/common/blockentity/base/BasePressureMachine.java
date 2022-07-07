package com.drizzs.occult.common.blockentity.base;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BasePressureMachine extends InventoryBlockEntity implements IPressureConsumer {



    public BasePressureMachine(BlockEntityType<?> type, int slotSize, BlockPos pos, BlockState state) {
        super(type, slotSize, pos, state);
    }






}
