package com.drizzs.occult.client.addon;

import com.drizzs.occult.api.capability.IPressure;
import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.api.gui.AssetTypes.AssetTypes;
import com.drizzs.occult.api.gui.AssetUtil;
import com.drizzs.occult.api.gui.SimpleAddon;
import com.drizzs.occult.api.gui.interfaces.IAsset;
import com.drizzs.occult.api.gui.interfaces.IAssetProvider;
import com.drizzs.occult.api.gui.interfaces.IAssetType;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;

import java.awt.*;

public class PressureBarAddon extends SimpleAddon {

    private final String type;
    private final IPressure pressureStorage;
    private IAsset background;

    protected PressureBarAddon(int posX, int posY,String type ,IPressure pressureStorage) {
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

        IAssetType<IAsset> selectedAsset = AssetTypes.UMBRAL_PRESSURE_BAR_EMPTY;
        if(type.equals("infernal"))
            selectedAsset = AssetTypes.INFERNAL_PRESSURE_BAR_EMPTY;
        if(type.equals("natural"))
            selectedAsset = AssetTypes.NATURAL_PRESSURE_BAR_EMPTY;
        if(type.equals("spiritual"))
            selectedAsset = AssetTypes.SPIRITUAL_PRESSURE_BAR_EMPTY;

        background = IAssetProvider.getAsset(asset, selectedAsset);
        Point offset = background.getOffset();
        AssetUtil.drawAsset(stack, screen, background, guiX + getPosX() + offset.x, guiY + getPosY() + offset.y);
    }

    @Override
    public void drawForegroundLayer(PoseStack stack, Screen screen, IAssetProvider asset, int guiX, int guiY, int mouseX, int mouseY) {
        IAsset foreground = IAssetProvider.getAsset(asset, AssetTypes.ENERGY_BAR);
        Point offset = foreground.getOffset();
        Rectangle area = foreground.getArea();

        int stored = pressureStorage.getPressureFromType(PressureType.getTypeFromName("umbral"));
        if(type.equals("infernal"))
            stored = pressureStorage.getPressureFromType(PressureType.getTypeFromName("umbral"));
        if(type.equals("natural"))
            stored = pressureStorage.getPressureFromType(PressureType.getTypeFromName("umbral"));
        if(type.equals("spiritual"))
            stored = pressureStorage.getPressureFromType(PressureType.getTypeFromName("umbral"));


        screen.getMinecraft().getTextureManager().bindForSetup(foreground.getResourceLocation());
        int powerOffset = (int) ((stored / Math.max(pressureStorage.getPressureCapacity(), 1)) * area.height);
        screen.blit(stack, getPosX() + offset.x, getPosY() + offset.y + area.height - powerOffset, area.x, area.y + (area.height - powerOffset), area.width, powerOffset);
    }
}
