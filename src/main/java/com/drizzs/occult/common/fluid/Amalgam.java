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

public class Amalgam {

    public static final ResourceLocation AMALGAM_STILL_TEXTURE = new ResourceLocation(MODID,"blocks/fluids/amalgam_still");
    public static final ResourceLocation AMALGAM_FLOWING_TEXTURE = new ResourceLocation(MODID,"blocks/fluids/amalgam_flowing");

    public static Block.Properties stdProp = Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable();

    public static FlowingFluid AMALGAM;
    public static FlowingFluid FLOWING_AMALGAM;
    public static OcFlowingFluidBlock AMALGAM_BLOCK;
    public static Item AMALGAM_BUCKET;

    public static FlowingFluid QuicksilverFluid(){
        AMALGAM = new ForgeFlowingFluid.Source(properties);
        return AMALGAM;
    }

    public static FlowingFluid FlowingQuicksilverFluid(){
        FLOWING_AMALGAM = new ForgeFlowingFluid.Flowing(properties);
        return FLOWING_AMALGAM;
    }

    public static OcFlowingFluidBlock FlowingQuicksilverBlock(){
        AMALGAM_BLOCK = new OcFlowingFluidBlock(() -> AMALGAM, stdProp);
        return AMALGAM_BLOCK;
    }

    public static Item QuicksilverBucket(){
        AMALGAM_BUCKET = new BucketItem(() -> AMALGAM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(OCCULT_GROUP));
        return AMALGAM_BUCKET;
    }


    public static final FluidType AMALGAM_FLUID_TYPE = new OcFluid(FluidType.Properties.create()
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
            AMALGAM_STILL_TEXTURE,
            AMALGAM_FLOWING_TEXTURE
    );

    public static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> AMALGAM_FLUID_TYPE, () -> AMALGAM, () -> FLOWING_AMALGAM)
            .block(() -> AMALGAM_BLOCK).bucket(() -> AMALGAM_BUCKET);

}
