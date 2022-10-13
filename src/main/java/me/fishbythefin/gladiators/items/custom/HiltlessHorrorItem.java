package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.weapons.GladiatorsItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HiltlessHorrorItem extends SwordItem {
    public HiltlessHorrorItem() {
        super(GladiatorsItemTier.HILTLESS, 7, -2.5f, new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.hiltless_horror").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.MID);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
