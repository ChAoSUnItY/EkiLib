package com.chaos.ekiLib.utils.handlers;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.objects.blocks.TicketGateBlock;
import com.chaos.ekiLib.objects.blocks.TicketVendorBlock;
import com.chaos.ekiLib.objects.items.StationTunerItem;
import com.chaos.ekiLib.objects.items.TicketItem;
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
    public static final RegistryObject<StationTunerItem> STATION_TUNER = ITEMS.register("station_tuner", StationTunerItem::new);
    public static final RegistryObject<TicketItem> TICKET = ITEMS.register("ticket", () -> new TicketItem(0));
    public static final RegistryObject<TicketItem> CUT_TICKET = ITEMS.register("cut_ticket", () -> new TicketItem(1));
    public static final RegistryObject<TicketItem> IC_TICKET = ITEMS.register("ic_ticket", () -> new TicketItem(2));

    // BLOCKS
    public static final RegistryObject<TicketGateBlock> TICKET_GATE = BLOCKS.register("ticket_gate", TicketGateBlock::new);
    public static final RegistryObject<TicketVendorBlock> TICKET_VENDOR = BLOCKS.register("ticket_vendor", TicketVendorBlock::new);

    // BLOCK ITEM
    public static final RegistryObject<BlockItem> TICKET_GATE_ITEM = ITEMS.register("ticket_gate",
            () -> new BlockItem(TICKET_GATE.get(), new Item.Properties().group(EkiLib.EkiLibGroup)));
    public static final RegistryObject<BlockItem> TICKET_VENDOR_ITEM = ITEMS.register("ticket_vendor",
            () -> new BlockItem(TICKET_VENDOR.get(), new Item.Properties().group(EkiLib.EkiLibGroup)));
}
