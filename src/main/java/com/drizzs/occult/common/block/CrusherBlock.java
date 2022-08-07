package com.drizzs.occult.common.block;

import com.drizzs.occult.common.block.base.BaseEntityBlock;
import com.drizzs.occult.common.blockentity.test.CrusherBE;
import com.drizzs.occult.common.blockentity.test.base.IPressureConsumer;
import com.drizzs.occult.common.container.CrusherContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.register.OcBlockEntities.CRUSHER_BE;

public class CrusherBlock extends BaseEntityBlock implements IPressureConsumer {

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return CRUSHER_BE.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null
                : (level0, pos, state0, blockEntity) -> ((CrusherBE) blockEntity).tick();
    }

    @Override
    public InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, BlockHitResult trace) {

        BlockEntity entity = world.getBlockEntity(pos);

        InteractionResult result = InteractionResult.PASS;


        if(!world.isClientSide() && entity instanceof CrusherBE ie){
                MenuProvider container = new SimpleMenuProvider(CrusherContainer.getServerContainer(ie, pos),
                        Component.translatable("container.occult.crusher"));
                NetworkHooks.openGui((ServerPlayer) player, container, pos);
                result = InteractionResult.SUCCESS;
        }

        return result;
    }
}
