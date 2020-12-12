package com.chaos.eki_lib.tileentity;

import com.chaos.eki_lib.api.EkiLibApi;
import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.handlers.TileEntityHandler;
import com.chaos.eki_lib.utils.util.UtilDimensionConverter;
import com.chaos.eki_lib.utils.util.UtilStationConverter;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TicketGateTileEntity extends TileEntity {
    protected BlockPos stationPos;

    public TicketGateTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TicketGateTileEntity() {
        this(TileEntityHandler.TICKET_GATE.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (hasStationPos())
            compound.putIntArray("stationPos", UtilStationConverter.toINTarray(this.stationPos));
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        if (nbt.contains("stationPos"))
            this.stationPos = UtilStationConverter.toBlockPos(nbt, "stationPos");
        else
            this.stationPos = null;
    }

    public Station getStation() {
        return EkiLibApi.getStationByPosition(this.stationPos, UtilDimensionConverter.getDimensionID(this.world)).get();
    }

    public void setStationPos(@Nullable BlockPos stationPos) {
        this.stationPos = stationPos;
        this.markDirty();
    }

    public boolean hasStationPos() {
        return this.stationPos != null;
    }

    public boolean checkStation() {
        if (hasStationPos())
            return EkiLibApi.hasStation(this.stationPos);
        return false;
    }

    public BlockPos getStationPos() {
        return this.stationPos;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 42, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.read(tag);
    }

    @Override
    public CompoundNBT getTileData() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }
}
