package com.drizzs.occult.common.fluid.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class OcFluid extends FluidType {

    private final ResourceLocation STILL_TEXTURE;
    private final ResourceLocation FLOWING_TEXTURE;
    private ResourceLocation overlayTexture;
    private int colourTint;

    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     * @param stillTexture is the still texture for the fluid
     * @param flowingTexture is the flowing texture for the fluid
     */
    public OcFluid(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties);
        this.STILL_TEXTURE = stillTexture;
        this.FLOWING_TEXTURE = flowingTexture;
    }

    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     * @param stillTexture is the still texture for the fluid
     * @param flowingTexture is the flowing texture for the fluid
     * @param overlayTexture is the overlay texture when an entity is in the fluid (I think)
     */
    public OcFluid(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture) {
        super(properties);
        this.STILL_TEXTURE = stillTexture;
        this.FLOWING_TEXTURE = flowingTexture;
        this.overlayTexture = overlayTexture;
    }

    public void setColourTint(int colourTint) {
        this.colourTint = colourTint;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer){
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

            @Nullable
            @Override
            public ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public int getTintColor() {
                return colourTint > 0 ? colourTint : IClientFluidTypeExtensions.super.getTintColor();
            }
        });
    }

}
