package com.chaos.ekiLib.gui.screen;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.gui.container.TicketVendorContainer;
import com.chaos.ekiLib.tileentity.TicketVendorTileEntity;
import com.chaos.ekiLib.utils.handlers.PacketHandler;
import com.chaos.ekiLib.utils.network.PacketVendorSpawnTicket;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.regex.Pattern;

@OnlyIn(Dist.CLIENT)
public class TicketVendorScreen extends ContainerScreen<TicketVendorContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(EkiLib.MODID, "textures/gui/container/ticket_vendor.png");
    private static final Pattern regex = Pattern.compile("\\d*");
    private final TicketVendorTileEntity TVte;
    private TextFieldWidget priceInputTextField;
    private Button clearButton;
    private Button confirmedButton;

    public TicketVendorScreen(TicketVendorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.TVte = screenContainer.getVendor();
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void init() {
        super.init();
        this.clearButton = this.addButton(new Button(
                this.guiLeft + 18,
                this.guiTop + 47,
                50,
                20,
                new TranslationTextComponent("eki_lib.screen.clear"),
                v -> this.priceInputTextField.setText("")));
        this.confirmedButton = this.addButton(new Button(
                this.guiLeft + 85,
                this.guiTop + 47,
                50,
                20,
                new TranslationTextComponent("eki_lib.screen.confirmed"),
                v -> {
                    double value = Double.valueOf(this.priceInputTextField.getText().isEmpty() ? "0" : this.priceInputTextField.getText());
                    PacketHandler.INSTANCE.sendToServer(new PacketVendorSpawnTicket(this.TVte.getPos(), 0, value));
                }));
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.priceInputTextField = new TextFieldWidget(this.font,
                this.guiLeft + 18,
                this.guiTop + 32,
                117,
                13,
                new StringTextComponent(""));
        this.priceInputTextField.setResponder(s -> this.confirmedButton.active = !s.isEmpty());
        this.priceInputTextField.setValidator(s -> regex.matcher(s).matches());
        this.children.add(this.priceInputTextField);

        this.confirmedButton.active = !this.priceInputTextField.getText().isEmpty();
        this.priceInputTextField.mouseClicked(this.guiLeft + 19, this.guiTop + 33, 1);
    }

    @Override
    public void tick() {
        this.priceInputTextField.tick();
    }

    @Override
    public void render(final MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.priceInputTextField.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final MatrixStack matrixStack, final int x, final int y) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final MatrixStack matrixStack, final float partialTicks, final int mouseX, final int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        super.onClose();
    }
}
