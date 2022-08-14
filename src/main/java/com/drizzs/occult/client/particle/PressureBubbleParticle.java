package com.drizzs.occult.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PressureBubbleParticle extends TextureSheetParticle {

    protected PressureBubbleParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.xd = xd * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
        this.yd = yd * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
        this.zd = zd * (double)0.2F + (Math.random() * 2.0D - 1.0D) * (double)0.02F;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_105793_) {
            this.sprite = p_105793_;
        }

        public Particle createParticle(SimpleParticleType p_105804_, ClientLevel p_105805_, double p_105806_, double p_105807_, double p_105808_, double p_105809_, double p_105810_, double p_105811_) {
            PressureBubbleParticle bubbleparticle = new PressureBubbleParticle(p_105805_, p_105806_, p_105807_, p_105808_, p_105809_, p_105810_, p_105811_);
            bubbleparticle.pickSprite(this.sprite);
            return bubbleparticle;
        }
    }

}
