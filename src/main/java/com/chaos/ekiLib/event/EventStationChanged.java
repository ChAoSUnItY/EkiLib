package com.chaos.ekiLib.event;

import com.chaos.ekiLib.station.data.Station;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class EventStationChanged extends Event {
    protected List<Station> newStations;

    private EventStationChanged(List<Station> newStations) {
        this.newStations = newStations;
    }

    public List<Station> getNewStations() {
        return newStations;
    }

    public static class Client extends EventStationChanged {
        public Client(List<Station> newStations) {
            super(newStations);
        }
    }

    public static class Server extends EventStationChanged {
        public Server(List<Station> newStations) {
            super(newStations);
        }
    }

    public static class Both extends EventStationChanged {
        public Both(List<Station> newStations) {
            super(newStations);
        }
    }
}
