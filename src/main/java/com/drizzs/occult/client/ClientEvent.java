package com.drizzs.occult.client;

import com.drizzs.occult.client.screen.CrusherScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.register.OcContainers.CRUSHER_CONTAINER;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(CRUSHER_CONTAINER.get(), CrusherScreen::new);

    }

}
