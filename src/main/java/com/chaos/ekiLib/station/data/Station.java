package com.chaos.ekiLib.station.data;

import net.minecraft.util.math.BlockPos;

public class Station {
    protected String name;
    protected BlockPos position;
    protected EnumStationLevel level;
    protected int dimensionID;

    public Station(String name, BlockPos pos, EnumStationLevel lvl, int dimensionID) {
        this.name = name;
        this.position = pos;
        this.level = lvl;
        this.dimensionID = dimensionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BlockPos getPosition() {
        return position;
    }

    public String getFormmatedPosition() {
        return String.format("(%d, %d, %d)", position.getX(), position.getY(), position.getZ());
    }

    public EnumStationLevel getLevel() {
        return level;
    }

    public void setLevel(EnumStationLevel level) {
        this.level = level;
    }

    public int getDimensionID() {
        return dimensionID;
    }

    public boolean hasNull() {
        return name == null || position == null || level == null;
    }

    public boolean equalsByName(Station station, boolean sensitive) {
        return sensitive ? name.equals(station.name) : name.equalsIgnoreCase(station.name);
    }

    public boolean equalsByName(String name, boolean sensitive) {
        return sensitive ? this.name.equals(name) : this.name.equalsIgnoreCase(name);
    }

    public boolean equalsByPos(Station station) {
        return station.position.equals(position);
    }

    public boolean equalsByPos(BlockPos pos) {
        return position.equals(pos);
    }
}
