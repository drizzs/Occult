package com.drizzs.occult.common.network;

import com.drizzs.occult.api.capability.PressureCap;
import com.drizzs.occult.api.capability.PressureStorage;
import com.drizzs.occult.common.blockentity.test.base.BasePressureMachine;
import com.drizzs.occult.common.container.base.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PressureGatheringPacket {

    public PressureGatheringPacket(){
    }

    public PressureGatheringPacket(FriendlyByteBuf buffer) {

    }

    public void encode(FriendlyByteBuf buf){

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide() == LogicalSide.SERVER) {
            ctx.get().enqueueWork(() -> {
                Player player = ctx.get().getSender();

                if(player.containerMenu instanceof BaseContainer fc){
                    assert player != null;
                   // BlockEntity te = player.level.getBlockEntity(fc.getPos());

                   // if(te instanceof BasePressureMachine bpm){
                   //     PacketHandler.INSTANCE.sendTo(new PressureReceivingPacket(PressureCap.getTileEntityPressure(bpm).orElse(new PressureStorage())), ctx.get().getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                  //  }


                }
            });
        }
        ctx.get().setPacketHandled(true);
    }

}
