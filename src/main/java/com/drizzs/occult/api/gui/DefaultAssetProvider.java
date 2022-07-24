package com.drizzs.occult.api.gui;

import com.drizzs.occult.api.gui.AssetTypes.AssetTypes;
import com.drizzs.occult.api.gui.interfaces.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class DefaultAssetProvider implements IAssetProvider {
    private final IAsset SLOT = () -> new Rectangle(1, 185, 18, 18);
    private final IAsset ENERGY_BAR = () -> new Rectangle(177, 94, 18, 56);
    private final IAsset ENERGY_FILL = new IAsset() {
        @Override
        public Rectangle getArea() {
            return new Rectangle(196, 97, 12, 50);
        }

        @Override
        public Point getOffset() {
            return new Point(3, 3);
        }
    };
    private final ITankAsset TANK_NORMAL = new ITankAsset() {
        @Override
        public int getFluidRenderPadding(Direction facing) {
            return 3;
        }

        @Override
        public Rectangle getArea() {
            return new Rectangle(177, 1, 18, 56);
        }
    };
    private final ITankAsset TANK_SMALL = new ITankAsset() {
        @Override
        public int getFluidRenderPadding(Direction facing) {
            return 3;
        }

        @Override
        public Rectangle getArea() {
            return new Rectangle(235, 1, 18, 19);
        }
    };
    private final Point HOTBAR_POS = new Point(8, 160);
    private final Point INV_POS = new Point(8, 102);
    private final IBackgroundAsset BACKGROUND = new IBackgroundAsset() {
        @Override
        public Point getInventoryPosition() {
            return INV_POS;
        }

        @Override
        public Point getHotbarPosition() {
            return HOTBAR_POS;
        }

        @Override
        public Rectangle getArea() {
            return new Rectangle(0, 0, 176, 184);
        }
    };
    private final IAsset PROGRESS_BAR_BACKGROUND = new IAsset() {
        @Override
        public Rectangle getArea() {
            return new Rectangle(229, 1, 5, 50);
        }

        @Override
        public Point getOffset() {
            return new Point(3, 3);
        }
    };
    private final IAsset PROGRESS_BAR_FILL = new IAsset() {
        @Override
        public Rectangle getArea() {
            return new Rectangle(223, 1, 5, 50);
        }

        @Override
        public Point getOffset() {
            return new Point(3, 3);
        }
    };
    private final IAsset PROGRESS_BAR_BORDER = () -> new Rectangle(211, 1, 11, 56);

    private final IAsset BUTTON_SIDENESS_DISABLED = () -> new Rectangle(196, 1, 14, 14);
    private final IAsset BUTTON_SIDENESS_ENABLED = () -> new Rectangle(196, 16, 14, 14);
    private final IAsset BUTTON_SIDENESS_PULL = () -> new Rectangle(196, 31, 14, 14);
    private final IAsset BUTTON_SIDENESS_PUSH = () -> new Rectangle(196, 46, 14, 14);
    private final IAsset BUTTON_SIDENESS_MANAGER = () -> new Rectangle(1, 231, 14, 14);
    private final IAsset PROGRESS_BAR_ARROW_HORIZONTAL = () -> new Rectangle(177, 77, 22, 16);
    private final IAsset PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL = () -> new Rectangle(177, 61, 22, 15);
    private final IAsset AUGMENT_BACKGROUND = () -> new Rectangle(212, 61, 30, 84);
    private final IAsset AUGMENT_BACKGROUND_LEFT = new IAsset() {
        @Override
        public Rectangle getArea() {
            return  new Rectangle(0,0, 27, 84);
        }

        @Override
        public ResourceLocation getResourceLocation() {
            return DEFAULT_LOCATION_ALT;
        }
    };
    private final IAsset AUGMENT_BACKGROUND_LEFT_TALL = new IAsset() {
        @Override
        public Rectangle getArea() {
            return new Rectangle(0,84, 27, 161);
        }

        @Override
        public ResourceLocation getResourceLocation() {
            return DEFAULT_LOCATION_ALT;
        }
    };
    private final IAsset BUTTON_ARROW_UP = () -> new Rectangle(177, 151, 14, 14);
    private final IAsset BUTTON_ARROW_RIGHT = () -> new Rectangle(192, 151, 14, 14);
    private final IAsset BUTTON_ARROW_DOWN = () -> new Rectangle(207, 151, 14, 14);
    private final IAsset BUTTON_ARROW_LEFT = () -> new Rectangle(222, 151, 14, 14);
    private final IAsset ITEM_BACKGROUND = () -> new Rectangle(177, 166, 18, 18);
    private final IAsset TEXT_FIELD_ACTIVE = () -> new Rectangle(31, 240, 110, 16);
    private final IAsset TEXT_FIELD_INACTIVE = () -> new Rectangle(142, 240, 110, 16);
    private final IAsset BUTTON_REDSTONE_IGNORED = () -> new Rectangle(196, 166, 14, 14);
    private final IAsset BUTTON_REDSTONE_NO_REDSTONE = () -> new Rectangle(226, 166, 14, 14);
    private final IAsset BUTTON_REDSTONE_REDSTONE = () -> new Rectangle(211, 166, 14, 14);
    private final IAsset BUTTON_REDSTONE_ONCE = () -> new Rectangle(241, 166, 14, 14);
    private final IAsset PROGRESS_BAR_ARROW_DOWN = () -> new Rectangle(221, 211, 15, 23);
    private final IAsset PROGRESS_BAR_BACKGROUND_ARROW_DOWN = () -> new Rectangle(221, 185, 15, 23);
    private final IAsset HUE_PICKER = () -> new Rectangle(235, 21, 9, 14);
    private final IAsset SHADER_PICKER = () -> new Rectangle(245, 21, 9, 9);
    private final IAsset BUTTON_LOCKED = () -> new Rectangle(241, 196, 14, 14);
    private final IAsset BUTTON_UNLOCKED = () -> new Rectangle(241, 181, 14, 14);

    public DefaultAssetProvider() {
    }

    @Nullable
    @Override
    public <T extends IAsset> T getAsset(IAssetType<T> assetType) {
        if (assetType == AssetTypes.BACKGROUND)
            return assetType.castOrDefault(BACKGROUND);
        else if (assetType == AssetTypes.ENERGY_BACKGROUND)
            return assetType.castOrDefault(ENERGY_BAR);
        else if (assetType == AssetTypes.ENERGY_BAR)
            return assetType.castOrDefault(ENERGY_FILL);
        else if (assetType == AssetTypes.PROGRESS_BAR_BACKGROUND_VERTICAL)
            return assetType.castOrDefault(PROGRESS_BAR_BACKGROUND);
        else if (assetType == AssetTypes.PROGRESS_BAR_VERTICAL)
            return assetType.castOrDefault(PROGRESS_BAR_FILL);
        else if (assetType == AssetTypes.SLOT)
            return assetType.castOrDefault(SLOT);
        else if (assetType == AssetTypes.TANK_NORMAL)
            return assetType.castOrDefault(TANK_NORMAL);
        else if (assetType == AssetTypes.TANK_SMALL)
            return assetType.castOrDefault(TANK_SMALL);
        else if (assetType == AssetTypes.PROGRESS_BAR_BORDER_VERTICAL)
            return assetType.castOrDefault(PROGRESS_BAR_BORDER);
        else if (assetType == AssetTypes.BUTTON_SIDENESS_DISABLED)
            return assetType.castOrDefault(BUTTON_SIDENESS_DISABLED);
        else if (assetType == AssetTypes.BUTTON_SIDENESS_ENABLED)
            return assetType.castOrDefault(BUTTON_SIDENESS_ENABLED);
        else if (assetType == AssetTypes.BUTTON_SIDENESS_PULL)
            return assetType.castOrDefault(BUTTON_SIDENESS_PULL);
        else if (assetType == AssetTypes.BUTTON_SIDENESS_PUSH)
            return assetType.castOrDefault(BUTTON_SIDENESS_PUSH);
        else if (assetType == AssetTypes.BUTTON_SIDENESS_MANAGER)
            return assetType.castOrDefault(BUTTON_SIDENESS_MANAGER);
        else if (assetType == AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL)
            return assetType.castOrDefault(PROGRESS_BAR_BACKGROUND_ARROW_HORIZONTAL);
        else if (assetType == AssetTypes.PROGRESS_BAR_ARROW_HORIZONTAL)
            return assetType.castOrDefault(PROGRESS_BAR_ARROW_HORIZONTAL);
        else if (assetType == AssetTypes.AUGMENT_BACKGROUND)
            return assetType.castOrDefault(AUGMENT_BACKGROUND);
        else if (assetType == AssetTypes.BUTTON_ARROW_LEFT)
            return assetType.castOrDefault(BUTTON_ARROW_LEFT);
        else if (assetType == AssetTypes.BUTTON_ARROW_RIGHT)
            return assetType.castOrDefault(BUTTON_ARROW_RIGHT);
        else if (assetType == AssetTypes.BUTTON_ARROW_UP)
            return assetType.castOrDefault(BUTTON_ARROW_UP);
        else if (assetType == AssetTypes.BUTTON_ARROW_DOWN)
            return assetType.castOrDefault(BUTTON_ARROW_DOWN);
        else if (assetType == AssetTypes.ITEM_BACKGROUND)
            return assetType.castOrDefault(ITEM_BACKGROUND);
        else if (assetType == AssetTypes.TEXT_FIELD_ACTIVE)
            return assetType.castOrDefault(TEXT_FIELD_ACTIVE);
        else if (assetType == AssetTypes.TEXT_FIELD_INACTIVE)
            return assetType.castOrDefault(TEXT_FIELD_INACTIVE);
        else if (assetType == AssetTypes.BUTTON_REDSTONE_IGNORED)
            return assetType.castOrDefault(BUTTON_REDSTONE_IGNORED);
        else if (assetType == AssetTypes.BUTTON_REDSTONE_NO_REDSTONE)
            return assetType.castOrDefault(BUTTON_REDSTONE_NO_REDSTONE);
        else if (assetType == AssetTypes.BUTTON_REDSTONE_REDSTONE)
            return assetType.castOrDefault(BUTTON_REDSTONE_REDSTONE);
        else if (assetType == AssetTypes.BUTTON_REDSTONE_ONCE)
            return assetType.castOrDefault(BUTTON_REDSTONE_ONCE);
        else if (assetType == AssetTypes.PROGRESS_BAR_ARROW_DOWN)
            return assetType.castOrDefault(PROGRESS_BAR_ARROW_DOWN);
        else if (assetType == AssetTypes.PROGRESS_BAR_BACKGROUND_ARROW_DOWN)
            return assetType.castOrDefault(PROGRESS_BAR_BACKGROUND_ARROW_DOWN);
        else if (assetType == AssetTypes.HUE_PICKER)
            return assetType.castOrDefault(HUE_PICKER);
        else if (assetType == AssetTypes.SHADE_PICKER)
            return assetType.castOrDefault(SHADER_PICKER);
        else if (assetType == AssetTypes.AUGMENT_BACKGROUND_LEFT)
            return assetType.castOrDefault(AUGMENT_BACKGROUND_LEFT);
        else if (assetType == AssetTypes.AUGMENT_BACKGROUND_LEFT_TALL)
            return assetType.castOrDefault(AUGMENT_BACKGROUND_LEFT_TALL);
        else if (assetType == AssetTypes.BUTTON_LOCKED)
            return assetType.castOrDefault(BUTTON_LOCKED);
        else if (assetType == AssetTypes.BUTTON_UNLOCKED)
            return assetType.castOrDefault(BUTTON_UNLOCKED);
        return null;
    }

}
