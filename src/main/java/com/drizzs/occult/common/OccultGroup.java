package com.drizzs.occult.common;

import com.drizzs.occult.register.OcItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OccultGroup extends CreativeModeTab
{

    public static final OccultGroup OCCULT_GROUP = new OccultGroup(CreativeModeTab.getGroupCountSafe(), "occult");

    private OccultGroup(int index, String label)
    {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack makeIcon() {
        return new ItemStack(OcItems.MERCURY.get());
    }

}
