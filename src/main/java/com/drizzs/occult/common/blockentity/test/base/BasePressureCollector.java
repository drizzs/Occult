package com.drizzs.occult.common.blockentity.test.base;

import com.drizzs.occult.api.capability.PressureCap;
import com.drizzs.occult.api.capability.PressureType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BasePressureCollector extends BlockEntity implements IPressureCollector{

    private int timer;
    private int totalPressures;

    private BlockPos currentMachine;

    public BasePressureCollector(BlockEntityType<?> type, BlockPos pos, BlockState state, int totalPressures) {
        super(type, pos, state);
        this.totalPressures = totalPressures;
    }

    public BlockPos getCurrentMachine() {
        return currentMachine;
    }

    public void setCurrentMachine(BlockPos currentMachine) {
        this.currentMachine = currentMachine;
    }

    public void removeMachine(){
        currentMachine = null;
    }

    public void tick(){
        timer++;
        assert level != null;
        if(!canSurvive(level,getBlockPos())){
            level.destroyBlock(getBlockPos(),true);
        }
    }

    private boolean canSurvive(Level level, BlockPos pos){
        return level.getBlockState(pos.east()).getBlock() instanceof IPressureConsumer || level.getBlockState(pos.west()).getBlock() instanceof IPressureConsumer || level.getBlockState(pos.north()).getBlock() instanceof IPressureConsumer|| level.getBlockState(pos.south()).getBlock() instanceof IPressureConsumer;
    }

    @Override
    public void collectPressureFromNearbyChunks(BasePressureMachine machine,int upgradeSpeed,int totalConsumablePressure) {
        if(machine.getBlockPos() == getCurrentMachine()) {
            if (upgradeSpeed <= timer) {
                for (int x = 0; x < totalPressures; ++x) {
                    assert level != null;
                    Object2IntMap<PressureType> currentMap = PressureCap.getAllPressureFromChunk(level.getChunkAt(this.getBlockPos()));

                    int randomPressure = new Random().nextInt(currentMap.keySet().size());

                    AtomicInteger next = new AtomicInteger();

                    currentMap.forEach((pressure, totalPressure) -> {

                        if (next.get() == randomPressure) {
                            PressureCap.removeChunkPressure(level.getChunkAt(this.getBlockPos()), pressure, Math.min(totalConsumablePressure, totalPressure));
                            PressureCap.addTilePressure(machine, pressure, Math.min(totalConsumablePressure, totalPressure));
                            level.addParticle(pressure.getCollectorParticle(),0.5D,0.5D,0.5D,0,0,0);
                        }
                        next.addAndGet(1);
                    });
                }
                timer = 0;
            }
        }
    }
}
