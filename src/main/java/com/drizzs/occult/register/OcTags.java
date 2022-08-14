package com.drizzs.occult.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static com.drizzs.occult.OccultMod.MODID;

public class OcTags {

    public static final TagKey<Item> SPEED_UPGRADE = ItemTags.create(new ResourceLocation("occult:speed_upgrade"));
    public static final TagKey<Item> POWER_COLLECTION_UPGRADE = ItemTags.create(new ResourceLocation("occult:power_collection_upgrade"));
    public static final TagKey<Item> RECYCLE_UPGRADE = ItemTags.create(new ResourceLocation("occult:recycle_upgrade"));
    public static final TagKey<Item> POWER_STORAGE_UPGRADE = ItemTags.create(new ResourceLocation("occult:power_storage_upgrade"));

    public static final TagKey<Block> LOW_HEAT = BlockTags.create(new ResourceLocation(MODID,"heat/low_heat"));
    public static final TagKey<Block> MEDIUM_HEAT = BlockTags.create(new ResourceLocation(MODID,"heat/medium_heat"));
    public static final TagKey<Block> HIGH_HEAT = BlockTags.create(new ResourceLocation(MODID,"heat/high_heat"));
    public static final TagKey<Block> EXTREME_HEAT = BlockTags.create(new ResourceLocation(MODID,"heat/extreme_heat"));
    public static final TagKey<Block> INFERNAL_HEAT = BlockTags.create(new ResourceLocation(MODID,"heat/internal_heat"));

}
