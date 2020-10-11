package com.chaos.ekiLib.api;

import com.chaos.ekiLib.station.StationManager;
import com.chaos.ekiLib.station.data.Station;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EkiLibApi {
    public static void reloadStations(List<Station> stations) {
        StationManager.INSTANCE.reload(stations);
    }

    public static void addStations(Station... stations) {
        Stream.of(stations).forEach(StationManager.INSTANCE::addStation);
    }

    public static void replaceStationsByPos(Station... stations) {
        Stream.of(stations).forEach(StationManager.INSTANCE::replaceStationByPosition);
    }

    public static boolean deleteStationByName(boolean sensitive, Station... stations) {
        return deleteProcess(sta -> StationManager.INSTANCE.removeStationByName(sta.getName(), sensitive) ? 1 : 0);
    }

    public static boolean deleteStationsByPosition(Station... stations) {
        return deleteProcess(sta -> StationManager.INSTANCE.removeStationByPos(sta.getPosition()) ? 1 : 0);
    }

    public static List<Station> getStationList(int dimensionID) {
        return StationManager.INSTANCE.getStations().stream()
                .filter(station -> station.getDimensionID() == dimensionID)
                .collect(Collectors.toList());
    }

    public static void markDirty() {
        StationManager.INSTANCE.markDirty();
    }

    private static boolean deleteProcess(ToIntFunction<Station> func, Station... stations) {
        int[] result = Stream.of(stations).mapToInt(func).toArray();
        return IntStream.of(result).sum() != 0;
    }
}
