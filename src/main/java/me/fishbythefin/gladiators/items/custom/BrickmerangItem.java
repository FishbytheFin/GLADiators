package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.entities.BrickmerangEntity;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrickmerangItem extends Item {
    public BrickmerangItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            player.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(RegistryHandler.LOWER_HALF_BRICKMERANG.get()));
            BrickmerangEntity brickmerangEntity = new BrickmerangEntity(RegistryHandler.BRICKMERANG_ENTITY.get(), level);//Creates a new brickmerang entity
            brickmerangEntity.setOwner(player);//Owner = thrower
            brickmerangEntity.setDeltaMovement(player.getLookAngle().multiply(2.0d, 2.0d, 2.0d));//Brickmerang velocity
            brickmerangEntity.setPos(player.getEyePosition());//Starting position
            level.addFreshEntity(brickmerangEntity);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.brickmerang").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.MID);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
