package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfinitePizzaItem extends Item {
    public InfinitePizzaItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(CreativeModeTab.TAB_FOOD)
                .food(new FoodProperties.Builder()
                        .nutrition(5)
                        .saturationMod(1.0f)
                        .build()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            if (!player.isCreative()) {
                player.addItem(this.getDefaultInstance());
            }
            player.getCooldowns().addCooldown(this, 200);
        }
        return itemStack;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.infinite_pizza").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.RADICAL);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
