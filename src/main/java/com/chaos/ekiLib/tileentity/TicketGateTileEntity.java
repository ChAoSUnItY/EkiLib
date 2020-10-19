package com.chaos.ekiLib.tileentity;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.TileEntityHandler;
import com.chaos.ekiLib.utils.util.UtilDimensionConverter;
import com.chaos.ekiLib.utils.util.UtilStationConverter;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class TicketGateTileEntity extends TileEntity {
    protected Station station;

    public TicketGateTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TicketGateTileEntity() {
        this(TileEntityHandler.TICKET_GATE.get());
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (hasStation())
            compound.put("targetStation", UtilStationConverter.toNBT(this.station));
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("targetStation"))
            this.station = UtilStationConverter.toStation(nbt.getCompound("targetStation"));
        else
            this.station = null;
    }

    public void setStation(@Nullable Station station) {
        this.station = station;
        this.markDirty();
    }

    public boolean hasStation() {
        return this.station != null;
    }

    public boolean checkStation() {
        if (hasStation())
            return EkiLibApi.getStationList(UtilDimensionConverter.dimensionKeyToID(this.world.getDimensionKey()).getAsInt()).stream().anyMatch(station -> station.equalsByPos(this.station.getPosition()));
        return false;
    }

    public Station getStation() {
        return this.station;
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
        this.read(this.world.getBlockState(this.pos), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public CompoundNBT getTileData() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }
}
