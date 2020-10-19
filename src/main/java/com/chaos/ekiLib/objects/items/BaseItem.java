package com.chaos.ekiLib.objects.items;

import com.chaos.ekiLib.EkiLib;
import net.minecraft.item.Item;

public class BaseItem extends Item {
    public BaseItem(Properties properties) {
        super(properties.group(EkiLib.EkiLibGroup));
    }
}
