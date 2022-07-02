package com.drizzs.occult.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class OcTags {

    public static final TagKey<Item> SIZE = ItemTags.create(new ResourceLocation("occult:sizecartridge"));

    static{
        for(int i = 1; i < 100;++i){

            BlockTags.create(new ResourceLocation("occult:infernus_" + i));
            BlockTags.create(new ResourceLocation("occult:spiritual_" + i));
            BlockTags.create(new ResourceLocation("occult:natural_" + i));
            BlockTags.create(new ResourceLocation("occult:umbral_" + i));
        }
    }
}
