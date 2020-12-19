package com.chaos.eki_lib.utils.handlers;

import com.chaos.eki_lib.EkiLib;
import com.chaos.eki_lib.tileentity.StationNameplateTileEntity;
import com.chaos.eki_lib.tileentity.TicketGateTileEntity;
import com.chaos.eki_lib.tileentity.TicketVendorTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityHandler {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, EkiLib.MODID);

    public static final RegistryObject<TileEntityType<TicketGateTileEntity>> TICKET_GATE = TILE_ENTITY_TYPES.register("ticket_gate.json",
            () -> TileEntityType.Builder.create(TicketGateTileEntity::new, RegistryHandler.TICKET_GATE.get()).build(null));
    public static final RegistryObject<TileEntityType<TicketVendorTileEntity>> TICKET_VENDOR = TILE_ENTITY_TYPES.register("ticket_vendor",
            () -> TileEntityType.Builder.create(TicketVendorTileEntity::new, RegistryHandler.TICKET_VENDOR.get()).build(null));
    public static final RegistryObject<TileEntityType<StationNameplateTileEntity>> STATION_NAMEPLATE = TILE_ENTITY_TYPES.register("station_nameplate",
            () -> TileEntityType.Builder.create(StationNameplateTileEntity::new, RegistryHandler.STATION_NAME_PLATE.get()).build(null));
}
