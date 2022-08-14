package com.drizzs.occult.client.renderer;

import com.drizzs.occult.common.blockentity.CrucibleBE;
import com.drizzs.occult.util.FluidRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import static com.drizzs.occult.common.block.base.BaseHorizontalBlock.FACING;

public class CrucibleBERenderer implements BlockEntityRenderer<CrucibleBE> {

    @Override
    public void render(@NotNull CrucibleBE crucibleBE, float partialTicks, PoseStack stack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderInventory(crucibleBE,stack,bufferIn,combinedLightIn,combinedOverlayIn);
        renderFluidInCrucible(crucibleBE, stack, bufferIn, combinedLightIn);
    }

    private void renderFluidInCrucible(CrucibleBE tile,PoseStack matrix,MultiBufferSource bufferIn,int combinedLightIn){
        FluidStack liquid = tile.getFluidStack();
        VertexConsumer builder = bufferIn.getBuffer(FluidRenderer.getBlockRenderType());
        float minY = 5.1F;
        float maxY = 19F;
        if(!liquid.isEmpty()){
            float sections = (maxY - minY)/tile.getCapacity();
            float height = (float)liquid.getAmount()*sections;
            FluidRenderer.renderScaledFluidCuboid(liquid,matrix,builder,combinedLightIn,4F,minY,4F,12F,minY + height,12F);
        }
    }

    private void renderInventory(CrucibleBE tile,PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn){
        float a;
        float b;
        float c;
        float d;
        if (isNorthorSouth(tile)) {
            a = 0.25F;
            b = 0.75F;
            c = 0.5F;
            d = 0.5F;
        } else {
            a = 0.5F;
            b = 0.5F;
            c = 0.25F;
            d = 0.75F;
        }
        if (tile.getItemList().size() == 1) {
            renderItem(tile.getItemList().get(0), tile,.5F, 1.8F, .5F, 0.3F, matrix, combinedLightIn, combinedOverlayIn, bufferIn);
        } else if (tile.getItemList().size() == 2) {
            renderItem(tile.getItemList().get(0), tile,a, 1.8F, c, 0.3F, matrix, combinedLightIn, combinedOverlayIn, bufferIn);
            renderItem(tile.getItemList().get(1), tile,b, 1.8F, d, 0.3F, matrix, combinedLightIn, combinedOverlayIn, bufferIn);
        } else if (tile.getItemList().size() == 3) {
            renderItem(tile.getItemList().get(0), tile,.5F, 1.8F, .5F, 0.3F, matrix, combinedLightIn, combinedOverlayIn, bufferIn);
            renderItem(tile.getItemList().get(1), tile,a, 1.7F, c, 0.3F, matrix, combinedLightIn, combinedOverlayIn, bufferIn);
            renderItem(tile.getItemList().get(2), tile,b, 1.7F, d, 0.3F, matrix, combinedLightIn, combinedOverlayIn, bufferIn);
        }

        if(!tile.getItemInSlot(3).isEmpty()){
            renderItem(tile.getItemInSlot(3),tile,.5F, 1.45F, .5F,0.3F,matrix,combinedLightIn,combinedOverlayIn,bufferIn);
        }
    }

    private boolean isNorthorSouth(CrucibleBE tile){
        return tile.getBlockState().getValue(FACING).equals(Direction.NORTH) || tile.getBlockState().getValue(FACING).equals(Direction.SOUTH);
    }

    private void renderItem(ItemStack item, CrucibleBE tile, float x, float y, float z, float scale, PoseStack stack, int lightIn, int overlay, MultiBufferSource buffer) {
        stack.pushPose();
        stack.translate(x, y, z);
        if(!isNorthorSouth(tile)){
            stack.mulPose(new Quaternion(90,-90,90,true));
        }
        stack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED, lightIn, overlay, stack, buffer,0);
        stack.popPose();
    }
}
