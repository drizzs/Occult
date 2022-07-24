package com.drizzs.occult.api.gui.interfaces;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;

public interface IAddon {

    void drawBackgroundLayer(PoseStack stack, Screen screen, IAssetProvider asset, int guiX, int guiY, int mouseX, int mouseY, float partialTicks);

    void drawForegroundLayer(PoseStack stack, Screen screen, IAssetProvider asset, int guiX, int guiY, int mouseX, int mouseY);

    default List<Component> getTooltipLines() {
        return Collections.emptyList();
    }

    boolean isInside(Screen screen, double mouseX, double mouseY);

    default boolean keyPressed(int key, int scan, int modifiers) {
        return false;
    }

    default void init(int screenX, int screenY) {

    }

    default boolean isBackground() {
        return false;
    }

}
