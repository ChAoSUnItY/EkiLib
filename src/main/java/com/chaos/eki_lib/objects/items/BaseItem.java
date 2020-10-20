package com.chaos.eki_lib.objects.items;

import com.chaos.eki_lib.EkiLib;
import net.minecraft.item.Item;

public class BaseItem extends Item {
    public BaseItem(Properties properties) {
        super(properties.group(EkiLib.EkiLibGroup));
    }
}
