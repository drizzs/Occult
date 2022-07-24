package com.drizzs.occult.api.gui.interfaces;

import com.drizzs.occult.OccultMod;
import com.drizzs.occult.api.gui.DefaultAssetProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public interface IAssetProvider {

    ResourceLocation DEFAULT_LOCATION = new ResourceLocation(OccultMod.MODID, "textures/gui/background.png");
    ResourceLocation DEFAULT_LOCATION_ALT = new ResourceLocation(OccultMod.MODID, "textures/gui/background_alt.png");

    DefaultAssetProvider DEFAULT_PROVIDER = new DefaultAssetProvider();

    @Nonnull
    static <T extends IAsset> T getAsset(IAssetProvider provider, IAssetType<T> type) {
        T asset = provider.getAsset(type);
        if (asset == null && provider != DEFAULT_PROVIDER)
            asset = DEFAULT_PROVIDER.getAsset(type);
        return asset != null ? asset : type.getDefaultAsset();
    }

    @Nullable
    <T extends IAsset> T getAsset(IAssetType<T> assetType);

}
