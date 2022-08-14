package com.drizzs.occult.common.recipe.crucible.serializer;

import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.common.recipe.crucible.CrucibleDippingRecipe;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

import static com.drizzs.occult.util.StaticJsonHelper.*;

public class CrucibleDippingSerializer implements RecipeSerializer<CrucibleDippingRecipe> {
    @Override
    public CrucibleDippingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Object2IntMap<PressureType> pressureMap = readPressureList(GsonHelper.getAsJsonArray(json, "pressureReleased"));
        NonNullList<ItemStack> itemsIn = readItemList(GsonHelper.getAsJsonArray(json, "itemInputs"));
        FluidStack fluidIn = deserializeFluid(json,"");
        ItemStack itemOut = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"result"));
        int experience = GsonHelper.getAsInt(json,"experience",0);
        int maxTemp = GsonHelper.getAsInt(json,"maxTemp",1500);
        int minTemp = GsonHelper.getAsInt(json,"minTemp",250);
        int cookTime = GsonHelper.getAsInt(json,"cookTime",100);
        return new CrucibleDippingRecipe(recipeId,itemsIn,fluidIn,itemOut,maxTemp,minTemp,cookTime,pressureMap,experience);
    }

    @Override
    public @Nullable CrucibleDippingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        int inSize = buffer.readInt();
        NonNullList<ItemStack> itemsIn = NonNullList.create();
        ;
        for (int x = 0; x < inSize; ++x) {
            itemsIn.add(buffer.readItem());
        }

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
        return new CrucibleDippingRecipe(recipeId,itemsIn,fluidIn,itemOut,maxTemp,minTemp,cookTime,pressureMap,experience);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, CrucibleDippingRecipe recipe) {
        buffer.writeInt(recipe.getItemsIn().size());
        for (ItemStack stack : recipe.getItemsIn()) {
            buffer.writeItem(stack);
        }

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
