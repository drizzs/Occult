package com.drizzs.occult.common.item;

import com.drizzs.occult.api.PressureType;
import com.drizzs.occult.api.capability.PressureCap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

public class OccultReader extends Item {

    public OccultReader(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
       if(!level.isClientSide()) {
           LevelChunk chunk = level.getChunkAt(player.getOnPos());
            Object2IntMap<PressureType> pressure = PressureCap.getAllPressureFromChunk(chunk);
            if (!pressure.isEmpty()) {
                for (PressureType pt : pressure.keySet()) {
                    player.sendSystemMessage(Component.translatable(pt.getId() + ": " + pressure.getInt(pt)));
                }
            } else {
                player.sendSystemMessage(Component.translatable("No Pressure Found"));
          }
        }
        return super.use(level, player, hand);
    }
}

