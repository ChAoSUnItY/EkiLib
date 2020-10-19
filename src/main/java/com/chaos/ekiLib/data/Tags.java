package com.chaos.ekiLib.data;

import com.chaos.ekiLib.EkiLib;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class Tags {
    public static final ITag ticketTag = ItemTags.getCollection().get(new ResourceLocation(EkiLib.MODID, "ticket"));
}
