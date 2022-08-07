package com.drizzs.occult.common.container;

import com.drizzs.occult.common.blockentity.test.CrusherBE;
import com.drizzs.occult.common.container.base.BaseContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuConstructor;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import static com.drizzs.occult.register.OcBlocks.CRUSHER;
import static com.drizzs.occult.register.OcContainers.CRUSHER_CONTAINER;

public class CrusherContainer extends BaseContainer {

    protected final ContainerLevelAccess containerAccess;
    protected final CrusherBE machine;
    private final BlockPos pos;

    public CrusherContainer(int id, Inventory playerinv) {
        this(id,playerinv,null,new ItemStackHandler(2), BlockPos.ZERO);
    }

    protected CrusherContainer(int id, Inventory inventory, CrusherBE machine, IItemHandler slots, BlockPos pos) {
        super(CRUSHER_CONTAINER.get(), id);
        this.containerAccess = ContainerLevelAccess.create(inventory.player.level,pos);
        this.pos = pos;
        addPlayerChestInventory(inventory);
        addHotbarSlots(inventory);
        this.machine = machine;
    }

    @Override
    public void setDisabled(boolean disabled) {

    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(containerAccess,player,CRUSHER.get());
    }

    public static MenuConstructor getServerContainer(CrusherBE entity, BlockPos pos){
        return (id,playerInventory, player) -> new CrusherContainer(id,playerInventory, entity,entity.inventory, pos);
    }

    public CrusherBE getMachine() {
        return machine;
    }
}
