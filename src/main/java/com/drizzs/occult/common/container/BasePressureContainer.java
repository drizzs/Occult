package com.drizzs.occult.common.container;

import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.api.capability.PressureCap;
import com.drizzs.occult.common.blockentity.base.BasePressureMachine;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class BasePressureContainer extends AbstractContainerMenu {

    private final BlockPos pos;
    private final BasePressureMachine entity;
    public BasePressureContainer(@Nullable MenuType<?> menuType,int id, BasePressureMachine entity, BlockPos pos) {
        this(menuType,id,entity,new ItemStackHandler(2), pos);
        final int slotSizePlus2 = 18, startX = 8, startY = 86, hotbarY = 144;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot((Container) entity.inventory, 9 + row * 9 + column, startX + column * slotSizePlus2,
                        startY + row * slotSizePlus2));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot((Container) entity.inventory, column, startX + column * slotSizePlus2, hotbarY));
        }

    }

    protected BasePressureContainer(@Nullable MenuType<?> menuType, int id,BasePressureMachine entity, IItemHandler slots, BlockPos pos) {
        super(menuType, id);
        this.pos = pos;
        this.entity = entity;
    }

    public boolean isActivated(){
        return false;
    }

    public Object2IntMap<PressureType> containedPressure(){
        return PressureCap.getAllPressureFromTile(this.entity);
    }

    public int getPressureCapacity(){
        return PressureCap.getTileEntityPressure(this.entity).map(IPressure::getPressureCapacity).get();
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }

}
