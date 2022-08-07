package com.drizzs.occult.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.register.OcPressure.*;

public class PressureGatheringParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected PressureGatheringParticle(ClientLevel level, double x, double y, double z ,float xd,float yd, float zd, int color,float quadSize,SpriteSet spriteSet) {
        super(level, x, y, z, 0, 0, 0);
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = spriteSet;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += xd;
        this.yd += yd;
        this.zd += zd;
        float f = level.random.nextFloat() * color;
        this.rCol = (color >> 16) & 0xff;
        this.gCol = (color >> 8) & 0xff;
        this.bCol = color & 0xff;
        this.quadSize *= 0.75F * quadSize;
        this.lifetime = (int)(8 / ((double)level.random.nextFloat() * 0.8D + 0.2D) * (double)quadSize);
        this.lifetime = Math.max(this.lifetime, 1);
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = true;
    }

    @Override
    public float getQuadSize(float size) {
        return this.quadSize * Mth.clamp(((float)this.age + size) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class InfernalProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public InfernalProvider(SpriteSet p_107696_) {
            this.sprites = p_107696_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new PressureGatheringParticle(level, x, y, z, (float) xd, (float) yd, (float) zd, INFERNAL.get().getPressureColour(),1.0F ,this.sprites);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class NaturalProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public NaturalProvider(SpriteSet p_107696_) {
            this.sprites = p_107696_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new PressureGatheringParticle(level, x, y, z, (float) xd, (float) yd, (float) zd, NATURAL.get().getPressureColour(),1.0F ,this.sprites);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class UmbralProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public UmbralProvider(SpriteSet p_107696_) {
            this.sprites = p_107696_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new PressureGatheringParticle(level, x, y, z, (float) xd, (float) yd, (float) zd, UMBRAL.get().getPressureColour(),1.0F ,this.sprites);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class SpiritualProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public SpiritualProvider(SpriteSet p_107696_) {
            this.sprites = p_107696_;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new PressureGatheringParticle(level, x, y, z, (float) xd, (float) yd, (float) zd, SPIRITUAL.get().getPressureColour(),1.0F ,this.sprites);
        }

    }
}
