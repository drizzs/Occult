package com.drizzs.occult.register;

import com.drizzs.occult.common.recipe.crucible.*;
import com.drizzs.occult.common.recipe.crucible.serializer.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.drizzs.occult.OccultMod.MODID;

public class OcRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);
    public static final RegistryObject<RecipeSerializer<CrucibleCookingRecipe>> CRUCIBLE_COOKING_SERIALIZER = RECIPE_SERIALIZERS.register("crucible_cooking", CrucibleCookingSerializer::new);
    public static final RegistryObject<RecipeSerializer<CrucibleMeltingRecipe>> CRUCIBLE_MELTING_SERIALIZER = RECIPE_SERIALIZERS.register("crucible_melting", CrucibleMeltingSerializer::new);
    public static final RegistryObject<RecipeSerializer<CrucibleMixingRecipe>> CRUCIBLE_MIXING_SERIALIZER = RECIPE_SERIALIZERS.register("crucible_mixing", CrucibleMixingSerializer::new);
    public static final RegistryObject<RecipeSerializer<CrucibleDippingRecipe>> CRUCIBLE_DIPPING_SERIALIZER = RECIPE_SERIALIZERS.register("crucible_dipping", CrucibleDippingSerializer::new);
    public static final RegistryObject<RecipeSerializer<CrucibleCoolingRecipe>> CRUCIBLE_COOLING_SERIALIZER = RECIPE_SERIALIZERS.register("crucible_cooling", CrucibleCoolingSerializer::new);

    public static final RegistryObject<RecipeType<CrucibleCookingRecipe>> CRUCIBLE_COOKING = RECIPE_TYPES.register("crucible_cooking",()->new RecipeType<>(){});
    public static final RegistryObject<RecipeType<CrucibleMeltingRecipe>> CRUCIBLE_MELTING =  RECIPE_TYPES.register("crucible_melting",()->new RecipeType<>(){});
    public static final RegistryObject<RecipeType<CrucibleMixingRecipe>> CRUCIBLE_MIXING =  RECIPE_TYPES.register("crucible_mixing",()->new RecipeType<>(){});
    public static final RegistryObject<RecipeType<CrucibleDippingRecipe>> CRUCIBLE_DIPPING =  RECIPE_TYPES.register("crucible_dipping",()->new RecipeType<>(){});
    public static final RegistryObject<RecipeType<CrucibleCoolingRecipe>> CRUCIBLE_COOLING =  RECIPE_TYPES.register("crucible_cooling",()->new RecipeType<>(){});



    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }

}
