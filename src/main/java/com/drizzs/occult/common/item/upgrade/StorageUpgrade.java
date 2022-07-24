package com.drizzs.occult.common.item.upgrade;

import net.minecraft.world.item.Item;

public class StorageUpgrade extends Item {

    private final int speed;

    public StorageUpgrade(Properties properties, int speed) {
        super(properties);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
