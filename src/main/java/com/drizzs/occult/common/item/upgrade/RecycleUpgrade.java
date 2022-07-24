package com.drizzs.occult.common.item.upgrade;

import net.minecraft.world.item.Item;

public class RecycleUpgrade extends Item {

    private final int speed;

    public RecycleUpgrade(Properties properties, int speed) {
        super(properties);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
