package com.drizzs.occult.common.recipe.crucible;

import com.drizzs.occult.api.capability.PressureType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public abstract class AbstractCrucibleRecipe implements Recipe<SimpleContainer> {

    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final float experience;
    protected final int maxTemp;
    protected final int minTemp;
    protected final int cookTime;
    protected final Object2IntMap<PressureType> pressureCreated;

    public AbstractCrucibleRecipe(RecipeType<?> type, ResourceLocation id, int maxTemp, int minTemp ,int cookTime, Object2IntMap<PressureType> pressureCreated, float experience){
        this.type = type;
        this.id = id;
        this.experience = experience;
        this.cookTime = cookTime;
        this.pressureCreated = pressureCreated;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }

    public abstract boolean matches(List<ItemStack> items, FluidStack fluid);

    public Object2IntMap<PressureType> getPressureCreated() {
        return pressureCreated;
    }

    public float getExperience() {
        return experience;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getCookTime() {
        return cookTime;
    }
}
