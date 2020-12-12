package com.chaos.eki_lib.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BaseScreen extends Screen {
    protected Screen previous;
    protected int dimID;
    protected final PlayerEntity player;

    public BaseScreen(ITextComponent titleIn, Screen previous, int dimID, PlayerEntity player) {
        super(titleIn);
        this.previous = previous;
        this.dimID = dimID;
        this.player = player;
    }
}
