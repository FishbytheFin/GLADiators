package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.weapons.GladiatorsItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToyHammerItem extends AxeItem {
    public ToyHammerItem() {
        super(GladiatorsItemTier.TOY_HAMMER, 2, -0.5f, new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.toy_hammer").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.DOG_DOODOO);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
