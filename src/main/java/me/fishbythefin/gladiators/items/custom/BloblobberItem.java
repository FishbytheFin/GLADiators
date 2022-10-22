package me.fishbythefin.gladiators.items.custom;

import me.fishbythefin.gladiators.entities.BlobEntity;
import me.fishbythefin.gladiators.items.GladiatorsItemRarities;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.gladiators.bloblobber").withStyle(ChatFormatting.GRAY));
        components.add(GladiatorsItemRarities.TIGHT);
        super.appendHoverText(itemStack, level, components, flag);
    }

    private void throwBlobs(Player player, Level level) {
        Vec3 velocityMultiple = new Vec3(0.5d, 0.5d, 0.5d);
        BlobEntity blob4 = new BlobEntity(RegistryHandler.BLOB.get(), level);//Last blob(Doesn't spawn anything)
        blob4.setOwner(player);
        blob4.setPos(player.getEyePosition());
        blob4.setDeltaMovement(player.getLookAngle().multiply(velocityMultiple));

        BlobEntity blob3 = new BlobEntity(RegistryHandler.BLOB.get(), level);//Second to last blob, spawns blob4
        blob3.setSpawnDelay(3);
        blob3.setOwner(player);
        blob3.setPos(player.getEyePosition());
        blob3.setDeltaMovement(player.getLookAngle().multiply(velocityMultiple));
        blob3.setSpawningAnother(true);
        blob3.setBlobToAdd(blob4);

        BlobEntity blob2 = new BlobEntity(RegistryHandler.BLOB.get(), level);//Second blob, spawns blob3
        blob2.setSpawnDelay(3);
        blob2.setOwner(player);
        blob2.setPos(player.getEyePosition());
        blob2.setDeltaMovement(player.getLookAngle().multiply(velocityMultiple));
        blob2.setSpawningAnother(true);
        blob2.setBlobToAdd(blob3);

        BlobEntity blob1 = new BlobEntity(RegistryHandler.BLOB.get(), level);//First blob, spawns blob2
        blob1.setSpawnDelay(3);
        blob1.setOwner(player);
        blob1.setPos(player.getEyePosition());
        blob1.setDeltaMovement(player.getLookAngle().multiply(velocityMultiple));
        blob1.setSpawningAnother(true);
        blob1.setBlobToAdd(blob2);
        level.addFreshEntity(blob1);
    }
}
