package com.chaos.eki_lib.gui.widgets;

import com.chaos.eki_lib.station.data.Station;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;

public class StationListWidget extends ExtendedList<StationListWidget.Entry> {
    public StationListWidget(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
    }

    public class Entry extends ExtendedList.AbstractListEntry<Entry> {
        public Station station;

        @Override
        public void render(MatrixStack p_230432_1_, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {

        }
    }
}
