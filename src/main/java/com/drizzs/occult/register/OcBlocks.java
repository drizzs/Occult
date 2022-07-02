package com.drizzs.occult.register;

import com.drizzs.occult.common.item.OccultBlockItem;
import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.common.OccultGroup.OCCULT_GROUP;
import static com.drizzs.occult.register.OcItems.ITEMS;

public class OcBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static Map<RegistryObject<Block>, String> blocklist = Maps.newHashMap();

    public static void register(IEventBus eventBus) {

        for (RegistryObject<Block> block : blocklist.keySet()) {
            ITEMS.register(blocklist.get(block), () -> new OccultBlockItem(block.get(), new Item.Properties().tab(OCCULT_GROUP)));
        }
        BLOCKS.register(eventBus);
    }

}