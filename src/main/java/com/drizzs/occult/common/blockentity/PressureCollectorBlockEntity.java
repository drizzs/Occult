package com.drizzs.occult.common.blockentity;

import com.drizzs.occult.common.blockentity.base.BasePressureMachine;
import com.drizzs.occult.common.item.upgrade.CollectionUpgrade;
import com.drizzs.occult.common.item.upgrade.RecycleUpgrade;
import com.drizzs.occult.common.item.upgrade.SpeedUpgrade;
import com.drizzs.occult.common.item.upgrade.StorageUpgrade;
import com.drizzs.occult.register.OcBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class PressureCollectorBlockEntity extends BasePressureMachine {

    public PressureCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(OcBlockEntities.PRESSURE_COLLECTOR_BE.get(), 6,pos, state);
    }

    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(this.itemSlots) {
            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                PressureCollectorBlockEntity.this.updateEntity();
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                PressureCollectorBlockEntity.this.updateEntity();
                return super.insertItem(slot, stack, simulate);
            }

            @Override
            public void setStackInSlot(int slot, @NotNull ItemStack stack) {
                switch (slot) {
                    case 0 :
                        if(isInRecipeList(stack))
                            super.setStackInSlot(slot,stack);
                    case 1 :
                        if(isInRecipeList(stack))
                            super.setStackInSlot(slot,stack);
                    case 2 :
                        if(stack.getItem() instanceof SpeedUpgrade)
                            super.setStackInSlot(slot,stack);
                    case 3 :
                        if(stack.getItem() instanceof CollectionUpgrade)
                            super.setStackInSlot(slot,stack);
                    case 4 :
                        if(stack.getItem() instanceof RecycleUpgrade)
                            super.setStackInSlot(slot,stack);
                    case 5 :
                        if(stack.getItem() instanceof StorageUpgrade)
                            super.setStackInSlot(slot,stack);
                    default:
                        break;
                }
            }
        };
    }

    private boolean isInRecipeList(ItemStack stack){
        return false;
    }

}
