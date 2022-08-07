package com.drizzs.occult.client.screen;

import com.drizzs.occult.client.assetprovider.PressureAssetProvider;
import com.drizzs.occult.client.screen.base.BaseContainerScreen;
import com.drizzs.occult.common.container.CrusherContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CrusherScreen extends BaseContainerScreen<CrusherContainer> {
    public CrusherScreen(CrusherContainer container, Inventory inventory, Component title) {
        super(container, inventory, title,new PressureAssetProvider());
    }






}
