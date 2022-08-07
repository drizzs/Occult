package com.drizzs.occult.common.block.base;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public abstract class BaseEntityBlock extends Block implements EntityBlock {

    public BaseEntityBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(5,6).requiresCorrectToolForDrops().sound(SoundType.METAL));
    }


}
