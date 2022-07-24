package com.drizzs.occult.api.gui.interfaces;

import javax.annotation.Nonnull;

public interface IFactory<T> {
    @Nonnull
    T create();
}