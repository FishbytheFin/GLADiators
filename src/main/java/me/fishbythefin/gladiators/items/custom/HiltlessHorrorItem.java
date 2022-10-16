package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.weapons.GladiatorsItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HiltlessHorrorItem extends SwordItem {
    public HiltlessHorrorItem() {
        super(GladiatorsItemTier.HILTLESS, 7, -2.5f, new Item.Properties().stacksTo(1));
    }

//    @Override
//    public boolean hurtEnemy(ItemStack itemStack, LivingEntity entity, LivingEntity attacker) {
//        if (attacker instanceof Player player && player.level instanceof ServerLevel) player.hurt(DamageSource.GENERIC, 1.0f);
//        return super.hurtEnemy(itemStack, entity, attacker);
//    }

    //This method runs everytime the blade is swung, be that attacking, opening a door, or even just trying to sleep
    //I'm gonna call this a feature bc i think it's funny that ur not just clumsy with while ur fighting(also to lazy to fix)
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player && player.level instanceof ServerLevel) player.hurt(DamageSource.GENERIC, 1.0f);
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.hiltless_horror").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.MID);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
