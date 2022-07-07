package com.drizzs.occult.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class PressureCollectorBlock extends Block {

    public PressureCollectorBlock() {
        super(Block.Properties.of(Material.METAL).color(MaterialColor.COLOR_GRAY).destroyTime(70));
    }


}
