package me.fishbythefin.gladiators.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class GladiatorsItemRarities {
    public static final Component RADICAL = Component.translatable("rarities.gladiators.radical").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD);
    public static final Component TIGHT = Component.translatable("rarities.gladiators.tight").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.BOLD);
    public static final Component MID = Component.translatable("rarities.gladiators.mid").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD);
    public static final Component MEH = Component.translatable("rarities.gladiators.meh").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD);
    public static final Component DOG_DOODOO = Component.translatable("rarities.gladiators.dog_doodoo").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.BOLD);
}
