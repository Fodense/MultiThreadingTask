package by.brel.Entity;

import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

public class Passenger implements Runnable {

    private static final Logger log = Logger.getLogger(Passenger.class);

    private int name;
    private int zoneStart;
    private int zoneStop;

    public Passenger() {
    }

    public Passenger(int name, int zoneStart, int zoneStop) {
        log.info("Пассажир " + name + " идёт на остановку №" + zoneStart + " хочет доехать до остановки №" + zoneStop);

        this.name = name;
        this.zoneStart = zoneStart;
        this.zoneStop = zoneStop;
    }

    @Override
    public void run() {
        Constants.STATIONS_COUNT_LIST
                .get(getZoneStart() - 1)
                .passengersInStation(getName(), 1)
                .passengersInBus(getName(), getZoneStop());
    }

    public int getName() {
        return name;
    }

    public int getZoneStart() {
        return zoneStart;
    }

    public int getZoneStop() {
        return zoneStop;
    }
}
