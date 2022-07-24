package com.drizzs.occult.api.gui.interfaces;

import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public interface IAsset {

    default ResourceLocation getResourceLocation() {
        return IAssetProvider.DEFAULT_LOCATION;
    }

    Rectangle getArea();

    default Point getOffset() {
        return new Point();
    }

}
