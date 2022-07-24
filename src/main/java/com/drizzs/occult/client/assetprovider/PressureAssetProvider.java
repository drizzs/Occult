package com.drizzs.occult.client.assetprovider;

import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.api.gui.AssetTypes.AssetTypes;
import com.drizzs.occult.api.gui.DefaultAssetProvider;
import com.drizzs.occult.api.gui.interfaces.IAsset;
import com.drizzs.occult.api.gui.interfaces.IAssetProvider;
import com.drizzs.occult.api.gui.interfaces.IAssetType;
import org.jetbrains.annotations.Nullable;

public class PressureAssetProvider extends DefaultAssetProvider {

    public PressureAssetProvider(){

    }

    @Override
    public <T extends IAsset> @Nullable T getAsset(IAssetType<T> assetType) {
        if (assetType == AssetTypes.UMBRAL_PRESSURE_BAR_EMPTY)
            return assetType.castOrDefault(PressureType.getTypeFromName("umbral").getProgressBarEmpty());
        else if (assetType == AssetTypes.UMBRAL_PRESSURE_BAR_FULL)
            return assetType.castOrDefault(PressureType.getTypeFromName("umbral").getProgressBarFull());
        else if (assetType == AssetTypes.NATURAL_PRESSURE_BAR_EMPTY)
            return assetType.castOrDefault(PressureType.getTypeFromName("natural").getProgressBarEmpty());
        else if (assetType == AssetTypes.NATURAL_PRESSURE_BAR_FULL)
            return assetType.castOrDefault(PressureType.getTypeFromName("natural").getProgressBarFull());
        else if (assetType == AssetTypes.INFERNAL_PRESSURE_BAR_EMPTY)
            return assetType.castOrDefault(PressureType.getTypeFromName("infernal").getProgressBarEmpty());
        else if (assetType == AssetTypes.INFERNAL_PRESSURE_BAR_FULL)
            return assetType.castOrDefault(PressureType.getTypeFromName("infernal").getProgressBarFull());
        else if (assetType == AssetTypes.SPIRITUAL_PRESSURE_BAR_EMPTY)
            return assetType.castOrDefault(PressureType.getTypeFromName("spiritual").getProgressBarEmpty());
        else if (assetType == AssetTypes.SPIRITUAL_PRESSURE_BAR_FULL)
            return assetType.castOrDefault(PressureType.getTypeFromName("spiritual").getProgressBarFull());
        else
            return super.getAsset(assetType);
    }


}
