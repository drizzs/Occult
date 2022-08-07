package com.drizzs.occult.client.screen.base;

import com.drizzs.occult.api.capability.PressureStorage;
import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.api.gui.AssetTypes.AssetTypes;
import com.drizzs.occult.api.gui.interfaces.*;
import com.drizzs.occult.api.gui.screen.IAddonConsumer;
import com.drizzs.occult.client.addon.PressureBarAddon;
import com.drizzs.occult.common.network.PacketHandler;
import com.drizzs.occult.common.network.PressureGatheringPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

import static com.drizzs.occult.register.OcPressure.PRESSURE;

public class BaseContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> implements IAddonConsumer {
    private final T container;
    private final Component title;
    private IAssetProvider assetProvider;
    private int xCenter;
    private int yCenter;
    private List<IAddon> addons;

    private int dragX;
    private int dragY;
    private boolean isMouseDragging;

    public BaseContainerScreen(T container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.container = container;
        this.title = title;
        this.assetProvider = IAssetProvider.DEFAULT_PROVIDER;
        IAsset background = IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND);
        this.imageWidth = background.getArea().width;
        this.imageHeight = background.getArea().height;
        this.isMouseDragging = false;
        this.addons = new ArrayList<>();
    }

    public BaseContainerScreen(T container, Inventory inventory, Component title, IAssetProvider provider) {
        super(container, inventory, title);
        this.container = container;
        this.title = title;
        this.assetProvider = provider;
        IAsset background = IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND);
        this.imageWidth = background.getArea().width;
        this.imageHeight = background.getArea().height;
        this.addons = new ArrayList<>();

        int next = 0;

        for (RegistryObject<PressureType> type: PRESSURE.getEntries()) {
            addons.add(new PressureBarAddon(16 + (8*next) ,25,type.get().getId(),new PressureStorage()));
            next++;
       }
       // addons.add(new PressureBarAddon(10 ,18,"umbral",new PressureStorage()));

    }

    // init
    @Override
    protected void init() {
        super.init();
        this.getAddons().forEach(screenAddon -> screenAddon.init(this.leftPos, this.topPos));
    }

    protected void renderBg(PoseStack stack, float partialTicks ,int mouseX, int mouseY) {
        // width
        xCenter = (width - imageWidth) / 2;
        // height
        yCenter = (height - imageHeight) / 2;
        PacketHandler.INSTANCE.sendToServer(new PressureGatheringPacket());
        this.renderBackground(stack);
        //BG RENDERING
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0,IAssetProvider.getAsset(assetProvider, AssetTypes.BACKGROUND).getResourceLocation());
        blit(stack, xCenter, yCenter, 0, 0, imageWidth, imageHeight);
        Minecraft.getInstance().font.draw(stack, ChatFormatting.DARK_GRAY + title.getString(), xCenter + imageWidth / 2 - Minecraft.getInstance().font.width(title.getString()) / 2, yCenter + 6, 0xFFFFFF);
        this.checkForMouseDrag(mouseX, mouseY);
        addons.stream().filter(IAddon::isBackground).forEach(iGuiAddon -> {
            stack.pushPose();
            iGuiAddon.drawBackgroundLayer(stack, this, assetProvider, xCenter, yCenter, mouseX, mouseY, partialTicks);
            stack.popPose();
        });
        addons.stream().filter(iScreenAddon -> !iScreenAddon.isBackground()).forEach(iGuiAddon -> {
            stack.pushPose();
            iGuiAddon.drawBackgroundLayer(stack, this, assetProvider, xCenter, yCenter, mouseX, mouseY, partialTicks);
            stack.popPose();
        });
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        stack.pushPose();
        renderBg(stack, partialTicks,mouseX, mouseY);
        stack.popPose();
        super.render(stack, mouseX, mouseY, partialTicks);
        stack.pushPose();
        renderFg(stack, mouseX, mouseY, partialTicks);
        stack.popPose();
    }

    protected void renderFg(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        // renderBackground
        //BG RENDERING
        this.checkForMouseDrag(mouseX, mouseY);
        addons.stream().filter(IAddon::isBackground).forEach(iGuiAddon -> {
            iGuiAddon.drawForegroundLayer(stack, this, assetProvider, xCenter, yCenter, mouseX, mouseY);
        });
        addons.stream().filter(iScreenAddon -> !iScreenAddon.isBackground()).forEach(iGuiAddon -> {
            iGuiAddon.drawForegroundLayer(stack, this, assetProvider, xCenter, yCenter, mouseX, mouseY);
        });
    }

    protected void drawGuiContainerForegroundLayer(PoseStack stack, int mouseX, int mouseY) {
        addons.forEach(iGuiAddon -> iGuiAddon.drawForegroundLayer(stack, this, assetProvider, xCenter, yCenter, mouseX, mouseY));
        // renderHoveredToolTip
        renderComponentHoverEffect(stack, Style.EMPTY,mouseX - xCenter, mouseY - yCenter);
        for (IAddon iScreenAddon : addons) {
            if (iScreenAddon.isInside(this, mouseX - xCenter, mouseY - yCenter) && !iScreenAddon.getTooltipLines().isEmpty()) {
                // renderTooltip
                renderComponentTooltip(stack, iScreenAddon.getTooltipLines(), mouseX - xCenter, mouseY - yCenter, minecraft.font);
            }
        }
    }

    @Override
    protected void renderTooltip(PoseStack stack, ItemStack itemStack, int mouseX, int mouseY) {
        drawGuiContainerForegroundLayer(stack,mouseX,mouseY);
        super.renderTooltip(stack, itemStack, mouseX, mouseY);
    }

    private void checkForMouseDrag(int mouseX, int mouseY) {
        if (GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {//Main Window
            if (!this.isMouseDragging) {
                this.isMouseDragging = true;
            } else {
                for (IAddon iScreenAddon : this.addons) {
                    if (iScreenAddon instanceof ICanMouseDrag && iScreenAddon.isInside(null, mouseX - this.xCenter, mouseY - this.yCenter)) {
                        ((ICanMouseDrag) iScreenAddon).drag(mouseX - this.xCenter, mouseY - this.yCenter);
                    }
                }
            }
            this.dragX = mouseX;
            this.dragY = mouseY;
        } else {
            this.isMouseDragging = false;
        }
    }

    // mouseClicked
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        new ArrayList<>(addons).stream().filter(iGuiAddon -> iGuiAddon instanceof IClickable && iGuiAddon.isInside(this, mouseX - xCenter, mouseY - yCenter))
                .forEach(iGuiAddon -> ((IClickable) iGuiAddon).handleClick(this, xCenter, yCenter, mouseX, mouseY, mouseButton));
        return false;
    }

    // keyPressed
    @Override
    public boolean keyPressed(int keyCode, int scan, int modifiers) {
        return this.getAddons().stream()
                .anyMatch(screenAddon -> screenAddon.keyPressed(keyCode, scan, modifiers)) ||
                super.keyPressed(keyCode, scan, modifiers);
    }

    public int getX() {
        return xCenter;
    }

    public int getY() {
        return yCenter;
    }

    @Override
    public @NotNull T getMenu() {
        return container;
    }

    @Override
    public List<IAddon> getAddons() {
        return addons;
    }

    public void setAddons(List<IAddon> addons) {
        this.addons = addons;
    }

    public IAssetProvider getAssetProvider() {
        return assetProvider;
    }

    public void setAssetProvider(IAssetProvider assetProvider) {
        this.assetProvider = assetProvider;
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) {

    }
}
