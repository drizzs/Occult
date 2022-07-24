package com.drizzs.occult.api.capability;

import com.drizzs.occult.api.gui.interfaces.IAsset;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.register.OcPressure.PRESSURE;

public class PressureType {
    private final int colour;

    private final String id;

    private final IAsset progressBarEmpty;
    private final IAsset progressBarFull;

    public PressureType(int colour , String name, IAsset progressBarFull, IAsset progressBarEmpty) {
        this.colour = colour;
        this.id = name;
        this.progressBarEmpty = progressBarEmpty;
        this.progressBarFull = progressBarFull;
    }

    public int getPressureColour() {
        return colour;
    }

    public String getId(){
        return this.id;
    }

    public IAsset getProgressBarEmpty() {
        return progressBarEmpty;
    }

    public IAsset getProgressBarFull() {
        return progressBarFull;
    }

    public static PressureType getTypeFromName(String name) {
        PressureType pressureType = null;

        for(RegistryObject<PressureType> type: PRESSURE.getEntries()) {
            if (type.getId().getPath().equals(name)) {
                pressureType = type.get();
            }
        }
        return pressureType;
    }
}
