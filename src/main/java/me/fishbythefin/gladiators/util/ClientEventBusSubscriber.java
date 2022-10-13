package me.fishbythefin.gladiators.util;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.client.render.BlobRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Gladiators.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        EntityRenderers.register(RegistryHandler.BLOB.get(), BlobRenderer::new);
    }
}
