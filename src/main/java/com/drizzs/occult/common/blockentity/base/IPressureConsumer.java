package com.drizzs.occult.common.blockentity.base;

import com.drizzs.occult.api.PressureType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface IPressureConsumer {

    Object2IntMap<PressureType> getPressureFromNearbyBEs();

}
