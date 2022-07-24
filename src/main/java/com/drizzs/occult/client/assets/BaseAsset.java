package com.drizzs.occult.client.assets;

import com.drizzs.occult.api.gui.interfaces.IAsset;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public abstract class BaseAsset implements IAsset {

    private final Rectangle rectangle;
    private final ResourceLocation assetLocation;

    public BaseAsset(Rectangle rectangle,ResourceLocation assetLocation){
        this.rectangle = rectangle;
        this.assetLocation = assetLocation;
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return assetLocation;
    }

    @Override
    public Rectangle getArea() {
        return rectangle;
    }

}
