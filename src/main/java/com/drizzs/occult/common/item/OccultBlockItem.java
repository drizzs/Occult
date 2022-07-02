package com.drizzs.occult.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class OccultBlockItem extends BlockItem {
    public Block block;

    public OccultBlockItem(Block block, Properties prop) {
        super(block, prop);
        this.block = block;
    }

}
