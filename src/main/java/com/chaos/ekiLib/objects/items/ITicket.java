package com.chaos.ekiLib.objects.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface ITicket {
    boolean isValid(ItemStack stack);

    void cutTicket(PlayerEntity player, ItemStack stack);

    boolean isSmartCard();
}
