package com.drizzs.occult.client.addon;

import com.drizzs.occult.api.gui.interfaces.IDisableContainer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

import java.util.function.BooleanSupplier;

public class DisableableSlot extends Slot {

    private final BooleanSupplier isDisabled;
    public DisableableSlot(Container container, int slot, int x, int y, IDisableContainer disableableContainer) {
        this(container, slot, x, y, disableableContainer::isDisabled);
    }

    public DisableableSlot(Container container, int slot, int x, int y, BooleanSupplier isDisabled) {
        super(container, slot, x, y);
        this.isDisabled = isDisabled;
    }

    @Override
    public boolean isActive() {
        return !isDisabled.getAsBoolean();
    }

}
