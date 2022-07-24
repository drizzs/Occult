package com.drizzs.occult.api.gui.interfaces;

public interface IAssetType<T extends IAsset> {

    T getDefaultAsset();

    T castOrDefault(IAsset asset) throws ClassCastException;
}
