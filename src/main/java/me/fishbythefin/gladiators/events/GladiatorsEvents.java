package me.fishbythefin.gladiators.events;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.client.GayRayHudOverlay;
import me.fishbythefin.gladiators.entities.BlobEntity;
import me.fishbythefin.gladiators.gay.PlayerGayTimer;
import me.fishbythefin.gladiators.gay.PlayerGayTimerProvider;
import me.fishbythefin.gladiators.networking.ModMessages;
import me.fishbythefin.gladiators.networking.packets.GayRayDataSyncS2CPacket;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;


public class GladiatorsEvents {
    @Mod.EventBusSubscriber(modid = Gladiators.MODID)
    public static class ForgeEvents {

        //An event fired when and entity attacks another entity
        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            if (event.getSource().getEntity() instanceof Player player) { //Attacker is player
                //Player is holding the toy hammer
                if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(RegistryHandler.TOY_HAMMER.get())) { event.getEntity().level.playSound(player, player.blockPosition(), RegistryHandler.TOY_HAMMER_SQUEAK.get(), SoundSource.PLAYERS, 1f, 1f);}
                //Player is holding the hiltless horror
                else if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(RegistryHandler.HILTLESS_HORROR.get())) { player.hurt(DamageSource.GENERIC, 1.0f); }

            }
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            //Makes sure all players have the gay timer capability
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).isPresent()) {
                    event.addCapability(new ResourceLocation(Gladiators.MODID, "properties"), new PlayerGayTimerProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            //If player dies, copy their old gay timer
            if (event.isWasDeath()) {
                event.getOriginal().getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        //Registers PlayerGayTimer
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerGayTimer.class);
        }

        //Runs once a tick per player
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.side == LogicalSide.SERVER) {//If this is a server-side event
                //Subtracts one from the timer every second
                event.player.getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(playerGayTimer -> {
                    if (playerGayTimer.getGayTimer() > 0){
                        playerGayTimer.subtractGayTime(1);
                    }
                    ModMessages.sendToPlayer(new GayRayDataSyncS2CPacket(playerGayTimer.getGayTimer()), (ServerPlayer) event.player);
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerJoin(EntityJoinLevelEvent event) {
            if (!event.getLevel().isClientSide) {
                if (event.getEntity() instanceof ServerPlayer player) {
                    player.getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(playerGayTimer -> {
                        ModMessages.sendToPlayer(new GayRayDataSyncS2CPacket(playerGayTimer.getGayTimer()), player);
                    });
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Gladiators.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            //Initializes the blob mob
            event.put(RegistryHandler.BLOB.get(), BlobEntity.setAttributes());
        }
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            //Registers the gay ray overlay
            event.registerAboveAll("gay_ray", GayRayHudOverlay.HUD_GAY_RAY);
        }
    }
}
