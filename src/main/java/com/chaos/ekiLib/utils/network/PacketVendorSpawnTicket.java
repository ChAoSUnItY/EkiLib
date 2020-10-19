package com.chaos.ekiLib.utils.network;

import com.chaos.ekiLib.objects.items.TicketItem;
import com.chaos.ekiLib.tileentity.TicketVendorTileEntity;
import com.chaos.ekiLib.utils.handlers.RegistryHandler;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketVendorSpawnTicket implements IPacketBase {
    private final BlockPos pos;
    private final int ticketType;
    private final double value;

    public PacketVendorSpawnTicket(BlockPos pos, int ticketType, double value) {
        this.pos = pos;
        this.ticketType = ticketType;
        this.value = value;
    }

    public static void encode(PacketVendorSpawnTicket pkg, PacketBuffer buf) {
        buf.writeBlockPos(pkg.pos);
        buf.writeVarInt(pkg.ticketType);
        buf.writeDouble(pkg.value);
    }

    public static PacketVendorSpawnTicket decode(PacketBuffer buf) {
        BlockPos pos = buf.readBlockPos();
        int ticketType = buf.readVarInt();
        double value = buf.readDouble();
        return new PacketVendorSpawnTicket(pos, ticketType, value);
    }

    public static void handle(PacketVendorSpawnTicket pkg, Supplier<NetworkEvent.Context> ctx) {
        if (IPacketBase.handleInSide(ctx, Dist.DEDICATED_SERVER)) {
            ctx.get().enqueueWork(() -> {
                TicketItem ticket;
                ServerWorld world = ctx.get().getSender().getServerWorld();
                TileEntity te = world.getTileEntity(pkg.pos);
                if (te instanceof TicketVendorTileEntity) {
                    TicketVendorTileEntity TVte = (TicketVendorTileEntity) te;
                    switch (pkg.ticketType) {
                        case 2:
                            ticket = RegistryHandler.IC_TICKET.get();
                            break;
                        case 1:
                        default:
                            ticket = RegistryHandler.TICKET.get();
                    }

                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putDouble("value", pkg.value);

                    ItemStack stack = new ItemStack(ticket, 1);
                    stack.setTag(nbt);

                    if (!TVte.getItems().get(0).isEmpty())
                        world.addEntity(new ItemEntity(world, pkg.pos.getX(), pkg.pos.getY(), pkg.pos.getZ(), stack));
                    else {
                        TVte.setInventorySlotContents(0, stack);
                        TVte.markDirty();
                    }
                }
            });
        }
    }
}
