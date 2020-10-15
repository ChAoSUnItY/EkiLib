package com.chaos.ekiLib.objects.items;

import com.chaos.ekiLib.EkiLib;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(Properties properties) {
        super(properties.group(EkiLib.EkiLibGroup));
    }
}
