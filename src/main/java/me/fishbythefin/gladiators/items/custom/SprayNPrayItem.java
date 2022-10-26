package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.util.RegistryHandler;
import me.fishbythefin.gladiators.weapons.GladiatorsItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SprayNPrayItem extends PickaxeItem {
    public SprayNPrayItem() {
        super(GladiatorsItemTier.SPRAY_N_PRAY, 6, -2.0f, new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            //NOT WORKING AT ALL:
            //Damns all entities in a 3x3 box in front of the player
            Vec3 endOfSprayHitBox = player.getLookAngle().multiply(4.0d, 0.0d, 4.0d);//End of the hitbox for the spray
            //Adds the damned effect to all living entities near the player:
            for (Entity entity : level.getEntities(player, new AABB(player.getX(), player.getY(), player.getZ(), player.getX() + endOfSprayHitBox.x, player.getY() + 3, player.getZ() + endOfSprayHitBox.z))) {
                player.sendSystemMessage(Component.literal("Type: " + entity.getType()));
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(RegistryHandler.DAMNED_EFFECT.get(), 200));
                    //Particles~
                    spawnDamningParticles(level, livingEntity);//NOT WORKING AS INTENDED
                }
            }




            //Set a cooldown
            player.getCooldowns().addCooldown(this, 100);
        }

        return super.use(level, player, interactionHand);
    }

//    @Override
//    public boolean hurtEnemy(ItemStack itemStack, LivingEntity entity, LivingEntity attacker) {
//        return super.hurtEnemy(itemStack, entity, attacker);
//    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.spray_n_pray").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.TIGHT);
        super.appendHoverText(itemStack, level, components, flag);
    }

    private void spawnDamningParticles(Level level, Entity entity) {
        int numParticles = 30;
        for (int i = 0; i <= numParticles; i++) {
            ((ServerLevel) level).sendParticles(RegistryHandler.DAMNED_PARTICLE.get(), entity.getX(), entity.getY() + entity.getEyeHeight() / 2, entity.getZ(), 1,Math.cos((double) 360 / numParticles) * 0.25d, 0.15d, Math.sin((double) 360 / numParticles) * 0.25d, 0.15d);
        }
    }
}
