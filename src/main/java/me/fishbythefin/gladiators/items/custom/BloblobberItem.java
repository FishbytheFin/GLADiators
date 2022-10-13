package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.entities.BlobEntity;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloblobberItem extends Item {
    public BloblobberItem() {
        super(new Item.Properties().stacksTo(1));
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide() && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            //Throw blobs
            throwBlobs(player, level);
            //Set a cooldown
            player.getCooldowns().addCooldown(this, 100);
        }

        return super.use(level, player, interactionHand);
    }

    private void throwBlobs(Player player, Level level) {
        BlobEntity blob = new BlobEntity(RegistryHandler.BLOB.get(), level);
        level.addFreshEntity(blob);
        blob.moveTo(player.position());
        blob.setDeltaMovement(player.getLookAngle().multiply(2, 2, 2));
    }
}
