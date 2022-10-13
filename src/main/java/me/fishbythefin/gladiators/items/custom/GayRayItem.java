package me.fishbythefin.gladiators.items.custom;

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
            Vec3 startingLocation = player.getEyePosition();
            Vec3 addition = player.getLookAngle().multiply(15.0d, 15.0d, 15.0d);
            EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(level, player, startingLocation, startingLocation.add(addition), player.getBoundingBox().expandTowards(player.getDeltaMovement()).inflate(15.0d), (val) -> true);
            if (hitResult != null && hitResult.getEntity() instanceof Player) {
                Player gayPlayer = (Player) hitResult.getEntity();
                hitResult.getEntity().setDeltaMovement(player.getLookAngle().multiply(10.0d, 10.0d, 10.0d));
            }

            player.getCooldowns().addCooldown(this, 20);
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
