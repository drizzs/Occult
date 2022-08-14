package com.drizzs.occult.register;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.OccultMod.MODID;

public class OcParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<SimpleParticleType> INFERNAL_COLLECTION_PARTICLE = PARTICLE_TYPE.register("infernal_collection_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPIRITUAL_COLLECTION_PARTICLE = PARTICLE_TYPE.register("spiritual_collection_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> NATURAL_COLLECTION_PARTICLE = PARTICLE_TYPE.register("natural_collection_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> UMBRAL_COLLECTION_PARTICLE = PARTICLE_TYPE.register("umbral_collection_particle",()-> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> INFERNAL_BUBBLE_PARTICLE = PARTICLE_TYPE.register("infernal_bubble_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPIRITUAL_BUBBLE_PARTICLE = PARTICLE_TYPE.register("spiritual_bubble_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> NATURAL_BUBBLE_PARTICLE = PARTICLE_TYPE.register("natural_bubble_particle",()-> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> UMBRAL_BUBBLE_PARTICLE = PARTICLE_TYPE.register("umbral_bubble_particle",()-> new SimpleParticleType(true));


    public static void register(IEventBus eventBus) {
        PARTICLE_TYPE.register(eventBus);
    }
}
