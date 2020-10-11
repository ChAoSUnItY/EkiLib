package com.chaos.ekiLib.station;

import com.chaos.ekiLib.EkiLib;
import com.chaos.ekiLib.event.EventStationChanged;
import com.chaos.ekiLib.station.data.Station;
import com.chaos.ekiLib.utils.handlers.PacketHandler;
import com.chaos.ekiLib.utils.network.PacketStationChanged;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class StationManager {
    public static final StationManager INSTANCE = new StationManager();
    private List<Station> stations;
    private File location;

    public void init(File worldDir) {
        stations = Lists.newArrayList();
        scanAllStations(worldDir);
    }

    private void scanAllStations(File worldDir) {
        location = new File(worldDir, "eki-stations");
        location.mkdirs();

        File[] stationsRaw = location.listFiles();
        EkiLib.LOGGER.info("================================");
        EkiLib.LOGGER.info("INIT STATIONS...");
        EkiLib.LOGGER.info("================================");
        for (File staR : stationsRaw)
            try {
                register(staR);
            } catch (Exception e) {
                e.printStackTrace();
            }
        EkiLib.LOGGER.info("================================");
    }

    private void register(File stationJSON) throws FileNotFoundException {
        FileReader reader = new FileReader(stationJSON);
        Gson gson = new Gson();

        JsonReader jsonReader = new JsonReader(reader);
        Station sta = gson.fromJson(jsonReader, Station.class);

        if (sta.hasNull()) {
            EkiLib.LOGGER.info(String.format("%s - %s | (Failed to load: Contains null)",
                    Optional.of(sta.getName()).orElse("\\033[3mNull\\033[0m"),
                    Optional.of(sta.getFormmatedPosition()).orElse("\\033[3mNull\\033[0m")));
        } else {
            stations.add(sta);
            EkiLib.LOGGER.info(String.format("%s - %s | (Loaded)", sta.getName(), sta.getFormmatedPosition()));
        }
    }

    private void saveStationToJSON(Station station) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(getFile(station))) {
            gson.toJson(station, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveStations() {
        stations.forEach(s -> saveStationToJSON(s));
        stations.clear();
    }

    public void reload(List<Station> stations) {
        this.stations.clear();
        this.stations = stations;
    }

    private File getFile(Station station) {
        return new File(location, String.format("%s.json", station.getFormmatedPosition()));
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

    public boolean addStation(Station station) {
        if (has(station.getPosition()))
            return false;

        stations.add(station);
        return true;
    }

    public boolean replaceStationByPosition(Station station) {
        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).equalsByPos(station)) {
                stations.set(i, station);
                return true;
            }
        return false;
    }

    public boolean replaceStationByName(Station station, boolean sensitive) {
        for (int i = 0; i < stations.size(); i++)
            if (stations.get(i).equalsByName(station, sensitive)) {
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
        PacketHandler.INSTANCE.sendToServer(new PacketStationChanged(stations));
    }
}
