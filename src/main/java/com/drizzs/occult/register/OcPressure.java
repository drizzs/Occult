package com.drizzs.occult.register;

import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.client.assets.PressureBarAsset;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.function.Supplier;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.register.OcParticles.*;

public class OcPressure {

    public static final DeferredRegister<PressureType> PRESSURE = DeferredRegister.create(new ResourceLocation(MODID,"pressure"),MODID);

    public static final Supplier<IForgeRegistry<PressureType>> REGISTRY = PRESSURE.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<PressureType> INFERNAL = PRESSURE.register("infernal",()->new PressureType(12408320,"infernal",new PressureBarAsset(new Rectangle(10,0,5,60)),new PressureBarAsset(new Rectangle(30,0,5,60)),INFERNAL_COLLECTION_PARTICLE.get(),INFERNAL_BUBBLE_PARTICLE.get()));
    public static final RegistryObject<PressureType> SPIRITUAL = PRESSURE.register("spiritual",()->new PressureType(14543359,"spiritual",new PressureBarAsset(new Rectangle(15,0,5,60)),new PressureBarAsset(new Rectangle(35,0,5,60)),SPIRITUAL_COLLECTION_PARTICLE.get(),SPIRITUAL_BUBBLE_PARTICLE.get()));
    public static final RegistryObject<PressureType> NATURAL = PRESSURE.register("natural",()->new PressureType(16384,"natural",new PressureBarAsset(new Rectangle(5,0,5,60)),new PressureBarAsset(new Rectangle(25,0,5,60)),NATURAL_COLLECTION_PARTICLE.get(),NATURAL_BUBBLE_PARTICLE.get()));
    public static final RegistryObject<PressureType> UMBRAL = PRESSURE.register("umbral",()->new PressureType(2621512,"umbral",new PressureBarAsset(new Rectangle(0,0,5,60)),new PressureBarAsset(new Rectangle(20,0,5,60)),UMBRAL_COLLECTION_PARTICLE.get(),UMBRAL_BUBBLE_PARTICLE.get()));

    public static void register(IEventBus eventBus) {
        PRESSURE.register(eventBus);
    }


}
