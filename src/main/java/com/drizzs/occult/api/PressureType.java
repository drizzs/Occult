package com.drizzs.occult.api;

import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.register.OcPressure.PRESSURE;

public class PressureType {
    private final int colour;

    public PressureType(int colour) {
        this.colour = colour;
    }

    public int getPressureColour() {
        return colour;
    }

    public PressureType getTypeFromName(String name) {
        PressureType pressureType = null;

        for(RegistryObject<PressureType> type: PRESSURE.getEntries()) {
            if (type.getId().toString().equals(name)) {
                pressureType = type.get();
            }
        }
        return pressureType;
    }
}
