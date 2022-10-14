package me.fishbythefin.gladiators.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.fishbythefin.gladiators.Gladiators;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GayRayHudOverlay {
    private static final ResourceLocation GAY_RAY_OVERLAY = new ResourceLocation(Gladiators.MODID, "textures/overlay/gay_ray_overlay.png");

    public static final IGuiOverlay HUD_GAY_RAY = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, ClientGayRayData.getPlayerGayRayTimer() * 0.005f);
        RenderSystem.setShaderTexture(0, GAY_RAY_OVERLAY);
        GuiComponent.blit(poseStack, 0, 0, 0, 0, screenWidth, screenHeight, 64, 36);
    }));
}
