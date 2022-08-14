package com.drizzs.occult.common.network;

import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.api.capability.PressureStorage;
import com.drizzs.occult.common.container.base.BaseContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PressureReceivingPacket {

    private final IPressure pressure;

    public PressureReceivingPacket(IPressure pressure){
        this.pressure = pressure;
    }

    public PressureReceivingPacket(FriendlyByteBuf buffer) {
        pressure = new PressureStorage();
        pressure.deserializeNBT(buffer.readNbt());
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeNbt(pressure.serializeNBT());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            ctx.get().enqueueWork(() -> {
                Player player = Minecraft.getInstance().player;

                if(player.containerMenu instanceof BaseContainer fc){
                    fc.setPressure(pressure);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }

}
