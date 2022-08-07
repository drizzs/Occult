package com.drizzs.occult.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.drizzs.occult.OccultMod.MODID;

public class OcTags {

    public static final TagKey<Item> SIZE = ItemTags.create(new ResourceLocation("occult:sizecartridge"));
    public static final TagKey<Item> SPEED_UPGRADE = ItemTags.create(new ResourceLocation("occult:speed_upgrade"));
    public static final TagKey<Item> POWER_COLLECTION_UPGRADE = ItemTags.create(new ResourceLocation("occult:power_collection_upgrade"));
    public static final TagKey<Item> RECYCLE_UPGRADE = ItemTags.create(new ResourceLocation("occult:recycle_upgrade"));
    public static final TagKey<Item> POWER_STORAGE_UPGRADE = ItemTags.create(new ResourceLocation("occult:power_storage_upgrade"));

    public static void createTags(){
        for(int i = 1; i < 100;++i){

            ItemTags.create(new ResourceLocation(MODID,"infernal_" + i));
            ItemTags.create(new ResourceLocation(MODID,"spiritual_" + i));
            ItemTags.create(new ResourceLocation(MODID,"natural_" + i));
            ItemTags.create(new ResourceLocation(MODID,"umbral_" + i));
        }
    }
}
