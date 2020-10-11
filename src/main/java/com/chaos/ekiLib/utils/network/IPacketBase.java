package com.chaos.ekiLib.utils.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacketBase {
    static boolean handleInSide(Supplier<NetworkEvent.Context> ctx, Dist side) {
        boolean pass = false;
        switch (side) {
            case CLIENT:
                if (ctx.get().getDirection().getReceptionSide().isServer()) {
                    ctx.get().setPacketHandled(true);
                    pass = false;
                } else {
                    pass = true;
                }
                break;
            case DEDICATED_SERVER:
                if (ctx.get().getDirection().getReceptionSide().isClient()) {
                    ctx.get().setPacketHandled(true);
                    pass = false;
                } else {
                    pass = true;
                }
                break;
            default:
        }
        return pass;
    }
}
