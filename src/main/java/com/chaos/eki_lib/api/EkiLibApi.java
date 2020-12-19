package com.chaos.eki_lib.api;

import com.chaos.eki_lib.station.data.Station;
import com.chaos.eki_lib.utils.handlers.StationHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EkiLibApi {
    public static void reloadStations(List<Station> stations) {
        StationHandler.INSTANCE.reload(stations);
    }

    public static boolean hasStation(BlockPos pos) {
        return StationHandler.INSTANCE.has(pos);
    }

    public static Optional<Station> getStationByPosition(BlockPos pos, ResourceLocation dimension) {
        return StationHandler.INSTANCE.getStations().stream().filter(station -> station.getPosition().equals(pos) && station.getDimension().equals(dimension)).findFirst();
    }

    public static void addStations(Station... stations) {
        Stream.of(stations).forEach(StationHandler.INSTANCE::addStation);
    }

    public static void replaceStations(Station... stations) {
        Stream.of(stations).forEach(StationHandler.INSTANCE::replaceStation);
    }

    public static boolean deleteStationByName(boolean sensitive, Station... stations) {
        return deleteProcess(sta -> StationHandler.INSTANCE.removeStationByName(sta.getName(), sensitive) ? 1 : 0, stations);
    }

    public static void deleteStationsByPosition(Station... stations) {
        deleteProcess(sta -> StationHandler.INSTANCE.removeStationByPos(sta.getPosition()) ? 1 : 0, stations);
    }

    public static List<Station> getStationList(ResourceLocation dimension) {
        return StationHandler.INSTANCE.getStations().stream()
                .filter(station -> station.getDimension().equals(dimension))
                .collect(Collectors.toList());
    }

    /***
     * Call this method every time player modifies a station from client side.
     */
    public static void markDirty() {
        StationHandler.INSTANCE.markDirty();
    }

    private static boolean deleteProcess(ToIntFunction<Station> func, Station... stations) {
        int[] result = Stream.of(stations).mapToInt(func).toArray();
        return IntStream.of(result).sum() != 0;
    }
}
