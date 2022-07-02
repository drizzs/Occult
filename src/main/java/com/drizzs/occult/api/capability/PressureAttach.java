package com.drizzs.occult.api.capability;

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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.PistonEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.OccultMod.MODID;

public class PressureAttach {

    public static class PressureProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        public static final ResourceLocation IDENTIFIER = new ResourceLocation(MODID, "pressure");

        private final IPressure backend = new PressureStorage();
        private final LazyOptional<IPressure> optionalData = LazyOptional.of(() -> backend);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return PressureCap.PRESSURE_CAPABILITY.orEmpty(cap, this.optionalData);
        }

        void invalidate() {
            this.optionalData.invalidate();
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
        final PressureProvider provider = new PressureProvider();

        event.addCapability(PressureProvider.IDENTIFIER, provider);
    }

    public static void attachBlockEntity(final AttachCapabilitiesEvent<BlockEntity> event) {
        final PressureProvider provider = new PressureProvider();

        event.addCapability(PressureProvider.IDENTIFIER, provider);
    }

    private PressureAttach() {
    }

}
