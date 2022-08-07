package com.drizzs.occult.register;

import com.drizzs.occult.common.blockentity.test.CrusherBE;
import com.drizzs.occult.common.blockentity.RitualFireBlockEntity;
import com.drizzs.occult.common.blockentity.test.pce.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.register.OcBlocks.*;

public class OcBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);

    public static final RegistryObject<BlockEntityType<?>> RITUAL_FIRE_BE = BLOCK_ENTITY_TYPE.register("ritual_fire",
            () -> BlockEntityType.Builder.of(RitualFireBlockEntity::new, RITUAL_FIRE.get()).build(null));

    public static final RegistryObject<BlockEntityType<?>> PRESSURE_COLLECTOR1_BE = BLOCK_ENTITY_TYPE.register("pressure_collector1",
            () -> BlockEntityType.Builder.of(WeakPressureCollectorBE::new, PRESSURE_COLLECTOR1.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> PRESSURE_COLLECTOR2_BE = BLOCK_ENTITY_TYPE.register("pressure_collector2",
            () -> BlockEntityType.Builder.of(AveragePressureCollectorBE::new, PRESSURE_COLLECTOR2.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> PRESSURE_COLLECTOR3_BE = BLOCK_ENTITY_TYPE.register("pressure_collector3",
            () -> BlockEntityType.Builder.of(StrongPressureCollectorBE::new, PRESSURE_COLLECTOR3.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> PRESSURE_COLLECTOR4_BE = BLOCK_ENTITY_TYPE.register("pressure_collector4",
            () -> BlockEntityType.Builder.of(SuperStrongPressureCollectorBE::new, PRESSURE_COLLECTOR4.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> PRESSURE_COLLECTOR5_BE = BLOCK_ENTITY_TYPE.register("pressure_collector5",
            () -> BlockEntityType.Builder.of(InfinitePressureCollectorBE::new, PRESSURE_COLLECTOR5.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> CRUSHER_BE = BLOCK_ENTITY_TYPE.register("crusher",
            () -> BlockEntityType.Builder.of(CrusherBE::new, CRUSHER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPE.register(eventBus);
    }

}
