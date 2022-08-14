package com.drizzs.occult.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.OccultMod.MODID;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
import static net.minecraft.client.renderer.texture.TextureAtlas.LOCATION_BLOCKS;

public class FluidRenderer {

    protected static Minecraft mc = Minecraft.getInstance();

    /**
     * Renders a fluid block with offset from the matrices with size w, h, d inside the block local coordinates, so from 0-1
     *
     * @param fluid Fluid to render
     * @param w     Width. 1 = full X-Width
     * @param h     Height. 1 = full Y-Height
     * @param d     Depth. 1 = full Z-Depth
     */
    public static void renderFluidCuboid(FluidStack fluid, PoseStack matrices, VertexConsumer renderer, int combinedLight, float w, float h, float d) {
        float wd = (1f - w) / 2f;
        float hd = (1f - h) / 2f;
        float dd = (1f - d) / 2f;

        renderFluidCuboid(fluid, matrices, renderer, combinedLight, wd, hd, dd, 1f - wd, 1f - hd, 1f - dd);
    }

    /**
     * Renders a fluid block with offset from the matrices and from x1/y1/z1 to x2/y2/z2 inside the block local coordinates, so from 0-1
     */
    public static void renderFluidCuboid(FluidStack fluid, PoseStack matrices, VertexConsumer renderer, int combinedLight, float x1, float y1, float z1, float x2, float y2, float z2) {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluid.getFluid());
        int color = extensions.getTintColor();
        renderFluidCuboid(fluid, matrices, renderer, combinedLight, x1, y1, z1, x2, y2, z2, color);
    }

    /**
     * Renders a fluid block with offset from the matrices and from x1/y1/z1 to x2/y2/z2 using block model coordinates, so from 0-16
     */
    public static void renderScaledFluidCuboid(FluidStack fluid, PoseStack matrices, VertexConsumer renderer, int combinedLight, float x1, float y1, float z1, float x2, float y2, float z2) {
        renderFluidCuboid(fluid, matrices, renderer, combinedLight, x1 / 16, y1 / 16, z1 / 16, x2 / 16, y2 / 16, x2 / 16);
    }

    /**
     * Renders a fluid block with offset from the matrices and from x1/y1/z1 to x2/y2/z2 inside the block local coordinates, so from 0-1
     */
    public static void renderFluidCuboid(FluidStack fluid, PoseStack matrices, VertexConsumer renderer, int combinedLight, float x1, float y1, float z1, float x2, float y2, float z2, int color) {
        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluid.getFluid());
        mc.getTextureManager().bindForSetup(LOCATION_BLOCKS);
        boolean upsideDown = fluid.getFluid().getFluidType().isLighterThanAir();

        TextureAtlasSprite still = mc.getTextureAtlas(LOCATION_BLOCKS).apply(extensions.getStillTexture(fluid));
        TextureAtlasSprite flowing = mc.getTextureAtlas(LOCATION_BLOCKS).apply(extensions.getFlowingTexture(fluid));

        matrices.pushPose();
        matrices.translate(x1, y1, z1);
        Matrix4f matrix = matrices.last().pose();

        // x/y/z2 - x/y/z1 is because we need the width/height/depth
        putTexturedQuad(renderer, matrix, still, x2 - x1, y2 - y1, z2 - z1, Direction.DOWN, color, combinedLight, false, upsideDown);
        putTexturedQuad(renderer, matrix, flowing, x2 - x1, y2 - y1, z2 - z1, Direction.NORTH, color, combinedLight, true, upsideDown);
        putTexturedQuad(renderer, matrix, flowing, x2 - x1, y2 - y1, z2 - z1, Direction.EAST, color, combinedLight, true, upsideDown);
        putTexturedQuad(renderer, matrix, flowing, x2 - x1, y2 - y1, z2 - z1, Direction.SOUTH, color, combinedLight, true, upsideDown);
        putTexturedQuad(renderer, matrix, flowing, x2 - x1, y2 - y1, z2 - z1, Direction.WEST, color, combinedLight, true, upsideDown);
        putTexturedQuad(renderer, matrix, still, x2 - x1, y2 - y1, z2 - z1, Direction.UP, color, combinedLight, false, upsideDown);

        matrices.popPose();
    }

    public static void putTexturedQuad(VertexConsumer renderer, Matrix4f matrix, TextureAtlasSprite sprite, float w, float h, float d, Direction face,
                                       int color, int brightness, boolean flowing) {
        putTexturedQuad(renderer, matrix, sprite, w, h, d, face, color, brightness, flowing, false);
    }

    public static void putTexturedQuad(VertexConsumer renderer, Matrix4f matrix, TextureAtlasSprite sprite, float w, float h, float d, Direction face,
                                       int color, int brightness, boolean flowing, boolean flipHorizontally) {
        int l1 = brightness >> 0x10 & 0xFFFF;
        int l2 = brightness & 0xFFFF;

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        putTexturedQuad(renderer, matrix, sprite, w, h, d, face, r, g, b, a, l1, l2, flowing, flipHorizontally);
    }

    public static void putTexturedQuad(VertexConsumer renderer, Matrix4f matrix, TextureAtlasSprite sprite, float w, float h, float d, Direction face,
                                       int r, int g, int b, int a, int light1, int light2, boolean flowing) {
        putTexturedQuad(renderer, matrix, sprite, w, h, d, face, r, g, b, a, light1, light2, flowing, false);
    }

    // x and x+w has to be within [0,1], same for y/h and z/d
    public static void putTexturedQuad(VertexConsumer renderer, Matrix4f matrix, TextureAtlasSprite sprite, float w, float h, float d, Direction face,
                                       int r, int g, int b, int a, int light1, int light2, boolean flowing, boolean flipHorizontally) {
        // safety
        if (sprite == null) {
            return;
        }
        float minU;
        float maxU;
        float minV;
        float maxV;

        double size = 16f;
        if (flowing) {
            size = 8f;
        }

        double xt1 = 0;
        double xt2 = w;
        while (xt2 > 1f) xt2 -= 1f;
        double yt1 = 0;
        double yt2 = h;
        while (yt2 > 1f) yt2 -= 1f;
        double zt1 = 0;
        double zt2 = d;
        while (zt2 > 1f) zt2 -= 1f;

        // flowing stuff should start from the bottom, not from the start
        if (flowing) {
            double tmp = 1d - yt1;
            yt1 = 1d - yt2;
            yt2 = tmp;
        }

        switch (face) {
            case DOWN:
            case UP:
                minU = sprite.getU(xt1 * size);
                maxU = sprite.getU(xt2 * size);
                minV = sprite.getV(zt1 * size);
                maxV = sprite.getV(zt2 * size);
                break;
            case NORTH:
            case SOUTH:
                minU = sprite.getU(xt2 * size);
                maxU = sprite.getU(xt1 * size);
                minV = sprite.getV(yt1 * size);
                maxV = sprite.getV(yt2 * size);
                break;
            case WEST:
            case EAST:
                minU = sprite.getU(zt2 * size);
                maxU = sprite.getU(zt1 * size);
                minV = sprite.getV(yt1 * size);
                maxV = sprite.getV(yt2 * size);
                break;
            default:
                minU = sprite.getU0();
                maxU = sprite.getU0();
                minV = sprite.getV0();
                maxV = sprite.getV1();
        }

        if (flipHorizontally) {
            float tmp = minV;
            minV = maxV;
            maxV = tmp;
        }

        switch (face) {
            case DOWN:
                renderer.vertex(matrix, 0, 0, 0).color(r, g, b, a).uv(minU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, 0, 0).color(r, g, b, a).uv(maxU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, 0, d).color(r, g, b, a).uv(maxU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, 0, d).color(r, g, b, a).uv(minU, maxV).uv2(light1, light2).endVertex();
                break;
            case UP:
                renderer.vertex(matrix, 0, h, 0).color(r, g, b, a).uv(minU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, h, d).color(r, g, b, a).uv(minU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, h, d).color(r, g, b, a).uv(maxU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, h, 0).color(r, g, b, a).uv(maxU, minV).uv2(light1, light2).endVertex();
                break;
            case NORTH:
                renderer.vertex(matrix, 0, 0, 0).color(r, g, b, a).uv(minU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, h, 0).color(r, g, b, a).uv(minU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, h, 0).color(r, g, b, a).uv(maxU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, 0, 0).color(r, g, b, a).uv(maxU, maxV).uv2(light1, light2).endVertex();
                break;
            case SOUTH:
                renderer.vertex(matrix, 0, 0, d).color(r, g, b, a).uv(maxU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, 0, d).color(r, g, b, a).uv(minU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, h, d).color(r, g, b, a).uv(minU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, h, d).color(r, g, b, a).uv(maxU, minV).uv2(light1, light2).endVertex();
                break;
            case WEST:
                renderer.vertex(matrix, 0, 0, 0).color(r, g, b, a).uv(maxU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, 0, d).color(r, g, b, a).uv(minU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, h, d).color(r, g, b, a).uv(minU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, 0, h, 0).color(r, g, b, a).uv(maxU, minV).uv2(light1, light2).endVertex();
                break;
            case EAST:
                renderer.vertex(matrix, w, 0, 0).color(r, g, b, a).uv(minU, maxV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, h, 0).color(r, g, b, a).uv(minU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, h, d).color(r, g, b, a).uv(maxU, minV).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, w, 0, d).color(r, g, b, a).uv(maxU, maxV).uv2(light1, light2).endVertex();
                break;
        }
    }

    /**
     * Similar to putTexturedQuad, except its only for upwards quads and a rotation is specified
     */
    public static void putRotatedQuad(VertexConsumer renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double d, Direction rotation,
                                      int color, int brightness, boolean flowing) {
        int l1 = brightness >> 0x10 & 0xFFFF;
        int l2 = brightness & 0xFFFF;

        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;

        putRotatedQuad(renderer, sprite, x, y, z, w, d, rotation, r, g, b, a, l1, l2, flowing);
    }

    /**
     * Similar to putTexturedQuad, except its only for upwards quads and a rotation is specified
     */
    public static void putRotatedQuad(VertexConsumer renderer, TextureAtlasSprite sprite, double x, double y, double z, double w, double d, Direction rotation,
                                      int r, int g, int b, int a, int light1, int light2, boolean flowing) {
        // safety
        if (sprite == null) {
            return;
        }

        double size = 16f;
        if (flowing) {
            size = 8f;
        }

        // coordinates for the sprite are super simple
        double x2 = x + w;
        double z2 = z + d;

        // textures
        double xt1 = x % 1d;
        double xt2 = xt1 + w;
        double zt1 = z % 1d;
        double zt2 = zt1 + d;

        // when rotating by 90 or 270 the dimensions switch, so switch the U and V before hand
        if (rotation.getAxis() == Direction.Axis.X) {
            double temp = xt1;
            xt1 = zt1;
            zt1 = temp;
            temp = xt2;
            xt2 = zt2;
            zt2 = temp;
        }

        // we want to start from the bottom for north or west textures as otherwise UV is backwards
        // we also want to start from the bottom for flowing fluids, and both should cancel
        if (flowing ^ (rotation == Direction.NORTH || rotation == Direction.WEST)) {
            double tmp = 1d - zt1;
            zt1 = 1d - zt2;
            zt2 = tmp;
        }

        float minU = sprite.getU(xt1 * size);
        float maxU = sprite.getU(xt2 * size);
        float minV = sprite.getV(zt1 * size);
        float maxV = sprite.getV(zt2 * size);

        switch (rotation) {
            case NORTH:
                renderer.vertex(x, y, z).color(r, g, b, a).uv(minU, minV).uv(light1, light2).endVertex();
                renderer.vertex(x, y, z2).color(r, g, b, a).uv(minU, maxV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z2).color(r, g, b, a).uv(maxU, maxV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z).color(r, g, b, a).uv(maxU, minV).uv(light1, light2).endVertex();
                break;
            case WEST:
                renderer.vertex(x, y, z).color(r, g, b, a).uv(maxU, minV).uv(light1, light2).endVertex();
                renderer.vertex(x, y, z2).color(r, g, b, a).uv(minU, minV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z2).color(r, g, b, a).uv(minU, maxV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z).color(r, g, b, a).uv(maxU, maxV).uv(light1, light2).endVertex();
                break;
            case SOUTH:
                renderer.vertex(x, y, z).color(r, g, b, a).uv(maxU, maxV).uv(light1, light2).endVertex();
                renderer.vertex(x, y, z2).color(r, g, b, a).uv(maxU, minV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z2).color(r, g, b, a).uv(minU, minV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z).color(r, g, b, a).uv(minU, maxV).uv(light1, light2).endVertex();
                break;
            case EAST:
                renderer.vertex(x, y, z).color(r, g, b, a).uv(minU, maxV).uv(light1, light2).endVertex();
                renderer.vertex(x, y, z2).color(r, g, b, a).uv(maxU, maxV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z2).color(r, g, b, a).uv(maxU, minV).uv(light1, light2).endVertex();
                renderer.vertex(x2, y, z).color(r, g, b, a).uv(minU, minV).uv(light1, light2).endVertex();

        }
    }

    public static void setColorRGB(int color) {
        setColorRGBA(color | 0xff000000);
    }

    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;

        RenderSystem.setShaderColor(r, g, b, a);
    }

    public static int compose(int r, int g, int b, int a) {
        int rgb = a;
        rgb = (rgb << 8) + r;
        rgb = (rgb << 8) + g;
        rgb = (rgb << 8) + b;
        return rgb;
    }

    public static int alpha(int c) {
        return (c >> 24) & 0xFF;
    }

    public static int red(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int green(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }

    /**
     * Gets the model for the given item
     * @param item   Item provider
     * @param clazz  Class type to cast result into
     * @param <T>    Class type
     * @return  Item model, or null if its missing or the wrong class type
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends BakedModel> T getBakedModel(ItemStack item, Class<T> clazz) {
        BakedModel baked = mc.getItemRenderer().getItemModelShaper().getItemModel(item);
        if (clazz.isInstance(baked)) {
            return (T) baked;
        }
        return null;
    }

    public static final RenderStateShard.TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    private static final RenderType blockRenderType = RenderType.create(MODID + ":block_render_type",
            POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(LOCATION_BLOCKS,false,false))
                    .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getBlockShader))
                    .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                    .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, true))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .createCompositeState(false));

    public static RenderType getBlockRenderType(){
        return blockRenderType;
    }

}
