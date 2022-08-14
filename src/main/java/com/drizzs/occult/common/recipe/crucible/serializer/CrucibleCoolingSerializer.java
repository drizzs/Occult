package com.drizzs.occult.common.recipe.crucible.serializer;

import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.common.recipe.crucible.CrucibleCoolingRecipe;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.util.StaticJsonHelper.deserializeFluid;
import static com.drizzs.occult.util.StaticJsonHelper.readPressureList;

public class CrucibleCoolingSerializer implements RecipeSerializer<CrucibleCoolingRecipe> {
    @Override
    public CrucibleCoolingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Object2IntMap<PressureType> pressureMap = readPressureList(GsonHelper.getAsJsonArray(json, "pressureReleased"));
        FluidStack fluidIn = deserializeFluid(json,"In");
        ItemStack itemOut = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"result"));
        int experience = GsonHelper.getAsInt(json,"experience",0);
        int maxTemp = GsonHelper.getAsInt(json,"maxTemp",1500);
        int minTemp = GsonHelper.getAsInt(json,"minTemp",250);
        int cookTime = GsonHelper.getAsInt(json,"coolTime",100);
        return new CrucibleCoolingRecipe(recipeId,fluidIn,itemOut,maxTemp,minTemp,cookTime,pressureMap,experience);
    }

    @Override
    public @Nullable CrucibleCoolingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        FluidStack fluidIn = buffer.readFluidStack();

        ItemStack itemOut = buffer.readItem();

        int maxTemp = buffer.readInt();
        int minTemp = buffer.readInt();
        int cookTime = buffer.readInt();
        float experience = buffer.readFloat();

        Object2IntMap<PressureType> pressureMap = new Object2IntOpenHashMap<>();
        int pressureSize = buffer.readInt();
        for (int x = 0; x < pressureSize; ++x) {
            PressureType type = PressureType.getTypeFromName(buffer.readUtf());
            pressureMap.put(type, buffer.readInt());
        }
        return new CrucibleCoolingRecipe(recipeId,fluidIn,itemOut,maxTemp,minTemp,cookTime,pressureMap,experience);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, CrucibleCoolingRecipe recipe) {
        buffer.writeFluidStack(recipe.getFluidIn());

        buffer.writeItem(recipe.getItemOut());

        buffer.writeInt(recipe.getMaxTemp());
        buffer.writeInt(recipe.getMinTemp());
        buffer.writeInt(recipe.getCookTime());
        buffer.writeFloat(recipe.getExperience());

        buffer.writeInt(recipe.getPressureCreated().size());
        for (PressureType type : recipe.getPressureCreated().keySet()) {
            buffer.writeUtf(type.getId());
            buffer.writeInt(recipe.getPressureCreated().get(type));
        }

    }
}
