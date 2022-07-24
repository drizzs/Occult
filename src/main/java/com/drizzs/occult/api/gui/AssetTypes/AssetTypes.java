package com.drizzs.occult.api.gui.AssetTypes;

import com.drizzs.occult.api.gui.interfaces.*;
import com.drizzs.occult.client.assetprovider.PressureAssetProvider;

public class AssetTypes {

    static PressureAssetProvider pressureAssetProvider = new PressureAssetProvider();

    public static final IAssetType<IAsset> BUTTON_SIDENESS_DISABLED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_ENABLED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_PULL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_PUSH = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<ITankAsset> TANK_NORMAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, ITankAsset.class);
    public static final IAssetType<ITankAsset> TANK_SMALL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, ITankAsset.class);
    public static final IAssetType<IBackgroundAsset> BACKGROUND = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IBackgroundAsset.class);
    public static final IAssetType<IAsset> SLOT = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> ENERGY_BAR = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> ENERGY_BACKGROUND = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BORDER_VERTICAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_VERTICAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BACKGROUND_VERTICAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BACKGROUND_ARROW_DOWN = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_ARROW_DOWN = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_SIDENESS_MANAGER = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> AUGMENT_BACKGROUND = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> AUGMENT_BACKGROUND_LEFT = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> AUGMENT_BACKGROUND_LEFT_TALL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_ARROW_LEFT = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_ARROW_RIGHT = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_ARROW_UP = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_ARROW_DOWN = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> ITEM_BACKGROUND = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> TEXT_FIELD_ACTIVE = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> TEXT_FIELD_INACTIVE = new GenericAssetType<>(IAssetType::getDefaultAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_REDSTONE_ONCE = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_REDSTONE_REDSTONE = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_REDSTONE_NO_REDSTONE = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_REDSTONE_IGNORED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> PROGRESS_BAR_ARROW_HORIZONTAL = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> SHADE_PICKER = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> HUE_PICKER = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_LOCKED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);
    public static final IAssetType<IAsset> BUTTON_UNLOCKED = new GenericAssetType<>(IAssetProvider.DEFAULT_PROVIDER::getAsset, IAsset.class);

    public static final IAssetType<IAsset> UMBRAL_PRESSURE_BAR_EMPTY = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> UMBRAL_PRESSURE_BAR_FULL = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> INFERNAL_PRESSURE_BAR_EMPTY = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> INFERNAL_PRESSURE_BAR_FULL = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> NATURAL_PRESSURE_BAR_EMPTY = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> NATURAL_PRESSURE_BAR_FULL = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> SPIRITUAL_PRESSURE_BAR_EMPTY = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

    public static final IAssetType<IAsset> SPIRITUAL_PRESSURE_BAR_FULL = new GenericAssetType<>(pressureAssetProvider::getAsset, IAsset.class);

}
