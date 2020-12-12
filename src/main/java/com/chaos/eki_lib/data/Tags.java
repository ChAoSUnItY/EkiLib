package com.chaos.eki_lib.data;

import com.chaos.eki_lib.EkiLib;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class Tags {
    public static final Tag ticketTag = ItemTags.getCollection().get(new ResourceLocation(EkiLib.MODID, "ticket"));
    public static final Tag registrableTag = BlockTags.getCollection().get(new ResourceLocation(EkiLib.MODID, "station_registrable"));
}
