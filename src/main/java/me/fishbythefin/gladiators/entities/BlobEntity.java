package me.fishbythefin.gladiators.entities;


import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BlobEntity extends ThrowableItemProjectile {
    private int bouncesLeft = 4;
    private boolean isSpawningAnother;
    private BlobEntity blobToAdd;
    private int spawnDelay;

    public BlobEntity(EntityType<? extends ThrowableItemProjectile> p_37466_, Level p_37467_) {
        super(p_37466_, p_37467_);
    }

    @Override
    public void tick() {
        if (isSpawningAnother) {
            if (spawnDelay <= 0) {
                super.tick();
            } else {
                this.spawnDelay--;
                if (spawnDelay <= 0) {
                    if (!this.level.isClientSide) {
                        this.level.addFreshEntity(blobToAdd);
                    }
                }
            }
        }
        else {
            super.tick();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            hitResult.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity)entity).setProjectile(), 7.5F);
            //hitResult.getEntity().invulnerableTime = 0;
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        super.onHitBlock(hitResult);
        if (!this.level.isClientSide) {
            bouncesLeft--;
            if (bouncesLeft <= 0) {
                this.discard();
            } else {
                //West and East means it hit from a change in x value
                //North and South are z
                //Up and Down are y

                //Flips the velocity on a given axis if the blob hit the block due to a change on that axis
                this.setDeltaMovement(this.getDeltaMovement().multiply((hitResult.getDirection().equals(Direction.EAST) || hitResult.getDirection().equals(Direction.WEST)) ? -1.0d : 1.0d, (hitResult.getDirection().equals(Direction.UP) || hitResult.getDirection().equals(Direction.DOWN)) ? -1.0d : 1.0d, (hitResult.getDirection().equals(Direction.NORTH) || hitResult.getDirection().equals(Direction.SOUTH)) ? -1.0d : 1.0d));
                this.setPos(this.getPosition(1.0f).add(this.getDeltaMovement()));

                //Plays noise: doesn't work :(
//                for (Player player:this.level.players()) {
//                    this.level.playSound(player, hitResult.getBlockPos(), SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 1.0f, 1.0f);
//                }
            }
        }
    }

    @Override
    protected float getGravity() {
        return 0.15f;
    }

    @Override
    protected Item getDefaultItem() {
        return RegistryHandler.BLOB_ITEM.get();
    }

    public void setSpawnDelay(int delay) {
        this.spawnDelay = delay;
    }

    public void setSpawningAnother(boolean isSpawningAnother) {
        this.isSpawningAnother = isSpawningAnother;
    }

    public void setBlobToAdd(BlobEntity blob) {
        this.blobToAdd = blob;
    }

}
