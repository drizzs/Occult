package com.drizzs.occult.common.blockentity;

import com.drizzs.occult.api.capability.PressureType;
import com.drizzs.occult.common.blockentity.base.BaseMachineEntity;
import com.drizzs.occult.common.recipe.crucible.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.drizzs.occult.api.capability.PressureCap.addChunkPressure;
import static com.drizzs.occult.register.OcBlockEntities.CRUCIBLE_BE;
import static com.drizzs.occult.register.OcRecipes.*;
import static com.drizzs.occult.register.OcTags.*;

public class CrucibleBE extends BaseMachineEntity {

    private final int capacity = 1296;
    private final FluidTank tank = new FluidTank(capacity);
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> tank);

    private AbstractCrucibleRecipe currentRecipe;

    public CrucibleBE(BlockPos pos, BlockState state) {
        super(CRUCIBLE_BE.get(), 5, pos, state);
    }

    @Override
    public boolean isActive() {
        assert level != null;
        return level.getBlockState(getBlockPos().below()).isBurning(level,getBlockPos().below());
    }

    @Override
    public void tick() {
        if (this.requiresUpdate && this.level != null) {
            updateEntity();
            this.requiresUpdate = false;
        }
        if (isHot()) {
            matchRecipe();
            if (getCurrentRecipe() != null) {
                if (getMachineType() == 1 && !isFluidFull()) {
                    if (getTempFromBelow() >= getMeltingRecipe().getMinTemp() && getTempFromBelow() <= getMeltingRecipe().getMaxTemp()) {
                        meltMyItems();
                    }
                } else if (getMachineType() == 2 ){
                    if (getTempFromBelow() >= getMixingRecipe().getMinTemp() && getTempFromBelow() <= getMixingRecipe().getMaxTemp()) {
                        mixMyItemsNFluid();
                    }
                }else if (getMachineType() == 3 ){
                    if (getTempFromBelow() >= getDippingRecipe().getMinTemp() && getTempFromBelow() <= getDippingRecipe().getMaxTemp()) {
                        dipMyItemsInFluid();
                    }
                }else if (getMachineType() == 4 ){
                    if (getTempFromBelow() >= getCookingRecipe().getMinTemp() && getTempFromBelow() <= getCookingRecipe().getMaxTemp()) {
                        cookMyItems();
                    }
                }
            } else {
                resetProgress();
            }
        } else if (!getFluidStack().isEmpty() && !isHot()) {
            matchCoolingRecipe();
            if (getCurrentRecipe() != null) {
                if (getMachineType() == 5) {
                    if (getItemInSlot(3).isEmpty() || getItemInSlot(3).getItem().equals(getCoolingRecipe().getItemOut().getItem()) && getItemInSlot(3).getCount() + getCoolingRecipe().getItemOut().getCount() <=64) {
                        if (getTempFromBelow() <= getCoolingRecipe().getMinTemp() && getTempFromBelow() >= getCoolingRecipe().getMaxTemp())
                            coolMyItems();
                    }
                }
            } else {
                resetProgress();
            }
        }
    }

    public LazyOptional<IFluidHandler> getFluidHandler() {
        return fluidHandler;
    }

    public FluidStack getFluidStack() {
        return getFluidHandler().map(fluid ->
                fluid.getFluidInTank(0)
        ).orElse(FluidStack.EMPTY);
    }

    public void setFluidStack(FluidStack stack) {
        if (getFluidStack().isEmpty()) {
            getFluidHandler().ifPresent(tank -> tank.fill(stack, IFluidHandler.FluidAction.EXECUTE));
        }
    }

    public boolean isFluidFull() {
        return getFluidHandler().map(tank -> tank.getFluidInTank(0).getAmount() == tank.getTankCapacity(0)).orElse(true);
    }

    public int getCapacity() {
        return capacity;
    }

    public List<ItemStack> getItemList() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int x = 0; x < 3; ++x) {
            if (!getItemInSlot(x).isEmpty()) {
                stacks.add(getItemInSlot(x));
            }
        }
        return stacks;
    }

    private void matchRecipe() {
        if (level != null) {
            currentRecipe = level.getRecipeManager()
                    .getRecipes()
                    .stream()
                    .filter(recipe -> recipe instanceof AbstractCrucibleRecipe && !recipe.getType().equals(CRUCIBLE_COOLING.get()))
                    .map(recipe -> (AbstractCrucibleRecipe) recipe)
                    .filter(recipe -> matchRecipe(recipe, getItemList(), getFluidStack()))
                    .findFirst()
                    .orElse(null);
        }
    }

    private void matchCoolingRecipe() {
        if (level != null) {
            currentRecipe = level.getRecipeManager()
                    .getRecipes()
                    .stream()
                    .filter(recipe ->  recipe instanceof CrucibleCoolingRecipe)
                    .map(recipe -> (AbstractCrucibleRecipe) recipe)
                    .filter(recipe -> matchRecipe(recipe, getItemList(), getFluidStack()))
                    .findFirst()
                    .orElse(null);
        }
    }

    private boolean matchRecipe(AbstractCrucibleRecipe recipe, List<ItemStack> list, FluidStack stack) {
        return recipe.matches(list, stack);
    }

    private boolean isHot() {
        return getTempFromBelow() > 249;
    }

    public AbstractCrucibleRecipe getCurrentRecipe() {
        return currentRecipe;
    }

    private int getMachineType() {
        if (getCurrentRecipe().getType() == CRUCIBLE_MELTING.get()) {
            return 1;
        } else if (getCurrentRecipe().getType() == CRUCIBLE_MIXING.get()) {
            return 2;
        } else if (getCurrentRecipe().getType() == CRUCIBLE_DIPPING.get()) {
            return 3;
        } else if (getCurrentRecipe().getType() == CRUCIBLE_COOKING.get()) {
            return 4;
        } else if (getCurrentRecipe().getType() == CRUCIBLE_COOLING.get()) {
            return 5;
        }
        return 0;
    }

    private CrucibleMeltingRecipe getMeltingRecipe() {
        return (CrucibleMeltingRecipe) getCurrentRecipe();
    }

    private CrucibleCoolingRecipe getCoolingRecipe() {
        return (CrucibleCoolingRecipe) getCurrentRecipe();
    }

    private CrucibleCookingRecipe getCookingRecipe() {
        return (CrucibleCookingRecipe) getCurrentRecipe();
    }

    private CrucibleMixingRecipe getMixingRecipe() {
        return (CrucibleMixingRecipe) getCurrentRecipe();
    }

    private CrucibleDippingRecipe getDippingRecipe() {
        return (CrucibleDippingRecipe) getCurrentRecipe();
    }

    private void meltMyItems() {
        if (getCurrentRecipe() != null && getProgress() >= getMeltingRecipe().getCookTime()) {
            int itemsToRemove = resolveRecipeItemRemoval(getMeltingRecipe().getItemsIn(), getMeltingRecipe().getItemsIn().size());
            if (itemsToRemove == 0) {
                addFluidToTank(getMeltingRecipe().getFluidOut());
                addPressureFromRecipe();
                resetProgress();
                this.requiresUpdate = true;
            }
        } else {
            addProgress();
        }
    }

    private void cookMyItems(){
        if (getCurrentRecipe() != null && getProgress() >= getCookingRecipe().getCookTime()) {
            int itemsToRemove = resolveRecipeItemRemoval(getCookingRecipe().getItemsIn(), getCookingRecipe().getItemsIn().size());
            if (itemsToRemove == 0) {
                insertItem(3,getCookingRecipe().getItemOut().copy());
                addPressureFromRecipe();
                resetProgress();
                this.requiresUpdate = true;
            }
        }else {
            addProgress();
        }
    }

    private void dipMyItemsInFluid(){
        if (getCurrentRecipe() != null && getProgress() >= getDippingRecipe().getCookTime()) {
            int itemsToRemove = resolveRecipeItemRemoval(getDippingRecipe().getItemsIn(), getDippingRecipe().getItemsIn().size());
            if (itemsToRemove == 0 && wasFluidRemoved(getDippingRecipe().getFluidIn())) {
                insertItem(3,getDippingRecipe().getItemOut().copy());
                addPressureFromRecipe();
                resetProgress();
                this.requiresUpdate = true;
            }
        }else {
            addProgress();
        }
    }

    private void mixMyItemsNFluid(){
        if (getCurrentRecipe() != null && getProgress() >= getMixingRecipe().getCookTime()) {
            int itemsToRemove = resolveRecipeItemRemoval(getMixingRecipe().getItemsIn(), getMixingRecipe().getItemsIn().size());
            if (itemsToRemove == 0 && wasFluidRemoved(getMixingRecipe().getFluidIn())) {
                setFluidStack(getMixingRecipe().getFluidOut());
                addPressureFromRecipe();
                resetProgress();
                this.requiresUpdate = true;
            }
        }else {
            addProgress();
        }
    }

    private void coolMyItems(){
        if (getCurrentRecipe() != null && getProgress() >= getCoolingRecipe().getCookTime()) {
            if (wasFluidRemoved(getCoolingRecipe().getFluidIn())) {
                insertItem(6,getCoolingRecipe().getItemOut().copy());
                addPressureFromRecipe();
                resetProgress();
                this.requiresUpdate = true;
            }
        }else {
            addProgress();
        }
    }

    public InteractionResult insertExtractItem(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && !player.isCrouching()) {
            for (int x = 0; x < 5; ++x) {
                ItemStack targetSlot = getItemInSlot(x);
                if (targetSlot.isEmpty()) {
                    insertItem(x, stack);
                    return InteractionResult.SUCCESS;
                } else if (targetSlot.getItem().equals(stack.getItem()) && targetSlot.getCount() != 64) {
                    if (targetSlot.getCount() + stack.getCount() != 64) {
                        targetSlot.setCount(targetSlot.getCount() + stack.getCount());
                        stack.shrink(stack.getCount());
                    } else {
                        int difference = 64 - targetSlot.getCount();
                        targetSlot.setCount(64);
                        stack.shrink(difference);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            if (!getItemInSlot(3).isEmpty()) {
                player.addItem(extractItem(3));
                return InteractionResult.SUCCESS;
            } else {
                for (int x = 0; x < 3; ++x) {
                    if (!getItemInSlot(x).isEmpty()) {
                        player.addItem(extractItem(x));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    private boolean wasFluidRemoved(FluidStack stack){
        if(!getFluidStack().isEmpty()){
            getFluidHandler().ifPresent(tank -> tank.drain(stack, IFluidHandler.FluidAction.EXECUTE));
            return true;
        }
        return false;
    }

    private void addFluidToTank(FluidStack stack) {
        getFluidHandler().ifPresent(tank -> tank.fill(stack, IFluidHandler.FluidAction.EXECUTE));
    }

    private int resolveRecipeItemRemoval(List<ItemStack> recipeItems, int size) {
        int itemsToRemove = size;
        for (int x = 0; x < 3; ++x) {
            if (itemsToRemove != 0) {
                ItemStack stack = getItemInSlot(x);
                for (ItemStack item : recipeItems) {
                    if (stack.getItem().equals(item.getItem())) {
                        int count = item.getCount();
                        stack.shrink(count);
                        itemsToRemove--;
                    }
                }
            } else {
                break;
            }
        }
        return itemsToRemove;
    }

    private void addPressureFromRecipe() {
        for (PressureType type : currentRecipe.getPressureCreated().keySet()) {
            assert level != null;
            if (!level.isClientSide()) {
                addChunkPressure(level.getChunkAt(getBlockPos()), type, currentRecipe.getPressureCreated().getInt(type));
            }
        }
    }

    private int getTempFromBelow() {
        if (level != null) {
            BlockPos pos = getBlockPos().below();
            BlockState belowState = level.getBlockState(pos);
            if (belowState.is(LOW_HEAT) || belowState.getBlock().isBurning(belowState,level,pos)) {
                return 250;
            } else if (belowState.is(MEDIUM_HEAT)) {
                return 650;
            } else if (belowState.is(HIGH_HEAT)) {
                return 1000;
            } else if (belowState.is(EXTREME_HEAT)) {
                return 1500;
            } else if (belowState.is(INFERNAL_HEAT)) {
                return 2000;
            }
        }
        return 0;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = super.serializeNBT();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        tank.readFromNBT(tag);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tank.writeToNBT(tag);
        super.saveAdditional(tag);
    }
}
