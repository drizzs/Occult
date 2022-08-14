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

import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_COOKING;
import static com.drizzs.occult.register.OcRecipes.CRUCIBLE_COOKING_SERIALIZER;

public class CrucibleCookingRecipe extends AbstractCrucibleRecipe{

    private final NonNullList<ItemStack> itemsIn;
    private final ItemStack itemOut;

    public CrucibleCookingRecipe(ResourceLocation id, NonNullList<ItemStack> itemsIn, ItemStack itemOut, int maxTemp, int minTemp, int cookTime, Object2IntMap<PressureType> pressureCreated, float experience) {
        super(CRUCIBLE_COOKING.get(), id, maxTemp, minTemp, cookTime, pressureCreated, experience);
        this.itemsIn = itemsIn;
        this.itemOut = itemOut;
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
        return CRUCIBLE_COOKING_SERIALIZER.get();
    }

    public ItemStack getItemOut() {
        return itemOut;
    }

    public NonNullList<ItemStack> getItemsIn() {
        return itemsIn;
    }

    @Override
    public boolean matches(List<ItemStack> items, FluidStack fluid) {
        if(fluid.isEmpty()) {
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
        }
        return false;
    }
}
