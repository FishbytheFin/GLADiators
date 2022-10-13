package me.fishbythefin.gladiators.client.model;

import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.entities.BlobEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlobModel extends AnimatedGeoModel<BlobEntity> {

    @Override
    public ResourceLocation getModelResource(BlobEntity object) {
        return new ResourceLocation(Gladiators.MODID, "geo/blob_model.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlobEntity object) {
        return new ResourceLocation(Gladiators.MODID, "textures/entity/blob_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlobEntity animatable) {
        return null;
    }
}
