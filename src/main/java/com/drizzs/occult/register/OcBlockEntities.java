package com.drizzs.occult.register;

import com.drizzs.occult.common.blockentity.PressureCollectorBlockEntity;
import com.drizzs.occult.common.blockentity.RitualFireBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.register.OcBlocks.PRESSURE_COLLECTOR;
import static com.drizzs.occult.register.OcBlocks.RITUAL_FIRE;
import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class OcBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

    public static final RegistryObject<BlockEntityType<?>> RITUAL_FIRE_BE = BLOCK_ENTITY_TYPE.register("ritual_fire",
            () -> BlockEntityType.Builder.of(RitualFireBlockEntity::new, RITUAL_FIRE.get()).build(null));

    public static final RegistryObject<BlockEntityType<?>> PRESSURE_COLLECTOR_BE = BLOCK_ENTITY_TYPE.register("pressure_collector",
            () -> BlockEntityType.Builder.of(PressureCollectorBlockEntity::new, PRESSURE_COLLECTOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPE.register(eventBus);
    }

}
