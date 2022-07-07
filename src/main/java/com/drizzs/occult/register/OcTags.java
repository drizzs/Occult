package com.drizzs.occult.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.drizzs.occult.OccultMod.MODID;

public class OcTags {

    public static final TagKey<Item> SIZE = ItemTags.create(new ResourceLocation("occult:sizecartridge"));
    public static void createTags(){
        for(int i = 1; i < 100;++i){

            ItemTags.create(new ResourceLocation(MODID,"infernal_" + i));
            ItemTags.create(new ResourceLocation(MODID,"spiritual_" + i));
            ItemTags.create(new ResourceLocation(MODID,"natural_" + i));
            ItemTags.create(new ResourceLocation(MODID,"umbral_" + i));
        }
    }
}
