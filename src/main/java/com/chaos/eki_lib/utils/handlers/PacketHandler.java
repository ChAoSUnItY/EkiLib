package com.chaos.eki_lib.utils.handlers;

import com.chaos.eki_lib.EkiLib;
import com.chaos.eki_lib.utils.network.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "7";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(EkiLib.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static  void init() {
        int ID = 0;

        INSTANCE.registerMessage(ID++,
                PacketNoticeStationChanged.class,
                PacketNoticeStationChanged::encode,
                PacketNoticeStationChanged::decode,
                PacketNoticeStationChanged::handle);

        INSTANCE.registerMessage(ID++,
                PacketInitStationHandler.class,
                PacketInitStationHandler::encode,
                PacketInitStationHandler::decode,
                PacketInitStationHandler::handle);

        INSTANCE.registerMessage(ID++,
                PacketInitStationHandler.PacketReloadStationHandler.class,
                PacketInitStationHandler.PacketReloadStationHandler::encode,
                PacketInitStationHandler.PacketReloadStationHandler::decode,
                PacketInitStationHandler.PacketReloadStationHandler::handle);

        INSTANCE.registerMessage(ID++,
                PacketBindTuner.class,
                PacketBindTuner::encode,
                PacketBindTuner::decode,
                PacketBindTuner::handle);

        INSTANCE.registerMessage(ID++,
                PacketVendorSpawnTicket.class,
                PacketVendorSpawnTicket::encode,
                PacketVendorSpawnTicket::decode,
                PacketVendorSpawnTicket::handle);
    }
}
