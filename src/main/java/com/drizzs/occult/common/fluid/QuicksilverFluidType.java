package com.drizzs.occult.common.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

public class QuicksilverFluidType extends FluidType {

    public QuicksilverFluidType() {
        super(FluidType.Properties.create()
                .supportsBoating(false)
                .canHydrate(false)
                .canExtinguish(false)
                .canSwim(false)
                .canPushEntity(true)
                .canDrown(true)
                .pathType(BlockPathTypes.WATER)
                .adjacentPathType(BlockPathTypes.WATER_BORDER)
                .canConvertToSource(true)
                .fallDistanceModifier(0.01F)
                .motionScale(0.001)
                .rarity(Rarity.RARE)
                .viscosity(50)
                .density(2000)
                .temperature(600)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY));
    }

}
