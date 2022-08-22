package com.drizzs.occult.common.network;

import com.drizzs.occult.common.blockentity.CrucibleBE;
import com.drizzs.occult.common.container.base.BaseContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Supplier;

public class CrucibleClientSyncPacket {

    private final FluidStack crucibleFluid;
    private final ItemStackHandler itemHandler;
    private final BlockPos pos;

    public CrucibleClientSyncPacket(CrucibleBE be){
        this.crucibleFluid = be.getFluidStack();
        this.itemHandler = be.inventory;
        this.pos = be.getBlockPos();
    }

    public CrucibleClientSyncPacket(FriendlyByteBuf buffer) {
        CompoundTag tag = buffer.readNbt();
        assert tag != null;
        crucibleFluid = FluidStack.loadFluidStackFromNBT(tag);
        itemHandler = new ItemStackHandler();
        itemHandler.deserializeNBT(tag.getCompound("Inventory"));
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf){
        CompoundTag tag = new CompoundTag();

        crucibleFluid.writeToNBT(tag);
        tag.put("Inventory", itemHandler.serializeNBT());

        buf.writeNbt(tag);
        buf.writeBlockPos(pos);

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                assert Minecraft.getInstance().level != null;
                BlockEntity entity = Minecraft.getInstance().level.getBlockEntity(pos);

               if(entity instanceof CrucibleBE be){

                   be.setFluidStack(crucibleFluid);
                   be.inventory = itemHandler;

               }
            });
        }
    }


}
