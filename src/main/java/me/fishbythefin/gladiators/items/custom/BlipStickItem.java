package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.entities.BrickmerangEntity;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlipStickItem extends Item {
    private static final int MAX_RANGE = 20;

    public BlipStickItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            //Event is server side

            //Searches for the nearest living entity to the player
            AABB boxThatLikeSearchesForTheEntitiesOrLikeSomething = new AABB(player.getX() - MAX_RANGE, player.getY() - MAX_RANGE, player.getZ() - MAX_RANGE, player.getX() + MAX_RANGE, player.getY() + MAX_RANGE, player.getZ() + MAX_RANGE);
            //All entities with MAX_RANGE blocks of the player in all directions
            List<Entity> entities = level.getEntities(player, boxThatLikeSearchesForTheEntitiesOrLikeSomething);

            //Creates a list of only the living entities near the player
            List<LivingEntity> livingEntities = new ArrayList<>();
            for (Entity entity:entities) {
                if (entity instanceof LivingEntity livingEntity)
                    livingEntities.add(livingEntity);
            }

            //Nearest living entity to the player
            LivingEntity entityToTeleportTo = level.getNearestEntity(livingEntities, TargetingConditions.DEFAULT, player, player.getX(), player.getY(), player.getZ());

            if (entityToTeleportTo != null) {
                //Entity actually exists
                Vec3 location = new Vec3(entityToTeleportTo.getX() - entityToTeleportTo.getLookAngle().x, entityToTeleportTo.getY() - entityToTeleportTo.getLookAngle().y, entityToTeleportTo.getZ() - entityToTeleportTo.getLookAngle().z);
                if (level.getBlockState(new BlockPos(location)).getBlock().equals(Blocks.AIR)) {
                    //Player is teleporting into air
                    player.teleportTo(location.x, location.y, location.z);
                } else {
                    //Block to teleport to is blocked
                    player.teleportTo(entityToTeleportTo.getX(), entityToTeleportTo.getY(), entityToTeleportTo.getZ());
                }

                //Makes player look at the entity
                player.lookAt(EntityAnchorArgument.Anchor.EYES, entityToTeleportTo.getEyePosition());

                //Cooldown
                player.getCooldowns().addCooldown(this, 275);
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), RegistryHandler.BLIP.get(), player.getSoundSource(), 1.0F, 1.0F);
            } else {
                //Entity doesn't exist?
                player.sendSystemMessage(Component.literal("No entities found!").withStyle(ChatFormatting.RED));
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.blip_stick").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.TIGHT);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
