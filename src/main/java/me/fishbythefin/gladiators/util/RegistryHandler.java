package me.fishbythefin.gladiators.util;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.effect.DamnedEffect;
import me.fishbythefin.gladiators.entities.BlobEntity;
import me.fishbythefin.gladiators.entities.BrickmerangEntity;
import me.fishbythefin.gladiators.items.ItemBase;
import me.fishbythefin.gladiators.items.custom.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Gladiators.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Gladiators.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Gladiators.MODID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Gladiators.MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Gladiators.MODID);
    //public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Gladiators.MODID);

    //Initializes all the deferred registries. IMPORTANT! Update every time you add a deferred registry.
    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        SOUND_EVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Gladiators.MODID, name)));
    }

    //ITEMS:
    //Melee
    public static final RegistryObject<SwordItem> HILTLESS_HORROR = ITEMS.register("hiltless_horror", HiltlessHorrorItem::new);
    public static final RegistryObject<AxeItem> TOY_HAMMER = ITEMS.register("toy_hammer", ToyHammerItem::new);
    public static final RegistryObject<PickaxeItem> SPRAY_N_PRAY = ITEMS.register("spray_n_pray", SprayNPrayItem::new);

    //Ranged
    public static final RegistryObject<Item> BLOBLOBBER = ITEMS.register("bloblobber", BloblobberItem::new);
    public static final RegistryObject<Item> BRICKMERANG = ITEMS.register("brickmerang", BrickmerangItem::new);

    //Support
    public static final RegistryObject<Item> GAY_RAY = ITEMS.register("gay_ray", GayRayItem::new);
    public static final RegistryObject<Item> SACRIFICIAL_LAMB = ITEMS.register("sacrificial_lamb", SacrificialLambItem::new);

    //Armor

    //Other
    public static final RegistryObject<Item> INFINITE_PIZZA = ITEMS.register("infinite_pizza", InfinitePizzaItem::new);
    public static final RegistryObject<Item> BLOB_ITEM = ITEMS.register("blob_item", ItemBase::new);
    public static final RegistryObject<Item> BRICKMERANG_PROJECTILE_ITEM = ITEMS.register("brickmerang_projectile_item", ItemBase::new);
    public static final RegistryObject<Item> UPPER_HALF_BRICKMERANG = ITEMS.register("upper_half_brickmerang", ItemBase::new);
    public static final RegistryObject<Item> LOWER_HALF_BRICKMERANG = ITEMS.register("lower_half_brickmerang", ItemBase::new);

    //ENTITIES:
    //Projectiles
    public static final RegistryObject<EntityType<BlobEntity>> BLOB = ENTITIES.register("blob", () ->
            EntityType.Builder.of(BlobEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(new ResourceLocation(Gladiators.MODID, "blob").toString()));

    public static final RegistryObject<EntityType<BrickmerangEntity>> BRICKMERANG_ENTITY = ENTITIES.register("brickmerang_entity", () ->
            EntityType.Builder.of(BrickmerangEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(new ResourceLocation(Gladiators.MODID, "brickmerang_entity").toString()));

    //EFFECTS:
    public static final RegistryObject<MobEffect> DAMNED_EFFECT = EFFECTS.register("damned_effect", DamnedEffect::new);

    //SOUNDS:
    public static final RegistryObject<SoundEvent> TOY_HAMMER_SQUEAK = registerSoundEvent("toy_hammer_squeak");

    //PARTICLES:
    public static final RegistryObject<SimpleParticleType> RAINBOW_PARTICLE = PARTICLE_TYPES.register("rainbow_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> DAMNED_PARTICLE = PARTICLE_TYPES.register("damned_particles", () -> new SimpleParticleType(true));


}
