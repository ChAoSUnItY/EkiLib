package com.chaos.eki_lib.utils.handlers;

import com.chaos.eki_lib.event.EventStationChanged;
import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.network.PacketInitStationHandler;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;
import java.util.function.Predicate;

public class StationHandler {
    public static final StationHandler INSTANCE = new StationHandler();
    private List<Station> stations = Lists.newArrayList();

    public void init(List<Station> stations) {
        this.stations = stations;
    }

    public void reload(List<Station> stations) {
        clear();
        init(stations);
    }

    public void clear() {
        stations.clear();
    }

    public List<Station> getStations() {
        return Lists.newArrayList(stations);
    }

    public boolean has(BlockPos pos) {
        return has(s -> s.equalsByPos(pos));
    }

    public boolean has(String name, boolean sensitive) {
        return has(s -> s.equalsByName(name, sensitive));
    }

    private boolean has(Predicate<? extends Station> predicate) {
        return stations.stream().anyMatch((Predicate<? super Station>) predicate);
    }

    /***
     * Add Station.
     * @param station
     * @return if it's position already has another station, then terminates the process and return false, otherwise return true.
     */
    public boolean addStation(Station station) {
        if (has(station.getPosition()))
            return false;

        stations.add(station);
        return true;
    }

    /***
     * Replace Station By Position.
     * @param station
     * @return if station is not exists, then terminates the process and return false, otherwise return true.
     */
    public boolean replaceStation(Station station) {
        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).equalsByPos(station)) {
                stations.set(i, station);
                return true;
            }
        return false;
    }

    public boolean removeStationByName(String name, boolean sensitive) {
        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).equalsByName(name, sensitive)) {
                stations.remove(i);
                return true;
            }
        return false;
    }

    public boolean removeStationByPos(BlockPos pos) {
        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).equalsByPos(pos)) {
                stations.remove(i);
                return true;
            }
        return false;
    }

    public void markDirty() {
        MinecraftForge.EVENT_BUS.post(new EventStationChanged.Client(stations));
        PacketHandler.INSTANCE.sendToServer(new PacketInitStationHandler.PacketReloadStationHandler(stations));
    }
}
