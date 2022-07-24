package com.drizzs.occult.api.gui;


import com.drizzs.occult.api.gui.interfaces.IAddon;
import net.minecraft.client.gui.screens.Screen;

public abstract class SimpleAddon implements IAddon {

    private int posX;
    private int posY;

    protected SimpleAddon(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public boolean isInside(Screen container, double mouseX, double mouseY) {
        return mouseX > this.getPosX() && mouseX < this.getPosX() + getXSize() && mouseY > this.getPosY() && mouseY < this.getPosY() + getYSize();
    }

    public abstract int getXSize();

    public abstract int getYSize();

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

}
