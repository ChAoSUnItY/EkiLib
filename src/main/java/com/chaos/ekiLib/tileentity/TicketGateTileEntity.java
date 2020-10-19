package com.chaos.ekiLib.tileentity;

import com.chaos.ekiLib.api.EkiLibApi;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.TileEntityHandler;
import com.chaos.ekiLib.utils.util.UtilDimensionConverter;
import com.chaos.ekiLib.utils.util.UtilStationConverter;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
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
        compound.put("targetStation", this.station != null ? UtilStationConverter.toNBT(this.station) : new CompoundNBT());
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.getCompound("targetStation").size() != 0)
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
        return EkiLibApi.getStationList(UtilDimensionConverter.dimensionKeyToID(this.world.getDimensionKey()).getAsInt()).contains(this.station);
    }

    public Station getStation() {
        return this.station;
    }
}
