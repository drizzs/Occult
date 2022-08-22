package com.drizzs.occult.common.network;

import com.drizzs.occult.OccultMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static com.drizzs.occult.OccultMod.MODID;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private PacketHandler() {
    }

    public static void init() {
        int index = 0;
        INSTANCE.messageBuilder(PressureGatheringPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(PressureGatheringPacket::encode).decoder(PressureGatheringPacket::new)
                .consumerMainThread(PressureGatheringPacket::handle).add();
        INSTANCE.messageBuilder(PressureReceivingPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(PressureReceivingPacket::encode).decoder(PressureReceivingPacket::new)
                .consumerMainThread(PressureReceivingPacket::handle).add();
        INSTANCE.messageBuilder(CrucibleClientSyncPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(CrucibleClientSyncPacket::encode).decoder(CrucibleClientSyncPacket::new)
                .consumerMainThread(CrucibleClientSyncPacket::handle).add();
        OccultMod.LOGGER.info("Registered {} packets for mod '{}'", index, MODID);
    }

}
