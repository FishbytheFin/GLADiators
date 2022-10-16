package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.lamb.PlayerSacrificialLambProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SacrificialLambItem extends Item {
    private static final String[] sheepNames = {"Millie", "Bobby", "Buster", "Salad", "Furby", "Plush Monster", "Smoochums", "Friend", "Willy", "Wooly", "Cow", "Zzzzz", "Sheila", "Taco", "Churro", "Lamby", "Sugar-pie", "Sweet cheeks", "Fairy", "jeb_", "Mary", "Tulip", "Bambi", "Beefcake", "Manbun", "Floof", "Twinkie", "Stay-puft", "Luna", "Maximo", "Momo", "Shep", "Pie", "Rhubarb"};

    public SacrificialLambItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (level instanceof ServerLevel serverLevel && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            //Server side and in main hand = ready for action!
            //Edits player capability
            player.getCapability(PlayerSacrificialLambProvider.PLAYER_SACRIFICIAL_LAMB).ifPresent(playerSacrificialLamb -> {

                if (!(serverLevel.getEntity(playerSacrificialLamb.getLambUUID()) instanceof LivingEntity livingEntity && livingEntity.getHealth() > 0) || serverLevel.getEntity(playerSacrificialLamb.getLambUUID()) == null) {
                    //Sheep is either dead or non-existant
                    Sheep sheep = new Sheep(EntityType.SHEEP, level); //new sheep
                    sheep.setPos(player.getEyePosition().add(player.getLookAngle()));
                    sheep.setCustomName(Component.literal(sheepNames[level.random.nextInt(sheepNames.length)]));

                    playerSacrificialLamb.setLambUUID(sheep.getUUID()); //Sets new uuid for the player
                    serverLevel.addFreshEntity(sheep);//Puts the lamb in the world

                    player.getCooldowns().addCooldown(this, 400);
                }
            });
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.sacrificial_lamb").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.RADICAL);
        super.appendHoverText(itemStack, level, components, flag);
    }
}
