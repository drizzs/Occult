package com.drizzs.occult.common;

import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.client.particle.PressureBubbleParticle;
import com.drizzs.occult.client.particle.PressureGatheringParticle;
import com.drizzs.occult.common.network.PacketHandler;
import com.drizzs.occult.common.recipe.crucible.CrucibleCookingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.NewRegistryEvent;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.register.OcParticles.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvent {

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IPressure.class);
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::init);
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
        Minecraft.getInstance().particleEngine.register(INFERNAL_COLLECTION_PARTICLE.get(), PressureGatheringParticle.InfernalProvider::new);
        Minecraft.getInstance().particleEngine.register(SPIRITUAL_COLLECTION_PARTICLE.get(), PressureGatheringParticle.SpiritualProvider::new);
        Minecraft.getInstance().particleEngine.register(NATURAL_COLLECTION_PARTICLE.get(), PressureGatheringParticle.NaturalProvider::new);
        Minecraft.getInstance().particleEngine.register(UMBRAL_COLLECTION_PARTICLE.get(), PressureGatheringParticle.UmbralProvider::new);

        Minecraft.getInstance().particleEngine.register(INFERNAL_BUBBLE_PARTICLE.get(), PressureBubbleParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(SPIRITUAL_BUBBLE_PARTICLE.get(), PressureBubbleParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(NATURAL_BUBBLE_PARTICLE.get(), PressureBubbleParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(UMBRAL_BUBBLE_PARTICLE.get(), PressureBubbleParticle.Provider::new);
    }

}
