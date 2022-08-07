package com.drizzs.occult.client.addon;

import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.api.gui.AssetUtil;
import com.drizzs.occult.api.gui.SimpleAddon;
import com.drizzs.occult.api.gui.interfaces.IAsset;
import com.drizzs.occult.api.gui.interfaces.IAssetProvider;
import com.drizzs.occult.common.container.CrusherContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;


public class PressureBarAddon extends SimpleAddon {
    private final String type;
    private final IPressure pressureStorage;
    private IAsset background;

    public PressureBarAddon(int posX, int posY, String type, IPressure pressureStorage) {
        super(posX, posY);
        this.type = type;
        this.pressureStorage = pressureStorage;
    }

    @Override
    public int getXSize() {
        return background != null ? background.getArea().width:0;
    }

    @Override
    public int getYSize() {
        return background != null ? background.getArea().width:0;
    }

    @Override
    public void drawBackgroundLayer(PoseStack stack, Screen screen, IAssetProvider asset, int guiX, int guiY, int mouseX, int mouseY, float partialTicks) {

        background = PressureType.getTypeFromName(type).getProgressBarEmpty();
        Point offset = background.getOffset();
        AssetUtil.drawAsset(stack, screen, background, guiX + getPosX() + offset.x, guiY + getPosY() + offset.y);
    }

    @Override
    public void drawForegroundLayer(PoseStack stack, Screen screen, IAssetProvider asset, int guiX, int guiY, int mouseX, int mouseY) {
        IAsset foreground = PressureType.getTypeFromName(type).getProgressBarFull();
        Point offset = foreground.getOffset();
        Rectangle area = foreground.getArea();
        AbstractContainerMenu menu = ((AbstractContainerScreen<?>) screen).getMenu();
        if (menu instanceof CrusherContainer cc) {
            IPressure pressure = cc.getPressure();
            if (pressure != null) {
                int stored = pressure.getPressureFromType(PressureType.getTypeFromName(type));

                RenderSystem.setShaderTexture(0, foreground.getResourceLocation());
                int capacity = pressure.getPressureCapacity();
                float offsetCap = (float)stored / capacity;
                int powerOffset = (int) (area.height * offsetCap);
                screen.blit(stack, guiX + getPosX() + offset.x, guiY + getPosY() + offset.y + area.height - powerOffset, area.x, area.y + (area.height - powerOffset), area.width, powerOffset);
            }
        }
    }

    public List<Component> getTooltip(int stored, int capacity) {
        return Arrays.asList(Component.translatable(ChatFormatting.GOLD + "Pressure:"), Component.translatable(new DecimalFormat().format(stored) + ChatFormatting.GOLD + "/" + ChatFormatting.WHITE + new DecimalFormat().format(capacity) + ChatFormatting.DARK_AQUA + type));
    }

    @Override
    public java.util.List<Component> getTooltipLines() {
        int stored = pressureStorage.getPressureFromType(PressureType.getTypeFromName(type));
        return getTooltip(stored, pressureStorage.getPressureCapacity());
    }
}
