package com.drizzs.occult.client;

import com.drizzs.occult.client.renderer.CrucibleBERenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.register.OcBlockEntities.CRUCIBLE_BE;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        //MenuScreens.register(CRUSHER_CONTAINER.get(), CrusherScreen::new);

    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers e){
            e.registerBlockEntityRenderer(CRUCIBLE_BE.get(), context -> new CrucibleBERenderer());
    }

}
