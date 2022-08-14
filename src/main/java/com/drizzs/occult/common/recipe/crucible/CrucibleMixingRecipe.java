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
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_MIXING;
import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_MIXING_SERIALIZER;

public class CrucibleMixingRecipe extends AbstractCrucibleRecipe{

    private final NonNullList<ItemStack> itemsIn;
    private final FluidStack fluidIn;
    private final FluidStack fluidOut;

    public CrucibleMixingRecipe(ResourceLocation id, NonNullList<ItemStack> itemsIn, FluidStack fluidIn, FluidStack fluidOut, int maxTemp, int minTemp, int cookTime, Object2IntMap<PressureType> pressureCreated, float experience) {
        super(CRUCIBLE_MIXING.get(), id, maxTemp, minTemp, cookTime, pressureCreated, experience);
        this.itemsIn = itemsIn;
        this.fluidIn = fluidIn;
        this.fluidOut = fluidOut;
    }

    public NonNullList<ItemStack> getItemsIn() {
        return itemsIn;
    }

    public FluidStack getFluidIn() {
        return fluidIn;
    }

    public FluidStack getFluidOut() {
        return fluidOut;
    }


    @Override
    public boolean matches(@NotNull SimpleContainer container, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer container) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CRUCIBLE_MIXING_SERIALIZER.get();
    }

    @Override
    public boolean matches(List<ItemStack> items, FluidStack fluid) {
        return false;
    }

}
