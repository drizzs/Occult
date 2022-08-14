package com.drizzs.occult.common.fluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class QuicksilverFluid extends ForgeFlowingFluid {

    public QuicksilverFluid(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    public int getAmount(FluidState state) {
        return state.getValue(LEVEL);
    }

    @Override
    protected boolean canConvertToSource() {
        return true;
    }
}
