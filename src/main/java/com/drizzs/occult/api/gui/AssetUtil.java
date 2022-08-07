package com.drizzs.occult.api.gui;

import com.drizzs.occult.api.gui.interfaces.IAsset;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import java.awt.*;

public class AssetUtil {

    public static void drawAsset(PoseStack stack, Screen screen, IAsset asset, int xPos, int yPos) {
        Point offset = asset.getOffset();
        Rectangle area = asset.getArea();
        RenderSystem.setShaderTexture(0,asset.getResourceLocation());
        screen.blit(stack, xPos + offset.x,
                yPos + offset.y,
                area.x,
                area.y,
                area.width,
                area.height);
    }

    public static void drawSelectingOverlay(PoseStack stack, int x, int y, int width, int height) {
        AbstractContainerScreen.fill(stack, x, y, width, height, -2130706433);
    }

    public static void drawHorizontalLine(PoseStack stack, int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        AbstractContainerScreen.fill(stack, startX, y, endX + 1, y + 1, color);
    }

    public static void drawVerticalLine(PoseStack stack, int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }
        AbstractContainerScreen.fill(stack, x, startY + 1, x + 1, endY, color);
    }

}
