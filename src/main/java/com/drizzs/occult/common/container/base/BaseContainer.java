package com.drizzs.occult.common.container.base;

import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.api.gui.AssetTypes.AssetTypes;
import com.drizzs.occult.api.gui.interfaces.IAssetProvider;
import com.drizzs.occult.api.gui.interfaces.IDisableContainer;
import com.drizzs.occult.client.addon.DisableableSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public abstract class BaseContainer extends AbstractContainerMenu implements IDisableContainer {
    private boolean hasPlayerInventory;

    private IPressure pressure;
    protected BaseContainer(@Nullable MenuType<?> menu, int id) {
        super(menu, id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        var retStack = ItemStack.EMPTY;
        final Slot slot = getSlot(index);
        if (slot.hasItem()) {
            final ItemStack item = slot.getItem();
            retStack = item.copy();
            if (index < 27) {
                if (!moveItemStackTo(item, 27, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(item, 0, 27, false))
                return ItemStack.EMPTY;

            if (item.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return retStack;
    }

    public void addPlayerChestInventory(Inventory inventory) {
        Point invPos = IAssetProvider.getAsset(IAssetProvider.DEFAULT_PROVIDER, AssetTypes.BACKGROUND).getInventoryPosition();
        if (!hasPlayerInventory) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                    addSlot(new DisableableSlot(inventory, j + i * 9 + 9, invPos.x + j * 18, invPos.y + i * 18, this));
                }
            }
            hasPlayerInventory = true;
        }
    }

    public void addHotbarSlots(Inventory inventory) {
        Point hotbarPos = IAssetProvider.getAsset(IAssetProvider.DEFAULT_PROVIDER, AssetTypes.BACKGROUND).getHotbarPosition();
        for (int k = 0; k < 9; k++) {
            addSlot(new Slot(inventory, k, hotbarPos.x + k * 18, hotbarPos.y));
        }
    }

    public void setPressure(IPressure pressure) {
        this.pressure = pressure;
    }

    public IPressure getPressure() {
        return pressure;
    }
}
