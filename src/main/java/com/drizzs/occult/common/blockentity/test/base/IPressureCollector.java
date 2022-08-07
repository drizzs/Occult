package com.drizzs.occult.common.blockentity.test.base;

public interface IPressureCollector {

    void collectPressureFromNearbyChunks(BasePressureMachine machine,int upgradeSpeed,int totalConsumablePressure);

    void tick();

}
