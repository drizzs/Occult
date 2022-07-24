package com.drizzs.occult.common.item.upgrade;

import net.minecraft.world.item.Item;

public class CollectionUpgrade extends Item {

    private final int collectionSpeed;

    public CollectionUpgrade(Properties properties, int collectionSpeed) {
        super(properties);
        this.collectionSpeed = collectionSpeed;
    }

    public int getCollectionSpeed() {
        return collectionSpeed;
    }
}
