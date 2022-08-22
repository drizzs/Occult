package com.drizzs.occult.common.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeRecipeSerializer;
import org.jetbrains.annotations.NotNull;

public abstract class BaseMachineEntity extends InventoryBlockEntity{

    protected boolean isActive;
    protected int progress;

    public BaseMachineEntity(BlockEntityType<?> type, int slotSize,BlockPos pos, BlockState state) {
        super(type, slotSize, pos, state);
        setIsActive();
        progress = 0;
    }

    public void setIsActive(){
        isActive = true;
    }

    public int getProgress() {
        return progress;
    }

    public void resetProgress() {
        progress = 0;
    }

    public void addProgress() {
        ++progress;
    }

    public boolean isActive() {
        return isActive;
    }

}
