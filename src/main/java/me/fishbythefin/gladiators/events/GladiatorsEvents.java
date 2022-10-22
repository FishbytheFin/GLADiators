package me.fishbythefin.gladiators.events;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.client.GayRayHudOverlay;
import me.fishbythefin.gladiators.gay.PlayerGayTimer;
import me.fishbythefin.gladiators.gay.PlayerGayTimerProvider;
import me.fishbythefin.gladiators.lamb.PlayerSacrificialLamb;
import me.fishbythefin.gladiators.lamb.PlayerSacrificialLambProvider;
import me.fishbythefin.gladiators.networking.ModMessages;
import me.fishbythefin.gladiators.networking.packets.GayRayDataSyncS2CPacket;
import me.fishbythefin.gladiators.particles.custom.RainbowParticles;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;


public class GladiatorsEvents {
    @Mod.EventBusSubscriber(modid = Gladiators.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
            if (!event.getEntity().level.isClientSide) {
                //Combines both halves of the brickmerang:
                //Inventory slot number of the lower half of the brickmerang (-1 if not present)
                int locLower = event.getEntity().getInventory().findSlotMatchingItem(new ItemStack(RegistryHandler.LOWER_HALF_BRICKMERANG.get()));
                //Inventory slot number of the upper half of the brickmerang (-1 if not present)
                int locUpper = event.getEntity().getInventory().findSlotMatchingItem(new ItemStack(RegistryHandler.UPPER_HALF_BRICKMERANG.get()));
                if (locLower >= 0 && locUpper >= 0) {
                    if (event.getStack().sameItem(new ItemStack(RegistryHandler.UPPER_HALF_BRICKMERANG.get()))) {
                        event.getEntity().getInventory().getItem(locUpper).shrink(1);
                        event.getEntity().getInventory().setItem(locLower, new ItemStack(RegistryHandler.BRICKMERANG.get()));
                    } else {
                        event.getEntity().getInventory().getItem(locLower).shrink(1);
                        event.getEntity().getInventory().setItem(locUpper, new ItemStack(RegistryHandler.BRICKMERANG.get()));
                    }
                    event.getEntity().getCooldowns().addCooldown(RegistryHandler.BRICKMERANG.get(), 100);
                }
            }
        }

        //An event fired when and entity attacks another entity
        @SubscribeEvent
        public static void onLivingAttack(LivingAttackEvent event) {
            if (event.getSource().getEntity() instanceof Player player) { //Attacker is player
                //Player is holding the toy hammer
                if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem().equals(RegistryHandler.TOY_HAMMER.get())) {
                    event.getEntity().level.playSound(player, player.blockPosition(), RegistryHandler.TOY_HAMMER_SQUEAK.get(), SoundSource.PLAYERS, 1f, 1f);
                }

            }
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (event.getEntity().level instanceof ServerLevel level && event.getEntity() instanceof Player player) {
                player.getCapability(PlayerSacrificialLambProvider.PLAYER_SACRIFICIAL_LAMB).ifPresent(playerSacrificialLamb -> {
                    if (level.getEntity(playerSacrificialLamb.getLambUUID()) instanceof Sheep sheep) {
                        if (sheep.getHealth() > 0) {
                            sheep.hurt(event.getSource(), event.getAmount());
                            event.setCanceled(true);
                        }
                    }
                });
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            if (event.getEntity().level instanceof ServerLevel serverLevel && event.getEntity().getType().equals(EntityType.SHEEP)) {
                //Sheep is dead
                for (ServerPlayer player : serverLevel.players()) {
                    player.getCapability(PlayerSacrificialLambProvider.PLAYER_SACRIFICIAL_LAMB).ifPresent(playerSacrificialLamb -> {
                        if (event.getEntity().getUUID().equals(playerSacrificialLamb.getLambUUID())) {
                            //The dead sheep belonged to player
                            //Alerts player of their precious sheep's death:
                            player.sendSystemMessage(Component.literal(event.getEntity().getCustomName().getString() + " has died. They will be missed. :(").withStyle(ChatFormatting.DARK_RED));
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            //Makes sure all players have the gay timer capability and the sacrificial lamb capability
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).isPresent()) {
                    //event.addCapability(new ResourceLocation(Gladiators.MODID, "properties"), new PlayerGayTimerProvider());
                    event.addCapability(new ResourceLocation(Gladiators.MODID, "properties_gay_timer"), new PlayerGayTimerProvider());
                }
                if (!event.getObject().getCapability(PlayerSacrificialLambProvider.PLAYER_SACRIFICIAL_LAMB).isPresent()) {
                    event.addCapability(new ResourceLocation(Gladiators.MODID, "properties_sacrificial_lamb"), new PlayerSacrificialLambProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            //If player dies, copy their old gay timer and old sheep UUID
            if (event.isWasDeath()) {
                event.getOriginal().getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(PlayerSacrificialLambProvider.PLAYER_SACRIFICIAL_LAMB).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerSacrificialLambProvider.PLAYER_SACRIFICIAL_LAMB).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        //Registers the PlayerGayTimer & PlayerSacrificialLamb classes
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerGayTimer.class);
            event.register(PlayerSacrificialLamb.class);
        }

        //Runs once a tick per player
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.side == LogicalSide.SERVER) {//If this is a server-side event
                //Subtracts one from the timer every second
                event.player.getCapability(PlayerGayTimerProvider.PLAYER_GAY_TIMER).ifPresent(playerGayTimer -> {
                    if (playerGayTimer.getGayTimer() > 0) {
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
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            //Registers the gay ray overlay
            event.registerAboveAll("gay_ray", GayRayHudOverlay.HUD_GAY_RAY);
        }

        @SubscribeEvent
        public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
            //Registers the rainbow particle
            Minecraft.getInstance().particleEngine.register(RegistryHandler.RAINBOW_PARTICLE.get(), RainbowParticles.Provider::new);
        }
    }
}
