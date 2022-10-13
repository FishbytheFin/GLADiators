package me.fishbythefin.gladiators.events;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.entities.BlobEntity;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class GladiatorsEvents {
    @Mod.EventBusSubscriber(modid = Gladiators.MODID)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onPlayerInteract(PlayerInteractEvent event) {
            if (event.getItemStack().getItem().equals(RegistryHandler.HILTLESS_HORROR.get())) {
                event.getEntity().hurt(DamageSource.GENERIC, 1.5f);
            }
        }
        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            if (event.getSource().getEntity() instanceof Player) {
                Player player = (Player) event.getSource().getEntity();
                if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(RegistryHandler.TOY_HAMMER.get())) event.getEntity().level.playSound(player, player.blockPosition(), RegistryHandler.TOY_HAMMER_SQUEAK.get(), SoundSource.PLAYERS, 1f, 1f);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Gladiators.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(RegistryHandler.BLOB.get(), BlobEntity.setAttributes());
        }
    }
}
