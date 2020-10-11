package com.chaos.ekiLib.screen;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.utils.util.UtilDimensionConverter;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ScreenMenu extends ScreenBase {
    private static final ResourceLocation BACKGROUND_TEXTURE =
            new ResourceLocation(EkiLib.MODID, "textures/gui/item_pedestal.png");

    public ScreenMenu(RegistryKey<World> dimensionType, PlayerEntity player) {
        this(UtilDimensionConverter.dimensionKeyToID(dimensionType).getAsInt(), player);
    }

    public ScreenMenu(int dimensionID, PlayerEntity player) {
        super(new TranslationTextComponent("eki.screen.menu"), dimensionID, player);
    }

    @Override
    protected void init() {
        this.addButton(
                new Button(
                        this.width / 2 - 50,
                        this.height / 2 - 10,
                        100,
                        20,
                        new StringTextComponent("slo"),
                        v ->
                                this.minecraft.displayGuiScreen(
                                        new ScreenModifyStation(this.dimID, this.player))));
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
