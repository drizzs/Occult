package com.drizzs.occult.util;

import com.drizzs.occult.api.capability.PressureType;
import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static com.drizzs.occult.register.OcPressure.SPIRITUAL;

public class StaticJsonHelper {

    public static NonNullList<ItemStack> readItemList(JsonArray array) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();

        for (int i = 0; i < array.size(); ++i) {
            ItemStack stack = stackElementDeserialize(array.get(i));
            nonnulllist.add(stack);
        }

        return nonnulllist;
    }

    private static ItemStack stackElementDeserialize(@Nullable JsonElement json) {
        if (json != null && !json.isJsonNull()) {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                return deserializeItem(jsonObject);
            } else {
                throw new JsonSyntaxException("Expected 1 item");
            }
        } else {
            throw new JsonSyntaxException("input cannot be null");
        }
    }

    private static ItemStack deserializeItem(JsonObject json) {
        if (json.has("item")) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "item"));
            ItemStack stack = Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(resourcelocation)).getDefaultInstance();
            stack.setCount(Math.max(1, GsonHelper.getAsInt(json, "count")));
            return stack;
        } else {
            throw new JsonParseException("A Recipe entry needs an input");
        }
    }

    public static Object2IntMap<PressureType> readPressureList(JsonArray array) {
        Object2IntMap<PressureType> pressureMap = new Object2IntOpenHashMap<>();

        for (int i = 0; i < array.size(); ++i) {
            PressureType type = pressureElementDeserialize(array.get(i));
            int amount = amountElementDeserialize(array.get(i));
            pressureMap.put(type, amount);
        }

        return pressureMap;
    }

    private static PressureType pressureElementDeserialize(@Nullable JsonElement json) {
        if (json != null && !json.isJsonNull()) {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                return deserializePressure(jsonObject);
            }
        }
        return SPIRITUAL.get();
    }

    private static PressureType deserializePressure(JsonObject json) {
        if (json.has("pressureType")) {
            return PressureType.getTypeFromName(GsonHelper.getAsString(json, "pressureType"));
        }
        return SPIRITUAL.get();
    }

    private static int amountElementDeserialize(@Nullable JsonElement json) {
        if (json != null && !json.isJsonNull()) {
            if (json.isJsonObject()) {
                JsonObject jsonObject = json.getAsJsonObject();
                return deserializePressureAmount(jsonObject);
            }
        }
        return 0;
    }

    private static int deserializePressureAmount(JsonObject json) {
        if (json.has("amount")) {
            return GsonHelper.getAsInt(json, "amount");
        }
        return 0;
    }

    public static FluidStack deserializeFluid(JsonObject object, String prefix) {
        String s = GsonHelper.getAsString(object, "fluid" + prefix);
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(s));
        int i = GsonHelper.getAsInt(object, "amount" + prefix, 100);
        return new FluidStack(fluid, i);
    }

}
