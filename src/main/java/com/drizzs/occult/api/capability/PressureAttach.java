package com.drizzs.occult.api.capability;

import com.drizzs.occult.util.OcConfig;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.OccultMod.MODID;

public class PressureAttach {

    public static final ResourceLocation IDENTIFIER = new ResourceLocation(MODID, "pressure");

    public static class PressureProviderChunk implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        private final IPressure backend = new PressureStorage(OcConfig.baseChunkCapacity.get());
        private final LazyOptional<IPressure> optionalData = LazyOptional.of(() -> backend);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return PressureCap.PRESSURE_CAPABILITY.orEmpty(cap, this.optionalData);
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    public static class PressureProviderTile implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private final IPressure backend = new PressureStorage(OcConfig.baseTileCapacity.get());
        private final LazyOptional<IPressure> optionalData = LazyOptional.of(() -> backend);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return PressureCap.PRESSURE_CAPABILITY.orEmpty(cap, this.optionalData);
        }

        @Override
        public CompoundTag serializeNBT() {
            return this.backend.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            this.backend.deserializeNBT(nbt);
        }
    }

    public static void attachChunk(final AttachCapabilitiesEvent<LevelChunk> event) {
        final PressureProviderChunk provider = new PressureProviderChunk();

        event.addCapability(IDENTIFIER, provider);
    }

    public static void attachBlockEntity(final AttachCapabilitiesEvent<BlockEntity> event) {
        final PressureProviderTile provider = new PressureProviderTile();

        event.addCapability(IDENTIFIER, provider);
    }

    private PressureAttach() {
    }

}
