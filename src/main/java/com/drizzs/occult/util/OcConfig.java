package com.drizzs.occult.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class OcConfig {

    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.IntValue baseChunkCapacity;
    public static ForgeConfigSpec.IntValue baseTileCapacity;
    public static ForgeConfigSpec.IntValue weakPressureCollector;
    public static ForgeConfigSpec.IntValue averagePressureCollector;
    public static ForgeConfigSpec.IntValue strongPressureCollector;
    public static ForgeConfigSpec.IntValue superStrongPressureCollector;


    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        baseChunkCapacity = builder.defineInRange("base_chunk_pressure", 10000, 1000, 500000);
        baseTileCapacity = builder.defineInRange("base_tile_pressure", 1000, 100, 500000);

        weakPressureCollector = builder.defineInRange("weak_pressure_absorber", 15, 1, 50000);
        averagePressureCollector = builder.defineInRange("average_pressure_absorber", 30, 1, 50000);
        strongPressureCollector = builder.defineInRange("powerful_pressure_absorber", 150, 1, 50000);
        superStrongPressureCollector = builder.defineInRange("destructive_pressure_absorber", 300, 1, 50000);
    }

}
