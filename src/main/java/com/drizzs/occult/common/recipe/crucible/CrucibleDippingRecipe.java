package com.drizzs.occult.common.recipe.crucible;

import com.drizzs.occult.api.capability.PressureType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_DIPPING;
import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_DIPPING_SERIALIZER;

public class CrucibleDippingRecipe extends AbstractCrucibleRecipe{

    private final NonNullList<ItemStack> itemsIn;
    private final FluidStack fluidIn;
    private final ItemStack itemOut;

    public CrucibleDippingRecipe(ResourceLocation id, NonNullList<ItemStack> itemsIn, FluidStack fluidIn, ItemStack itemOut, int maxTemp, int minTemp, int cookTime, Object2IntMap<PressureType> pressureCreated, float experience) {
        super(CRUCIBLE_DIPPING.get(), id, maxTemp, minTemp, cookTime, pressureCreated, experience);
        this.itemsIn = itemsIn;
        this.fluidIn = fluidIn;
        this.itemOut = itemOut;
    }

    public ItemStack getItemOut() {
        return itemOut;
    }

    public NonNullList<ItemStack> getItemsIn() {
        return itemsIn;
    }

    public FluidStack getFluidIn() {
        return fluidIn;
    }


    @Override
    public boolean matches(SimpleContainer p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_) {
        return getItemOut();
    }

    @Override
    public ItemStack getResultItem() {
        return getItemOut().copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CRUCIBLE_DIPPING_SERIALIZER.get();
    }

    @Override
    public boolean matches(List<ItemStack> items, FluidStack fluid) {
        return false;
    }

}
