package com.chaos.eki_lib.tileentity.renderer;

import com.chaos.eki_lib.objects.blocks.base.HorizontalBaseBlock;
import com.chaos.eki_lib.tileentity.StationNameplateTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.charset.StandardCharsets;

@OnlyIn(Dist.CLIENT)
public class StationNameplateTER extends TileEntityRenderer<StationNameplateTileEntity> {
    public StationNameplateTER(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(StationNameplateTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-tileEntityIn.getBlockState().get(HorizontalBaseBlock.FACING).getHorizontalAngle()));
        matrixStackIn.translate(0.0D, -0.3125D, -0.42D);
        matrixStackIn.scale(0.010416667F, -0.010416667F, 0.010416667F);
        matrixStackIn.scale(2F, 3F, 2F);
        FontRenderer fontRenderer = this.renderDispatcher.getFontRenderer();
        String s = toUnicode(tileEntityIn.checkStation() ? tileEntityIn.getStation().getName(): "");
        if (s.length() > 17) {
            matrixStackIn.scale(0.75F, 1.25F, 0.75F);
            matrixStackIn.translate(0D, 3.5D, 0D);
        }
        fontRenderer.renderString(toUnicode(tileEntityIn.checkStation() ? tileEntityIn.getStation().getName() : ""), -(fontRenderer.getStringWidth(s) / 2), -20, NativeImage.getCombined(1, 256, 256, 256), false, matrixStackIn.getLast().getMatrix(), bufferIn, false, 0, combinedLightIn);
        matrixStackIn.pop();
    }

    public String toUnicode(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
