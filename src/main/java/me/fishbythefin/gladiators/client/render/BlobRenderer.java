package me.fishbythefin.gladiators.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.fishbythefin.gladiators.Gladiators;
import me.fishbythefin.gladiators.client.model.BlobModel;
import me.fishbythefin.gladiators.entities.BlobEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlobRenderer extends GeoEntityRenderer<BlobEntity> {

    public BlobRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlobModel());
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(BlobEntity object) {
        return new ResourceLocation(Gladiators.MODID, "textures/entity/blob_texture.png");
    }

    @Override
    public RenderType getRenderType(BlobEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
