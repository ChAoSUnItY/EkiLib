package com.chaos.ekiLib.screen;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.station.data.Station;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenStationSelection extends ScreenBase {
    private ScreenStationSelection.List list;
    private final boolean selectMode;

    public ScreenStationSelection(int dimID, PlayerEntity player, boolean selectMode) {
        super(new TranslationTextComponent("eki.screen.station_selection"), dimID, player);
        this.selectMode = selectMode;
    }

    protected void init() {
        this.list = new ScreenStationSelection.List(this.minecraft);
        this.children.add(this.list);
        super.init();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.list.render(matrixStack, mouseX, mouseY, partialTicks);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 16, 16777215);
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @OnlyIn(Dist.CLIENT)
    class List extends ExtendedList<ScreenStationSelection.List.StationEntry> {
        public List(Minecraft mcIn) {
            super(mcIn, ScreenStationSelection.this.width, ScreenStationSelection.this.height, 32, ScreenStationSelection.this.height - 65 + 4, 18);

            for (Station station : EkiLibApi.getStationList(ScreenStationSelection.this.dimID)){
                this.addEntry(new StationEntry(station));
            }

            if (this.getSelected() != null) {
                this.centerScrollOn(this.getSelected());
            }

        }

        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + 20;
        }

        public int getRowWidth() {
            return super.getRowWidth() + 50;
        }

        protected void renderBackground(MatrixStack matrixStack) {
            ScreenStationSelection.this.renderBackground(matrixStack);
        }

        protected boolean isFocused() {
            return ScreenStationSelection.this.getListener() == this;
        }

        @OnlyIn(Dist.CLIENT)
        public class StationEntry extends ExtendedList.AbstractListEntry<ScreenStationSelection.List.StationEntry> {
            private final Station station;

            public StationEntry(Station station) {
                this.station = station;
            }

            public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
                String s = this.station.getName() + " - " + this.station.getFormmatedPosition();
                ScreenStationSelection.this.font.func_238406_a_(p_230432_1_, s, (float) (ScreenStationSelection.List.this.width / 2 - ScreenStationSelection.this.font.getStringWidth(s) / 2), (float) (p_230432_3_ + 1), 16777215, true);
            }

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
