package com.drizzs.occult.api.gui.screen;

import com.drizzs.occult.api.gui.AssetTypes.AssetTypes;
import com.drizzs.occult.api.gui.AssetUtil;
import com.drizzs.occult.api.gui.interfaces.*;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AddonScreen extends Screen implements IAddonConsumer {

    public int x;
    public int y;
    private IAssetProvider assetProvider;
    private List<IAddon> addonList;
    private boolean drawBackground;

    private boolean isMouseDragging;
    private int dragX;
    private int dragY;

    protected AddonScreen(IAssetProvider assetProvider, boolean drawBackground) {
        super(Component.translatable(""));
        this.assetProvider = assetProvider;
        this.drawBackground = drawBackground;
    }

    @Override
    public void init() {
        super.init();
        IBackgroundAsset background = IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND);
        // width
        this.x = this.width / 2 - background.getArea().width / 2;
        // height
        this.y = this.height / 2 - background.getArea().height / 2;
        this.addonList = this.guiAddons().stream().map(IFactory::create).collect(Collectors.toList());
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) { //render
        stack.pushPose();
        renderBg(stack, mouseX, mouseY, partialTicks);
        stack.popPose();
        super.render(stack, mouseX, mouseY, partialTicks);
        stack.pushPose();
        renderFg(stack, mouseX, mouseY, partialTicks);
        stack.popPose();
    }

    public void renderBg(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.checkForMouseDrag(mouseX, mouseY);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        if (drawBackground) {
            this.renderBackground(stack, 0);//draw tinted background
            AssetUtil.drawAsset(stack, this, assetProvider.getAsset(AssetTypes.BACKGROUND), x, y);
        }
        addonList.forEach(iGuiAddon -> iGuiAddon.drawBackgroundLayer(stack, this, assetProvider, x, y, mouseX, mouseY, partialTicks));
    }

    public void renderFg(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        addonList.forEach(iGuiAddon -> iGuiAddon.drawForegroundLayer(stack, this, assetProvider, x, y, mouseX, mouseY));
        for (IAddon iAddon : addonList) {
            if (iAddon.isInside(this, mouseX - x, mouseY - y) && !iAddon.getTooltipLines().isEmpty()) {
                // renderTooltip
                assert minecraft != null;
                renderComponentTooltip(stack, iAddon.getTooltipLines(), mouseX, mouseY, minecraft.font);
            }
        }
    }

    public abstract List<IFactory<IAddon>> guiAddons();

    private void checkForMouseDrag(int mouseX, int mouseY) {
        if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {//Main Window
            if (!this.isMouseDragging) {
                this.isMouseDragging = true;
            } else {
                for (IAddon iScreenAddon : this.addonList) {
                    if (iScreenAddon instanceof ICanMouseDrag && iScreenAddon.isInside(null, mouseX - x, mouseY - y)) {
                        ((ICanMouseDrag) iScreenAddon).drag(mouseX - x, mouseY - y);
                    }
                }
            }
            this.dragX = mouseX;
            this.dragY = mouseY;
        } else {
            this.isMouseDragging = false;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        addonList.stream()
                .filter(iScreenAddon -> iScreenAddon instanceof IClickable && iScreenAddon.isInside(this, mouseX - x, mouseY - y))
                .forEach(iScreenAddon -> ((IClickable) iScreenAddon).handleClick(this, x, y, mouseX, mouseY, mouseButton));
        return false;
    }

    @Override
    public List<IAddon> getAddons() {
        return addonList;
    }
}
