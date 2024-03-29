package com.drizzs.occult.common.fluid;

import com.drizzs.occult.common.fluid.base.OcFlowingFluidBlock;
import com.drizzs.occult.common.fluid.base.OcFluid;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.common.OccultGroup.OCCULT_GROUP;

public class Quicksilver {

    public static final ResourceLocation MERCURY_STILL_TEXTURE = new ResourceLocation(MODID,"blocks/fluids/quicksilver_still");
    public static final ResourceLocation MERCURY_FLOWING_TEXTURE = new ResourceLocation(MODID,"blocks/fluids/quicksilver_flowing");

    public static Block.Properties stdProp = Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable();

    public static FlowingFluid MERCURY;
    public static FlowingFluid FLOWING_MERCURY;
    public static OcFlowingFluidBlock MERCURY_BLOCK;
    public static Item MERCURY_BUCKET;

    public static FlowingFluid QuicksilverFluid(){
        MERCURY = new ForgeFlowingFluid.Source(properties);
        return MERCURY;
    }

    public static FlowingFluid FlowingQuicksilverFluid(){
        FLOWING_MERCURY = new ForgeFlowingFluid.Flowing(properties);
        return FLOWING_MERCURY;
    }

    public static OcFlowingFluidBlock FlowingQuicksilverBlock(){
        MERCURY_BLOCK = new OcFlowingFluidBlock(() -> MERCURY, stdProp);
        return MERCURY_BLOCK;
    }

    public static Item QuicksilverBucket(){
        MERCURY_BUCKET = new BucketItem(() -> MERCURY, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(OCCULT_GROUP));
        return MERCURY_BUCKET;
    }


    public static final FluidType MERCURY_FLUID_TYPE = new OcFluid(FluidType.Properties.create()
            .adjacentPathType(BlockPathTypes.WATER)
            .canConvertToSource(false)
            .canDrown(true)
            .canExtinguish(false)
            .canHydrate(false)
            .canPushEntity(false)
            .canConvertToSource(false)
            .canSwim(false)
            .lightLevel(0)
            .density(1)
            .temperature(300)
            .viscosity(1)
            .motionScale(0)
            .fallDistanceModifier(0)
            .rarity(Rarity.RARE)
            .supportsBoating(false)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY),
            MERCURY_STILL_TEXTURE,
            MERCURY_FLOWING_TEXTURE
    );

    public static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> MERCURY_FLUID_TYPE, () -> MERCURY, () -> FLOWING_MERCURY)
            .block(() -> MERCURY_BLOCK).bucket(() -> MERCURY_BUCKET);

}
