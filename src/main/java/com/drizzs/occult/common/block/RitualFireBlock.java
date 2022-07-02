package com.drizzs.occult.common.block;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class RitualFireBlock extends Block {

    public RitualFireBlock() {
        super(Block.Properties.of(Material.WOOD).color(MaterialColor.COLOR_BLUE).lightLevel(value -> 5));
    }

}
