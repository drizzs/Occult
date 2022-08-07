package com.drizzs.occult;

import com.drizzs.occult.api.capability.PressureAttach;
import com.drizzs.occult.register.*;
import com.drizzs.occult.util.OcConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(OccultMod.MODID)
public class OccultMod
{
    public static final String MODID = "occult";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OccultMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OcConfig.GENERAL_SPEC, "occult/general.toml");
        OcBlocks.register(modEventBus);
        OcItems.register(modEventBus);
        OcBlockEntities.register(modEventBus);
        OcContainers.register(modEventBus);
        OcParticles.register(modEventBus);
        OcPressure.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.addGenericListener(LevelChunk.class, PressureAttach::attachChunk);
        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, PressureAttach::attachBlockEntity);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ItemBlockRenderTypes.setRenderLayer(OcBlocks.RITUAL_FIRE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(OcBlocks.PRESSURE_COLLECTOR1.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(OcBlocks.PRESSURE_COLLECTOR2.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(OcBlocks.PRESSURE_COLLECTOR3.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(OcBlocks.PRESSURE_COLLECTOR4.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(OcBlocks.PRESSURE_COLLECTOR5.get(), RenderType.cutout());
        }
    }
}
