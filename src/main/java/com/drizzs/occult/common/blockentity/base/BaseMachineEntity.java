package com.drizzs.occult.common.blockentity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeRecipeSerializer;

public abstract class BaseMachineEntity<T extends IForgeRecipeSerializer<?>> extends InventoryBlockEntity{

    private final T recipe;
    protected boolean isActive;
    protected int progress;

    public BaseMachineEntity(BlockEntityType<?> type, int slotSize,T recipe,BlockPos pos, BlockState state) {
        super(type, slotSize, pos, state);
        setIsActive();
        setProgress();
        this.recipe = recipe;
    }

    public void setIsActive(){
        isActive = false;
    }

    public void setProgress(){
        ++progress;
    }

    @Override
    public void tick() {
        if(isActive){
            ++progress;
        }else if ()

        super.tick();
    }
}
