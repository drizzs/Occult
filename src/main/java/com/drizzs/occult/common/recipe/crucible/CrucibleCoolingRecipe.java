package com.drizzs.occult.common.recipe.crucible;

import com.drizzs.occult.api.capability.PressureType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_COOLING;
import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_COOLING_SERIALIZER;

public class CrucibleCoolingRecipe extends AbstractCrucibleRecipe{

    private final FluidStack fluidIn;
    private final ItemStack itemOut;

    public CrucibleCoolingRecipe(ResourceLocation id, FluidStack fluidIn, ItemStack itemOut, int maxTemp, int minTemp, int cookTime, Object2IntMap<PressureType> pressureCreated, float experience) {
        super(CRUCIBLE_COOLING.get(), id, maxTemp, minTemp, cookTime, pressureCreated, experience);
        this.fluidIn = fluidIn;
        this.itemOut = itemOut;
    }

    public FluidStack getFluidIn() {
        return fluidIn;
    }

    public ItemStack getItemOut() {
        return itemOut;
    }


    @Override
    public boolean matches(@NotNull SimpleContainer container, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer container) {
        return getItemOut();
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return getItemOut().copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return CRUCIBLE_COOLING_SERIALIZER.get();
    }

    @Override
    public boolean matches(List<ItemStack> items, FluidStack fluid) {
        if(!fluid.isEmpty()){
            if(fluidIn.isFluidEqual(fluidIn)){
                return fluid.getAmount() >= fluidIn.getAmount();
            }
        }
        return false;
    }

}
