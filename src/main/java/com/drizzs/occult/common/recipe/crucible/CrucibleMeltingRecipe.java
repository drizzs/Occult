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

import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_MELTING;
import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_MELTING_SERIALIZER;

public class CrucibleMeltingRecipe extends AbstractCrucibleRecipe{

    private final NonNullList<ItemStack> itemsIn;
    private final FluidStack fluidOut;

    public CrucibleMeltingRecipe(ResourceLocation id, NonNullList<ItemStack> itemsIn, FluidStack fluidOut, int maxTemp, int minTemp, int cookTime, Object2IntMap<PressureType> pressureCreated, float experience) {
        super(CRUCIBLE_MELTING.get(), id, maxTemp, minTemp, cookTime, pressureCreated, experience);
        this.itemsIn = itemsIn;
        this.fluidOut = fluidOut;
    }

    public NonNullList<ItemStack> getItemsIn() {
        return itemsIn;
    }

    public FluidStack getFluidOut() {
        return fluidOut;
    }

    @Override
    public boolean matches(SimpleContainer p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CRUCIBLE_MELTING_SERIALIZER.get();
    }

    @Override
    public boolean matches(List<ItemStack> items, FluidStack fluid) {
        if (!items.isEmpty()) {
            int requiredItems = getItemsIn().size();
            for (ItemStack stack : getItemsIn()) {
                for (ItemStack item : items) {
                    if (stack.getItem().equals(item.getItem())) {
                        if (item.getCount() >= stack.getCount()) {
                            --requiredItems;
                            break;
                        }
                    }
                }
            }
            return requiredItems == 0;
        }
        return false;
    }

}
