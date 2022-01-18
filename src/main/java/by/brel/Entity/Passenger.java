package by.brel.Entity;

import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

public class Passenger implements Runnable {

    private static final Logger log = Logger.getLogger(Passenger.class);

    private int name;
    private int zoneStart;
    private int zoneStop;
    private int route;

    public Passenger() {
    }

    public Passenger(int name, int zoneStart, int zoneStop, int route) {
        log.info("Пассажир " + name + " идёт на остановку №" + zoneStart + " хочет доехать до остановки №" + zoneStop);

        this.name = name;
        this.zoneStart = zoneStart;
        this.zoneStop = zoneStop;
        this.route = route;
    }

    @Override
    public void run() {

        Constants.STATIONS_COUNT_LIST_FIRST_LINE
                .get(getZoneStart())
                .passengersInStation(getName(), route)
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

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }
}
