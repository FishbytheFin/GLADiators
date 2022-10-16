package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.gay.PlayerGayTimerProvider;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GayRayItem extends Item {
    public GayRayItem() {
        super(new Item.Properties()
                .stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            Vec3 startingLocation = player.getEyePosition(); //Start of view vector
            Vec3 addition = player.getLookAngle().multiply(15.0d, 15.0d, 15.0d); //Vector to add
            //Returns the hitResult of the enemy the player is looking at(can be a null entity)
            EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level, player, startingLocation, startingLocation.add(addition), player.getBoundingBox().expandTowards(player.getDeltaMovement()).inflate(15.0d), (val) -> true);

            //Draws particles
            spawnGayParticles((ServerLevel) level, startingLocation, startingLocation.add(addition));

            //Adds gay
            if (hitResult != null) { //Checks if the player is looking at another player
                if (hitResult.getEntity() instanceof Player gayPlayer) {
                    //makes the gayPlayer's screen fill with rainbows
                    gayPlayer.getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(playerGayTimer -> {
                        playerGayTimer.addGayTime(200L);
                    });
                }
                player.getCooldowns().addCooldown(this, 250); //Adds a cooldown of 12.5 seconds for hitting the target
            } else {
                player.getCooldowns().addCooldown(this, 400); //Adds a cooldown of 20 seconds for missing
            }

        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.gay_ray").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.MEH);
        super.appendHoverText(itemStack, level, components, flag);
    }

    private void spawnGayParticles(ServerLevel level, Vec3 starting, Vec3 ending) {
        //A vector from point a to point b:
        Vec3 differenceVector = new Vec3(ending.x-starting.x, ending.y-starting.y, ending.z-starting.z);
        //A unit vector with the same angle
        Vec3 incrementalVector = differenceVector.multiply(1 / differenceVector.length(), 1 / differenceVector.length(), 1 / differenceVector.length());
        //Loops for each block hit by the difference vector
        for (int i = 0; i < 2 * differenceVector.length(); i++) {
            level.sendParticles(RegistryHandler.RAINBOW_PARTICLE.get(), starting.x() + ((float)i / 2 * incrementalVector.x), starting.y() + ((float)i / 2 * incrementalVector.y), starting.z() + ((float)i / 2 * incrementalVector.z), 5, 0.1d, 0.1d, 0.1d, 0.1d);
        }
    }
}
