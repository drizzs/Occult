package com.drizzs.occult.register;

import com.drizzs.occult.common.item.BaseItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.common.OccultGroup.OCCULT_GROUP;

public class OcItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> MERCURY = ITEMS.register("mercury",
            () -> new BaseItem(Objects.requireNonNull(new Item.Properties()
                    .tab(OCCULT_GROUP)))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
