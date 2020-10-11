package com.chaos.ekiLib.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public class ScreenBase extends Screen {
    protected int dimID;
    protected final PlayerEntity player;

    public ScreenBase(ITextComponent titleIn, int dimID, PlayerEntity player) {
        super(titleIn);
        this.dimID = dimID;
        this.player = player;
    }
}
