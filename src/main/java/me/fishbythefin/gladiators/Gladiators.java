package me.fishbythefin.gladiators;

import com.mojang.logging.LogUtils;
import me.fishbythefin.gladiators.networking.ModMessages;
import me.fishbythefin.gladiators.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Gladiators.MODID)
public class Gladiators {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "gladiators";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Gladiators() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();



        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //Initializes the registry handler(A class that registers items/blocks)
        RegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        ModMessages.register();

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            EntityRenderers.register(RegistryHandler.BLOB.get(), ThrownItemRenderer::new);
            EntityRenderers.register(RegistryHandler.BRICKMERANG_ENTITY.get(), ThrownItemRenderer::new);
        }
    }

    public static final CreativeModeTab MELEE_TAB = new CreativeModeTab("melee_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegistryHandler.HILTLESS_HORROR.get());
        }
        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            //Puts the items in the creative tab in a specified order
            items.add(new ItemStack(RegistryHandler.HILTLESS_HORROR.get()));
            items.add(new ItemStack(RegistryHandler.TOY_HAMMER.get()));
            super.fillItemList(items);
        }
    };
    public static final CreativeModeTab RANGED_TAB = new CreativeModeTab("ranged_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegistryHandler.BLOBLOBBER.get());
        }
        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            //Puts the items in the creative tab in a specified order
            items.add(new ItemStack(RegistryHandler.BLOBLOBBER.get()));
            items.add(new ItemStack(RegistryHandler.BRICKMERANG.get()));
            super.fillItemList(items);
        }
    };
    public static final CreativeModeTab SUPPORT_TAB = new CreativeModeTab("support_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegistryHandler.SACRIFICIAL_LAMB.get());
        }
        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            //Puts the items in the creative tab in a specified order
            items.add(new ItemStack(RegistryHandler.GAY_RAY.get()));
            items.add(new ItemStack(RegistryHandler.SACRIFICIAL_LAMB.get()));
            super.fillItemList(items);
        }
    };

}
