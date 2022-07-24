package com.drizzs.occult.client.screen;

import com.drizzs.occult.OccultMod;
import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.common.container.BasePressureContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.register.OcPressure.PRESSURE;

public abstract class BasePressureScreen extends AbstractContainerScreen<BasePressureContainer> {

    private final String textureLocation;


    public BasePressureScreen(BasePressureContainer container, Inventory inventory, Component title,String textureLocation) {
        super(container, inventory, title);
        this.leftPos = 0;
        this.topPos = 0;

        this.textureLocation = textureLocation;

    }

    @Override
    protected void renderBg(PoseStack stack, float mouseX, int mouseY, int partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);

        RenderSystem.setShaderTexture(0,new ResourceLocation(OccultMod.MODID,textureLocation));
        int i = this.leftPos;
        int j = this.topPos;
        blit(stack,i,j,0,0,this.imageWidth,this.imageHeight);
        int l;

        if (this.menu.isActivated()) {


        }
        Object2IntMap<PressureType> r;
        r = this.menu.containedPressure();

        int totalPressure = 0;
        int pressureCap = this.menu.getPressureCapacity();


        for(RegistryObject<PressureType> pressure : PRESSURE.getEntries()){
            if(r.containsKey(pressure.get()))
                totalPressure = 59*(r.getInt(pressure.get()) / pressureCap);
            this.blit(stack, i + 79, j + 34, 176, 14, 60, 16);
            this.blit(stack, i + 79, j + 34, 176, 14, totalPressure + 1, 16);
            totalPressure = 1;
        }

    }


}
