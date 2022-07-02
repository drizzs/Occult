package com.drizzs.occult.common;

import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.api.capability.PressureAttach;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.drizzs.occult.OccultMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvent {

    @SubscribeEvent
    public void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(IPressure.class);
    }

}
