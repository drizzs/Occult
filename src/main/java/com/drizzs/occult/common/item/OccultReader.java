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

public class OccultReader extends Item {

    public OccultReader(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        Object2IntMap<PressureType> pressure = PressureCap.getAllPressureFromChunk(level.getChunk((int)player.getX(),(int)player.getY()));
        if(!pressure.isEmpty()) {
            for (PressureType pt : pressure.keySet()) {
                player.displayClientMessage(Component.translatable(pt.toString() + pressure.getInt(pt)), true);
            }
        }else{
            player.displayClientMessage(Component.translatable("No Pressure Found"), true);
        }
        return super.use(level, player, hand);
    }
}

