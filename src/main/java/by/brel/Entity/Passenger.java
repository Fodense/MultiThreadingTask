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

    public Passenger(int name, int zoneStart, int zoneStop) {
        log.info("Пассажир " + name + " идёт на остановку №" + zoneStart + " хочет доехать до остановки №" + zoneStop);

        this.name = name;
        this.zoneStart = zoneStart;
        this.zoneStop = zoneStop;
    }

    @Override
    public void run() {
        if ((getZoneStart() - getZoneStop()) % 2 == 0) {
            route = 0;

        } else {
            route = 1;
        }

        Constants.STATIONS_COUNT_LIST_FIRST_LINE
                .get(getZoneStart() - 1)
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
