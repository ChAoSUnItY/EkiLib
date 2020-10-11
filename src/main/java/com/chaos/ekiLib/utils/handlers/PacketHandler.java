package com.chaos.ekiLib.utils.handlers;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.utils.network.*;
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
                PacketStationChanged.class,
                PacketStationChanged::encode,
                PacketStationChanged::decode,
                PacketStationChanged::handle);

        INSTANCE.registerMessage(ID++,
                PacketStationChanged.PacketRemindStationChanged.class,
                PacketStationChanged.PacketRemindStationChanged::encode,
                PacketStationChanged.PacketRemindStationChanged::decode,
                PacketStationChanged.PacketRemindStationChanged::handle);
    }
}
