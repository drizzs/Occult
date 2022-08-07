package com.drizzs.occult.common.blockentity.test.base;


import com.drizzs.occult.api.capability.PressureCap;
import com.drizzs.occult.common.blockentity.base.InventoryBlockEntity;
import com.drizzs.occult.common.item.upgrade.CollectionUpgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BasePressureMachine extends InventoryBlockEntity implements IPressureConsumer {

    private int progress = 0;

    public BasePressureMachine(BlockEntityType<?> type, int slotSize, BlockPos pos, BlockState state) {
        super(type, slotSize, pos, state);
    }


    public void collectPressure() {
        assert level != null;
        PressureCap.getAllPressureFromChunk(level.getChunkAt(getBlockPos())).forEach((pressureType, integer) -> {
            ItemStack item = inventory.getStackInSlot(3);
            int pressureToRemove = 1;
            if(item.getItem() instanceof CollectionUpgrade cu) {
                pressureToRemove = cu.getCollectionSpeed();
            }
            PressureCap.addTilePressure(this, pressureType, pressureToRemove);
            PressureCap.removeChunkPressure(level.getChunkAt(getBlockPos()), pressureType, pressureToRemove);
        });
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
