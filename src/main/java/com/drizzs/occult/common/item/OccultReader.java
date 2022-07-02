package com.drizzs.occult.common.item;

import com.drizzs.occult.api.PressureType;
import com.drizzs.occult.api.capability.PressureCap;
import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;
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

        for(PressureType pt : pressure.keySet()){
            player.displayClientMessage(Component.translatable(pt.toString() + pressure.getInt(pt)),true);
        }
        return super.use(level, player, hand);
    }
}

