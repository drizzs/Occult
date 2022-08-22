package com.drizzs.occult.register;

import com.drizzs.occult.common.block.CrucibleBlock;
import com.drizzs.occult.common.block.RitualFireBlock;
import com.drizzs.occult.common.item.OccultBlockItem;
import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

import static com.drizzs.occult.OccultMod.MODID;
import static com.drizzs.occult.common.OccultGroup.OCCULT_GROUP;
import static com.drizzs.occult.register.OcItems.ITEMS;

public class OcBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static Map<RegistryObject<Block>, String> blocklist = Maps.newHashMap();

   public static final RegistryObject<Block> RITUAL_FIRE = BLOCKS.register("ritual_fire", RitualFireBlock::new);
   // public static final RegistryObject<Block> PRESSURE_COLLECTOR1 = BLOCKS.register("pressure_collector1", WeakPressureCollector::new);
   // public static final RegistryObject<Block> PRESSURE_COLLECTOR2 = BLOCKS.register("pressure_collector2", AveragePressureCollector::new);
    //public static final RegistryObject<Block> PRESSURE_COLLECTOR3 = BLOCKS.register("pressure_collector3", StrongPressureCollector::new);
    //public static final RegistryObject<Block> PRESSURE_COLLECTOR4 = BLOCKS.register("pressure_collector4", ExtraStrongPressureCollector::new);
    //public static final RegistryObject<Block> PRESSURE_COLLECTOR5 = BLOCKS.register("pressure_collector5", InfinitePressureCollector::new);

    //public static final RegistryObject<Block> CRUSHER = BLOCKS.register("crusher", CrusherBlock::new);
    public static final RegistryObject<Block> CRUCIBLE = BLOCKS.register("crucible", CrucibleBlock::new);
    public static void register(IEventBus eventBus) {

        BLOCKS.register(eventBus);

        blocklist.put(RITUAL_FIRE,"ritual_fire");
        blocklist.put(CRUCIBLE,"crucible");
        //blocklist.put(CRUSHER,"crusher");
        //blocklist.put(PRESSURE_COLLECTOR1,"pressure_collector1");
       // blocklist.put(PRESSURE_COLLECTOR2,"pressure_collector2");
        //blocklist.put(PRESSURE_COLLECTOR3,"pressure_collector3");
       // blocklist.put(PRESSURE_COLLECTOR4,"pressure_collector4");
       // blocklist.put(PRESSURE_COLLECTOR5,"pressure_collector5");

        for (RegistryObject<Block> block : blocklist.keySet()) {
            ITEMS.register(blocklist.get(block), () -> new OccultBlockItem(block.get(), new Item.Properties().tab(OCCULT_GROUP)));
        }
    }

}
