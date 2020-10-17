package com.chaos.ekiLib.screen;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.objects.items.ItemStationTuner;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.PacketHandler;
import com.chaos.ekiLib.utils.network.PacketBindTuner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenStationSelection extends ScreenBase {
    private ScreenStationSelection.List list;

    public ScreenStationSelection(Screen previous, int dimID, PlayerEntity player) {
        super(new TranslationTextComponent("eki.screen.station_selection"), previous, dimID, player);
    }

    @Override
    protected void init() {
        this.list = new ScreenStationSelection.List(this.minecraft);
        this.children.add(this.list);
        this.addButton(
                new Button(this.width / 2 - 200,
                        this.height - 50,
                        100,
                        20,
                        new StringTextComponent("Modify / Create"),
                        v -> {
                            if (this.list.getSelected() != null)
                                this.minecraft.displayGuiScreen(
                                        new ScreenModifyStation(
                                                this,
                                                this.dimID,
                                                this.player,
                                                this.list.getSelected().station));
                            else
                                this.minecraft.displayGuiScreen(
                                        new ScreenModifyStation(
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
                        new StringTextComponent("Remove"),
                        v -> {
                            if (this.list.getSelected() != null) {
                                EkiLibApi.deleteStationsByPosition(this.list.getListener().station);
                                EkiLibApi.markDirty();
                                this.minecraft.displayGuiScreen(this);
                            }
                        }));
        Button buttonBind = this.addButton(
                new Button(this.width / 2 + 100,
                        this.height - 50,
                        100,
                        20,
                        new StringTextComponent("Bind"),
                        v -> {
                            PacketHandler.INSTANCE.sendToServer(new PacketBindTuner(this.list.getSelected().station));
                            ITextComponent msg = new StringTextComponent("Successfully binds the selected station to the station tuner!");
                            this.player.sendStatusMessage(msg, true);
                            this.closeScreen();
                        },
                        (p1, p2, p3, p4) -> {
                            java.util.List<ITextComponent> list = Lists.newArrayList();
                            list.add(new StringTextComponent("Binds the selected station to the station tuner you're holding."));

                            if (!(this.player.getHeldItemMainhand().getItem() instanceof ItemStationTuner)) {
                                IFormattableTextComponent subtext = new StringTextComponent("You aren't holding a station tuner in your mainhand!");
                                subtext.mergeStyle(TextFormatting.RED);
                                list.add(subtext);
                            }

                            this.renderTooltip(p2,
                                    Lists.transform(list, ITextComponent::func_241878_f),
                                    p3,
                                    p4);
                        }));
        if (!(this.player.getHeldItemMainhand().getItem() instanceof ItemStationTuner))
            buttonBind.active = false;
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.list.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 16, 16777215);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX > this.list.getRight() ||
                mouseX < this.list.getLeft() ||
                mouseY > this.list.getTop() ||
                mouseY < this.list.getBottom())
            this.minecraft.displayGuiScreen(this);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @OnlyIn(Dist.CLIENT)
    class List extends ExtendedList<ScreenStationSelection.List.StationEntry> {
        public List(Minecraft mcIn) {
            super(mcIn, ScreenStationSelection.this.width, ScreenStationSelection.this.height, 32, ScreenStationSelection.this.height - 65 + 4, 18);

            for (Station station : EkiLibApi.getStationList(ScreenStationSelection.this.dimID)) {
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
        protected void renderBackground(MatrixStack matrixStack) {
            ScreenStationSelection.this.renderBackground(matrixStack);
        }

        @Override
        protected boolean isFocused() {
            return ScreenStationSelection.this.getListener() == this;
        }

        @OnlyIn(Dist.CLIENT)
        public class StationEntry extends ExtendedList.AbstractListEntry<ScreenStationSelection.List.StationEntry> {
            private final Station station;

            public StationEntry(Station station) {
                this.station = station;
            }

            @Override
            public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
                String s = this.station.getName() + " - " + this.station.getFormmatedPosition();
                ScreenStationSelection.this.font.func_238406_a_(p_230432_1_, s, (float) (ScreenStationSelection.List.this.width / 2 - ScreenStationSelection.this.font.getStringWidth(s) / 2), (float) (p_230432_3_ + 1), 16777215, true);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    this.select();
                    return true;
                } else {
                    return false;
                }
            }

            private void select() {
                ScreenStationSelection.List.this.setSelected(this);
            }
        }
    }
}
