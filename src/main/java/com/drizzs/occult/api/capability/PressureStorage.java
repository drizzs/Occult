package com.drizzs.occult.api.capability;

import com.drizzs.occult.api.PressureType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.nbt.CompoundTag;

public class PressureStorage implements IPressure {

    public Object2IntMap<PressureType> pressures = new Object2IntOpenHashMap<>();
    public int capacity;

    public PressureStorage() {
        this.capacity = 1000;
    }

    public PressureStorage(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int add(PressureType pressureType, int amount) {
        int pressure = getPressureFromType(pressureType);
        if (pressure + amount <= capacity) {
            int add = pressure + amount;
            pressures.put(pressureType, add);
            return amount;
        } else {
            fill(pressureType);
            return capacity;
        }
    }

    @Override
    public int remove(PressureType pressureType, int amount) {
        int pressure = getPressureFromType(pressureType);
        if (pressure + amount >= 0) {
            int remove = pressure - amount;
            pressures.put(pressureType, remove);
            return amount;
        } else {
            empty(pressureType);
            return 0;
        }
    }

    @Override
    public void empty(PressureType pressureType) {
        pressures.put(pressureType, 0);
    }

    @Override
    public void fill(PressureType pressureType) {
        pressures.replace(pressureType, capacity);
    }

    @Override
    public Object2IntMap<PressureType> getAllPressure() {
        return pressures;
    }

    @Override
    public int getPressureFromType(PressureType pressureType) {
        ensureExists(pressureType);
        return pressures.getOrDefault(pressureType, 1);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        for (PressureType pressureType : pressures.keySet()) {
            compound.putInt(pressureType.getId(), getPressureFromType(pressureType));
        }
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (String pressureType : nbt.getAllKeys()) {
            if(!pressureType.isEmpty()) {
                pressures.put(PressureType.getTypeFromName(pressureType), nbt.getInt(pressureType));
            }
        }
    }

    private void ensureExists(PressureType pressureType) {
        if (!pressures.containsKey(pressureType)) {
            pressures.put(pressureType, 0);
        }
    }
}
