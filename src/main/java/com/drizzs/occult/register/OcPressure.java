package com.drizzs.occult.register;

import com.drizzs.occult.OccultMod;
import com.drizzs.occult.api.PressureType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.drizzs.occult.OccultMod.MODID;

public class OcPressure {

    public static final DeferredRegister<PressureType> PRESSURE = DeferredRegister.create(new ResourceLocation(MODID,"pressure"),MODID);

    public static final Supplier<IForgeRegistry<PressureType>> REGISTRY = PRESSURE.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<PressureType> INFERNAL = PRESSURE.register("infernal",()->new PressureType(12408320));
    public static final RegistryObject<PressureType> SPIRITUAL = PRESSURE.register("spiritual",()->new PressureType(14543359));
    public static final RegistryObject<PressureType> NATURAL = PRESSURE.register("natural",()->new PressureType(16384));
    public static final RegistryObject<PressureType> UMBRAL = PRESSURE.register("umbral",()->new PressureType(2621512));

    public static void register(IEventBus eventBus) {
        PRESSURE.register(eventBus);
        OccultMod.LOGGER.info("Pressure Registered");
    }


}
