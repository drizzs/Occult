package com.drizzs.occult.register;

import com.drizzs.occult.common.fluid.Quicksilver;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.register.OcBlocks.BLOCKS;
import static com.drizzs.occult.register.OcItems.ITEMS;

public class OcFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MODID);

    public static RegistryObject<FlowingFluid> QUICKSILVER = FLUIDS.register("quicksilver",
            Quicksilver::QuicksilverFluid);
    public static RegistryObject<FlowingFluid> FLOWING_QUICKSILVER = FLUIDS.register("flowing_quicksilver",
            Quicksilver::FlowingQuicksilverFluid);
    public static RegistryObject<LiquidBlock> FLOWING_QUICKSILVER_BLOCK = BLOCKS.register("quicksilver_block",
            Quicksilver::FlowingQuicksilverBlock);
    public static RegistryObject<Item> QUICKSILVER_BUCKET = ITEMS.register("quicksilver_bucket",
            Quicksilver::QuicksilverBucket);
    public static RegistryObject<FluidType> QUICKSILVER_TYPE = FLUID_TYPES.register("quicksilver",
            () -> Quicksilver.MERCURY_FLUID_TYPE);

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

}
