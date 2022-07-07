package com.drizzs.occult.common.block;


import com.drizzs.occult.api.PressureType;
import com.drizzs.occult.api.capability.PressureCap;
import com.drizzs.occult.common.blockentity.RitualFireBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.drizzs.occult.register.OcPressure.PRESSURE;


public class RitualFireBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    public static final BooleanProperty LIT;
    public static final BooleanProperty SIGNAL_FIRE;
    public static final BooleanProperty WATERLOGGED;
    public static final DirectionProperty FACING;
    private static final VoxelShape VIRTUAL_FENCE_POST;
    private static final int SMOKE_DISTANCE = 5;
    private final boolean spawnParticles;
    private final int fireDamage;

    public RitualFireBlock() {
        super(Block.Properties.of(Material.WOOD).color(MaterialColor.COLOR_BLUE).lightLevel(value -> 5));
        this.spawnParticles = true;
        this.fireDamage = 1;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, true).setValue(SIGNAL_FIRE, false).setValue(WATERLOGGED, false).setValue(FACING, Direction.NORTH));

    }

    @Override
    public void entityInside(BlockState state, @NotNull Level level, BlockPos pos, Entity entity) {
        if (state.getValue(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(DamageSource.IN_FIRE, (float)this.fireDamage);
        }else if(state.getValue(LIT) && entity instanceof ItemEntity ie){
                ItemStack item = ie.getItem();
                item.getTags().forEach(itemTagKey -> {
                    if (PRESSURE.getEntries().stream().anyMatch(object -> itemTagKey.location().toString().contains(object.getId().getPath()))) {
                        if (!ie.isRemoved()) {
                            ie.remove(Entity.RemovalReason.DISCARDED);
                        }
                        int amount = Integer.parseInt(itemTagKey.location().toString().replaceAll("\\D", ""));

                        String name = itemTagKey.location().getPath().replaceAll("\\d", "").replace("_", "");

                        LevelChunk chunk = level.getChunkAt(pos);

                        PressureCap.addChunkPressure(chunk, PressureType.getTypeFromName(name), amount);

                    }
                });
        }

        super.entityInside(state, level, pos, entity);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_51240_) {
        LevelAccessor levelaccessor = p_51240_.getLevel();
        BlockPos blockpos = p_51240_.getClickedPos();
        boolean flag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag).setValue(SIGNAL_FIRE, this.isSmokeSource(levelaccessor.getBlockState(blockpos.below()))).setValue(LIT, !flag).setValue(FACING, p_51240_.getHorizontalDirection());
    }

    @Override
    public BlockState updateShape(BlockState p_51298_, Direction p_51299_, BlockState p_51300_, LevelAccessor p_51301_, BlockPos p_51302_, BlockPos p_51303_) {
        if (p_51298_.getValue(WATERLOGGED)) {
            p_51301_.scheduleTick(p_51302_, Fluids.WATER, Fluids.WATER.getTickDelay(p_51301_));
        }

        return p_51299_ == Direction.DOWN ? p_51298_.setValue(SIGNAL_FIRE, this.isSmokeSource(p_51300_)) : super.updateShape(p_51298_, p_51299_, p_51300_, p_51301_, p_51302_, p_51303_);
    }

    private boolean isSmokeSource(BlockState p_51324_) {
        return p_51324_.is(Blocks.HAY_BLOCK);
    }

    public VoxelShape getShape(BlockState p_51309_, BlockGetter p_51310_, BlockPos p_51311_, CollisionContext p_51312_) {
        return SHAPE;
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_51307_) {
        return RenderShape.MODEL;
    }

    public void animateTick(BlockState p_220918_, Level p_220919_, BlockPos p_220920_, RandomSource p_220921_) {
        if (p_220918_.getValue(LIT)) {
            if (p_220921_.nextInt(10) == 0) {
                p_220919_.playLocalSound((double)p_220920_.getX() + 0.5, (double)p_220920_.getY() + 0.5, (double)p_220920_.getZ() + 0.5, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + p_220921_.nextFloat(), p_220921_.nextFloat() * 0.7F + 0.6F, false);
            }

            if (this.spawnParticles && p_220921_.nextInt(5) == 0) {
                for(int i = 0; i < p_220921_.nextInt(1) + 1; ++i) {
                    p_220919_.addParticle(ParticleTypes.LAVA, (double)p_220920_.getX() + 0.5, (double)p_220920_.getY() + 0.5, (double)p_220920_.getZ() + 0.5, (double)(p_220921_.nextFloat() / 2.0F), 5.0E-5, (double)(p_220921_.nextFloat() / 2.0F));
                }
            }
        }

    }

    public static void dowse(@Nullable Entity p_152750_, LevelAccessor p_152751_, BlockPos p_152752_, BlockState p_152753_) {
        if (p_152751_.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                makeParticles((Level)p_152751_, p_152752_, p_152753_.getValue(SIGNAL_FIRE), true);
            }
        }

        BlockEntity blockentity = p_152751_.getBlockEntity(p_152752_);
        if (blockentity instanceof CampfireBlockEntity) {
            ((CampfireBlockEntity)blockentity).dowse();
        }

        p_152751_.gameEvent(p_152750_, GameEvent.BLOCK_CHANGE, p_152752_);
    }

    public boolean placeLiquid(LevelAccessor p_51257_, BlockPos p_51258_, BlockState p_51259_, FluidState p_51260_) {
        if (!(Boolean)p_51259_.getValue(BlockStateProperties.WATERLOGGED) && p_51260_.getType() == Fluids.WATER) {
            boolean flag = p_51259_.getValue(LIT);
            if (flag) {
                if (!p_51257_.isClientSide()) {
                    p_51257_.playSound(null, p_51258_, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                dowse(null, p_51257_, p_51258_, p_51259_);
            }

            p_51257_.setBlock(p_51258_, p_51259_.setValue(WATERLOGGED, true).setValue(LIT, false), 3);
            p_51257_.scheduleTick(p_51258_, p_51260_.getType(), p_51260_.getType().getTickDelay(p_51257_));
            return true;
        } else {
            return false;
        }
    }

    public void onProjectileHit(Level p_51244_, BlockState p_51245_, BlockHitResult p_51246_, Projectile p_51247_) {
        BlockPos blockpos = p_51246_.getBlockPos();
        if (!p_51244_.isClientSide && p_51247_.isOnFire() && p_51247_.mayInteract(p_51244_, blockpos) && !(Boolean)p_51245_.getValue(LIT) && !(Boolean)p_51245_.getValue(WATERLOGGED)) {
            p_51244_.setBlock(blockpos, p_51245_.setValue(BlockStateProperties.LIT, true), 11);
        }

    }

    public static void makeParticles(Level p_51252_, BlockPos p_51253_, boolean p_51254_, boolean p_51255_) {
        RandomSource randomsource = p_51252_.getRandom();
        SimpleParticleType simpleparticletype = p_51254_ ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        p_51252_.addAlwaysVisibleParticle(simpleparticletype, true, (double)p_51253_.getX() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1), (double)p_51253_.getY() + randomsource.nextDouble() + randomsource.nextDouble(), (double)p_51253_.getZ() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        if (p_51255_) {
            p_51252_.addParticle(ParticleTypes.SMOKE, (double)p_51253_.getX() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1), (double)p_51253_.getY() + 0.4, (double)p_51253_.getZ() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
        }

    }

    public static boolean isSmokeyPos(Level p_51249_, BlockPos p_51250_) {
        for(int i = 1; i <= 5; ++i) {
            BlockPos blockpos = p_51250_.below(i);
            BlockState blockstate = p_51249_.getBlockState(blockpos);
            if (isLitCampfire(blockstate)) {
                return true;
            }

            boolean flag = Shapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, blockstate.getCollisionShape(p_51249_, blockpos, CollisionContext.empty()), BooleanOp.AND);
            if (flag) {
                BlockState blockstate1 = p_51249_.getBlockState(blockpos.below());
                return isLitCampfire(blockstate1);
            }
        }

        return false;
    }

    public static boolean isLitCampfire(BlockState p_51320_) {
        return p_51320_.hasProperty(LIT) && p_51320_.is(BlockTags.CAMPFIRES) && p_51320_.getValue(LIT);
    }

    public FluidState getFluidState(BlockState p_51318_) {
        return p_51318_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_51318_);
    }

    public BlockState rotate(BlockState p_51295_, Rotation p_51296_) {
        return p_51295_.setValue(FACING, p_51296_.rotate(p_51295_.getValue(FACING)));}

    public BlockState mirror(BlockState p_51292_, Mirror p_51293_) {
        return p_51292_.rotate(p_51293_.getRotation(p_51292_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51305_) {
        p_51305_.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos p_152759_, @NotNull BlockState p_152760_) {
        return new RitualFireBlockEntity(p_152759_, p_152760_);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152755_, BlockState p_152756_, BlockEntityType<T> p_152757_) {
        if (p_152755_.isClientSide) {
            return p_152756_.getValue(LIT) ? createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, CampfireBlockEntity::particleTick) : null;
        } else {
            return p_152756_.getValue(LIT) ? createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cookTick) : createTickerHelper(p_152757_, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cooldownTick);
        }
    }

    public boolean isPathfindable(BlockState p_51264_, BlockGetter p_51265_, BlockPos p_51266_, PathComputationType p_51267_) {
        return false;
    }

    public static boolean canLight(BlockState p_51322_) {
        return p_51322_.is(BlockTags.CAMPFIRES, (p_51262_) -> {
            return p_51262_.hasProperty(WATERLOGGED) && p_51262_.hasProperty(LIT);
        }) && !(Boolean)p_51322_.getValue(WATERLOGGED) && !(Boolean)p_51322_.getValue(LIT);
    }

    static {
        LIT = BlockStateProperties.LIT;
        SIGNAL_FIRE = BlockStateProperties.SIGNAL_FIRE;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        VIRTUAL_FENCE_POST = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    }
}
