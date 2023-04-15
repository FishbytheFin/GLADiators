package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.band.PlayerRubberBandProvider;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RubberBandItem extends Item {
    public RubberBandItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BOW;
    }
    public int getUseDuration(ItemStack p_43419_) {
        return 72000;
    }


    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int duration ) {

        int i = this.getUseDuration(itemStack) - duration;

        if (!level.isClientSide) { //Server level code
            if (livingEntity instanceof Player player) { //It is a player
                player.getCapability(PlayerRubberBandProvider.PLAYER_RUBBER_BAND).ifPresent(playerRubberBand -> {
                    if (playerRubberBand.getRubberBandBounces() == 0) {
                    //Eating Sound:
                    level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), itemStack.getEatingSound(), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);

                    player.hurtMarked = true;
                    player.setDeltaMovement(player.getLookAngle().multiply(i * 0.125, i * 0.125, i * 0.125));

                    playerRubberBand.setRubberBandBounces(5);



                    player.getCooldowns().addCooldown(this, 100); //Adds a 5 sec. cooldown
                }});

            }
        }
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.rubber_band").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.TIGHT);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
