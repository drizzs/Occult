package com.drizzs.occult.api.gui.interfaces;

import net.minecraft.client.gui.screens.Screen;

public interface IClickable {
    void handleClick(Screen screen, int guiX, int guiY, double mouseX, double mouseY, int button);

}
