package com.drizzs.occult.api.capability;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class PressureCap {

    public static final Capability<IPressure> PRESSURE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static final Direction DEFAULT_FACING = null;


    private PressureCap(){
    }

    public static LazyOptional<IPressure> getChunkPressure(final LevelChunk chunk) {
        return chunk.getCapability(PRESSURE_CAPABILITY, DEFAULT_FACING);
    }

    public static LazyOptional<IPressure> getChunkPressure(final Level level, final ChunkPos chunkPos) {
        return getChunkPressure(level.getChunk(chunkPos.x, chunkPos.z));
    }

    public static LazyOptional<IPressure> getTileEntityPressure(final BlockEntity tileEntity) {
        return tileEntity.getCapability(PRESSURE_CAPABILITY, DEFAULT_FACING);
    }

    public static void addChunkPressure(final LevelChunk chunk, final PressureType type, final int amount) {
        getChunkPressure(chunk)
                .ifPresent(chunkPressure -> {
                    chunkPressure.add(type, amount);
                });
    }
    public static void removeChunkPressure(final LevelChunk chunk, final PressureType type, final int amount) {
        getChunkPressure(chunk)
                .ifPresent(chunkPressure -> {
                    chunkPressure.remove(type, amount);
                });
    }

    public static int getChunkPressureForType(final LevelChunk chunk, final PressureType type) {
        getChunkPressure(chunk)
                .map(chunkPressure -> chunkPressure.getPressureFromType(type));
        return 0;
    }

    public static Object2IntMap<PressureType> getAllPressureFromChunk(final LevelChunk chunk){
        Object2IntMap<PressureType> pressure = new Object2IntOpenHashMap<>();
        getChunkPressure(chunk)
                .map(chunkPressure -> {
                    pressure.putAll(chunkPressure.getAllPressure());

                    return chunkPressure.getAllPressure();
                });
        return pressure;
    }

    public static void addTilePressure(final BlockEntity entity, final PressureType type, final int amount) {
        getTileEntityPressure(entity)
                .ifPresent(entityPressure -> {
                    entityPressure.add(type, amount);
                });
    }

    public static void removeTilePressure(final BlockEntity entity, final PressureType type, final int amount) {
        getTileEntityPressure(entity)
                .ifPresent(entityPressure -> {
                    entityPressure.remove(type, amount);
                });
    }

    public static int getTilePressureForType(final BlockEntity entity, final PressureType type) {
        getTileEntityPressure(entity)
                .map(entityPressure -> entityPressure.getPressureFromType(type));
        return 0;
    }

    public static Object2IntMap<PressureType> getAllPressureFromTile(final BlockEntity entity){
        Object2IntMap<PressureType> pressure = new Object2IntOpenHashMap<>();
        getTileEntityPressure(entity)
                .map(entityPressure -> {
                    for (PressureType type : entityPressure.getAllPressure().keySet()) {
                        pressure.put(type, entityPressure.getPressureFromType(type));
                    }
                    return pressure;
                });
        return pressure;
    }
}
