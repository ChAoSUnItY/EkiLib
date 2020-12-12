package com.chaos.eki_lib.gui.screen;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.objects.items.StationTunerItem;
import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.handlers.PacketHandler;
import com.chaos.eki_lib.utils.network.PacketBindTuner;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StationSelectionScreen extends BaseScreen {
    private Button bindButton;
    private StationSelectionScreen.List list;

    public StationSelectionScreen(Screen previous, int dimID, PlayerEntity player) {
        super(new TranslationTextComponent("eki_lib.screen.station_selection"), previous, dimID, player);
    }

    @Override
    protected void init() {
        this.list = new StationSelectionScreen.List(this.minecraft);
        this.children.add(this.list);
        this.addButton(
                new Button(this.width / 2 - 200,
                        this.height - 50,
                        100,
                        20,
                        new TranslationTextComponent("eki_lib.screen.create_modify").getFormattedText(),
                        v -> {
                            if (this.list.getSelected() != null)
                                this.minecraft.displayGuiScreen(
                                        new ModifyStationScreen(
                                                this,
                                                this.dimID,
                                                this.player,
                                                this.list.getSelected().station));
                            else
                                this.minecraft.displayGuiScreen(
                                        new ModifyStationScreen(
                                                this,
                                                this.dimID,
                                                this.player
                                        )
                                );
                        }));
        this.addButton(
                new Button(this.width / 2 - 50,
                        this.height - 50,
                        100,
                        20,
                        new TranslationTextComponent("eki_lib.screen.remove").getFormattedText(),
                        v -> {
                            if (this.list.getSelected() != null) {
                                EkiLibApi.deleteStationsByPosition(this.list.getFocused().station);
                                EkiLibApi.markDirty();
                                this.minecraft.displayGuiScreen(this);
                            }
                        }));
        this.bindButton = this.addButton(
                new Button(this.width / 2 + 100,
                        this.height - 50,
                        100,
                        20,
                        new TranslationTextComponent("eki_lib.screen.bind").getFormattedText(),
                        v -> {
                            if (this.list.getSelected() == null)
                                return;

                            if (this.list.getSelected().station == null)
                                return;

                            PacketHandler.INSTANCE.sendToServer(new PacketBindTuner(this.list.getSelected().station));
                            ITextComponent msg = new TranslationTextComponent("eki_lib.screen.bind_accept");
                            this.player.sendStatusMessage(msg, true);
                            this.onClose();
                        }));
        this.addButton(
                new Button(this.width - 60,
                        5,
                        20,
                        20,
                        new StringTextComponent("X").getFormattedText(),
                        v -> {
                            this.minecraft.displayGuiScreen(this);
                            this.bindButton.active = false;
                        }));
        if (!(this.player.getHeldItemMainhand().getItem() instanceof StationTunerItem))
            this.bindButton.active = false;
        super.init();
    }

    @Override
    protected void renderComponentHoverEffect(ITextComponent p_renderComponentHoverEffect_1_, int mouseX, int mouseY) {
        super.renderComponentHoverEffect(p_renderComponentHoverEffect_1_, mouseX, mouseY);
        if (this.bindButton.isHovered()) {
            java.util.List<ITextComponent> list = Lists.newArrayList();
            list.add(new TranslationTextComponent("eki_lib.screen.bind.description"));

            if (!(this.player.getHeldItemMainhand().getItem() instanceof StationTunerItem))
                list.add(new TranslationTextComponent("eki_lib.screen.bind.warning")
                        .applyTextStyle(TextFormatting.RED));

            this.renderTooltip(
                    Lists.transform(list, ITextComponent::getFormattedText),
                    mouseX,
                    mouseY,
                    font
            );
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.list.render(mouseX, mouseY, partialTicks);
        drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 16, 16777215);
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    class List extends ExtendedList<StationSelectionScreen.List.StationEntry> {
        public List(Minecraft mcIn) {
            super(mcIn, StationSelectionScreen.this.width, StationSelectionScreen.this.height, 32, StationSelectionScreen.this.height - 65 + 4, 18);

            for (Station station : EkiLibApi.getStationList(StationSelectionScreen.this.dimID)) {
                this.addEntry(new StationEntry(station));
            }

            if (this.getSelected() != null) {
                this.centerScrollOn(this.getSelected());
            }

        }

        @Override
        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + 20;
        }

        @Override
        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        @Override
        protected void renderBackground() {
            StationSelectionScreen.this.renderBackground();
        }

        @Override
        protected boolean isFocused() {
            return StationSelectionScreen.this.getFocused() == this;
        }

        @OnlyIn(Dist.CLIENT)
        public class StationEntry extends ExtendedList.AbstractListEntry<StationSelectionScreen.List.StationEntry> {
            private final Station station;

            public StationEntry(Station station) {
                this.station = station;
            }

            @Override
            public void render(int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
                String s = this.station.getName() + " - " + this.station.getFormattedPosition();
                StationSelectionScreen.this.font.drawStringWithShadow(s, (float) (StationSelectionScreen.List.this.width / 2 - StationSelectionScreen.this.font.getStringWidth(s) / 2), (float) (p_230432_3_ + 1), 16777215);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    StationSelectionScreen.this.bindButton.active = true;
                    this.select();
                    return true;
                } else {
                    return false;
                }
            }

            private void select() {
                StationSelectionScreen.List.this.setSelected(this);
            }
        }
    }
}
