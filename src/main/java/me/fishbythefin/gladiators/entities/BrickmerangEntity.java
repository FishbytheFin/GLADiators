package me.fishbythefin.gladiators.entities;

import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class BrickmerangEntity extends ThrowableItemProjectile {
    private boolean isComingBack = false;
    private int ticksLeft;

    public BrickmerangEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }


    @Override
    public void tick() {
        if (!isComingBack) {
            super.tick();
        } else if (ticksLeft == 0) {
            this.discard();
            if (this.getOwner() instanceof Player player) {
                int locLower = player.getInventory().findSlotMatchingItem(new ItemStack(RegistryHandler.LOWER_HALF_BRICKMERANG.get()));
                if (locLower >= 0) {
                    player.getInventory().setItem(locLower, new ItemStack(RegistryHandler.BRICKMERANG.get()));
                    player.getCooldowns().addCooldown(RegistryHandler.BRICKMERANG.get(), 100);
                } else {
                    if (player.getHealth() > 0) {
                        //Gives the brickmerang half to an alive player
                        player.addItem(new ItemStack(RegistryHandler.UPPER_HALF_BRICKMERANG.get()));
                    } else {
                        //Drops the top half brickmerang item if player is dead
                        this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegistryHandler.UPPER_HALF_BRICKMERANG.get())));
                    }
                }
            }
        } else {
            Vec3 deltaMovement = this.getDeltaMovement();
            double dx = this.getX() + deltaMovement.x;
            double dy = this.getY() + deltaMovement.y;
            double dz = this.getZ() + deltaMovement.z;
            this.setPos(dx, dy, dz);
            ticksLeft--;
        }


    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = this.getOwner();
        this.isComingBack = true;
        this.ticksLeft = 4;
        Vec3 comeBackToLocation = entity.getEyePosition();
        this.setNoGravity(true);
        this.setDeltaMovement(new Vec3(comeBackToLocation.x - this.position().x, comeBackToLocation.y - this.position().y, comeBackToLocation.z - this.position().z).multiply(0.25d, 0.25d, 0.25d));
        if (entity instanceof LivingEntity) {
            hitResult.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity) entity).setProjectile(), 10.0F);
        }

    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level.isClientSide) {
            if (this.level.getBlockState(hitResult.getBlockPos()).getBlock().getName().toString().toUpperCase().contains("GLASS")) {
                this.level.destroyBlock(hitResult.getBlockPos(), true);
            } else {
                this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(RegistryHandler.UPPER_HALF_BRICKMERANG.get())));
                this.discard();
            }
        }

    }

    @Override
    protected Item getDefaultItem() {
        return RegistryHandler.BRICKMERANG_PROJECTILE_ITEM.get();
    }

    @Override
    protected float getGravity() {
        return 0.1f;
    }

//    @Override
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//    }
}
