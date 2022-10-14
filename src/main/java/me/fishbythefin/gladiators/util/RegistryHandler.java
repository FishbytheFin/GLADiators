package me.fishbythefin.gladiators.util;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.entities.BlobEntity;
import me.fishbythefin.gladiators.items.custom.*;
import me.fishbythefin.gladiators.particles.custom.RainbowParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gladiators.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Gladiators.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Gladiators.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Gladiators.MODID);
    //public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Gladiators.MODID);

    //Initializes all the deferred registries. IMPORTANT! Update every time you add a deferred registry.
    public static void init(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Gladiators.MODID, name)));
    }

    //ITEMS:
    //Melee
    public static final RegistryObject<SwordItem> HILTLESS_HORROR = ITEMS.register("hiltless_horror", HiltlessHorrorItem::new);
    public static final RegistryObject<Item> TOY_HAMMER = ITEMS.register("toy_hammer", ToyHammerItem::new);

    //Ranged
    public static final RegistryObject<Item> BLOBLOBBER = ITEMS.register("bloblobber", BloblobberItem::new);

    //Support
    public static final RegistryObject<Item> GAY_RAY = ITEMS.register("gay_ray", GayRayItem::new);

    //Armor

    //Other
    public  static final RegistryObject<Item> INFINITE_PIZZA = ITEMS.register("infinite_pizza", InfinitePizzaItem::new);

    //ENTITIES:
    //Projectiles
    public static final RegistryObject<EntityType<BlobEntity>> BLOB = ENTITIES.register("blob", () ->
            EntityType.Builder.of(BlobEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(new ResourceLocation(Gladiators.MODID, "blob").toString()));

    //SOUNDS:
    public static final RegistryObject<SoundEvent> TOY_HAMMER_SQUEAK = registerSoundEvent("toy_hammer_squeak");

    //PARTICLES:
    public static final RegistryObject<SimpleParticleType> RAINBOW_PARTICLE = PARTICLE_TYPES.register("rainbow_particles", () -> new SimpleParticleType(true));


}
