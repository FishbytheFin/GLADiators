package me.fishbythefin.gladiators.items.custom;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.weapons.GladiatorsItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseballBatItem extends SwordItem {
    public BaseballBatItem() {
        super(GladiatorsItemTier.BASEBALL_BAT, 8, -2.0f, new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            if (level instanceof ServerLevel serverLevel) {
                //Spawns Sweep effect
                double d0 = (-Mth.sin(player.getYRot() * ((float)Math.PI / 180F)));
                double d1 = Mth.cos(player.getYRot() * ((float)Math.PI / 180F));
                serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.75D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);

                //Knocksback all nearby entities

                Vec3 lookVector = player.getLookAngle().multiply(2, 2, 2);
                Vec3 hitboxLocation = new Vec3(player.getX(), player.getEyePosition().y - (player.getEyeHeight() / 2), player.getZ());
                hitboxLocation = hitboxLocation.add(lookVector); //Creates a vec3 that is the position 1.5 blocks from player in the direction they are looking

                double x1 = hitboxLocation.x - 1;
                double y1 = hitboxLocation.y - 1;
                double z1 = hitboxLocation.z - 1;

                double x2 = hitboxLocation.x + 1;
                double y2 = hitboxLocation.y + 1;
                double z2 = hitboxLocation.z + 1;

                //Creates a hitbox at the location of hitboxLocation that is 2x2x2
                AABB pushbackHitbox = new AABB(x1, y1, z1, x2, y2, z2);

                for(Entity entity : serverLevel.getEntities(player, pushbackHitbox)) {
                    //Sets the fireball's direction
                    if (entity instanceof Fireball fireball) {
                        fireball.hurt(DamageSource.playerAttack(player), 1.0f);
                        continue;
                    }

                    //Sets entity's velocity to twice the player's direction
                    entity.setDeltaMovement(player.getLookAngle().multiply(2.0d, 2.0d, 2.0d));
                }

                //Set a cooldown
                player.getCooldowns().addCooldown(this, 100);

            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.baseball_bat").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.TIGHT);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
