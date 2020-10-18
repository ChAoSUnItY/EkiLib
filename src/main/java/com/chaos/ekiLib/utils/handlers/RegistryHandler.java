package com.chaos.ekiLib.utils.handlers;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.objects.blocks.BlockTicketGate;
import com.chaos.ekiLib.objects.items.ItemStationTuner;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EkiLib.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EkiLib.MODID);

    // ITEMS
    public static final RegistryObject<ItemStationTuner> STATION_TUNER = ITEMS.register("station_tuner", ItemStationTuner::new);

    // BLOCKS
    public static final RegistryObject<BlockTicketGate> TICKET_GATE = BLOCKS.register("ticket_gate", BlockTicketGate::new);

    // BLOCK ITEM
    public static final RegistryObject<Item> TICKET_GATE_ITEM = ITEMS.register("ticket_gate",
            () -> new BlockItem(TICKET_GATE.get(), new Item.Properties().group(EkiLib.EkiLibGroup)));
}
