package com.drizzs.occult.api;

import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.register.OcPressure.PRESSURE;

public class PressureType {
    private final int colour;

    private final String id;

    public PressureType(int colour , String name) {
        this.colour = colour;
        this.id = name;
    }

    public int getPressureColour() {
        return colour;
    }

    public String getId(){
        return this.id;
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
