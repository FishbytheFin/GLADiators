package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.gay.PlayerGayTimerProvider;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

            if (hitResult != null && hitResult.getEntity() instanceof Player) { //Checks if the player is looking at another player
                //WILL MAKE THE PLAYER SEE RAINBOWS N' STUFF. NOT CURRENTLY WORKING!
                Player gayPlayer = (Player) hitResult.getEntity();
                gayPlayer.getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(playerGayTimer -> {
                    playerGayTimer.addGayTime(200L);
                });            }

            player.getCooldowns().addCooldown(this, 20); //Adds a cooldown
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.gay_ray").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.MEH);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
