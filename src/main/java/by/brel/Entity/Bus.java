package by.brel.Entity;

import by.brel.Utils.Util;
import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.Set;

public class Bus implements Runnable {

    private static final Logger log = Logger.getLogger(Bus.class);

    private Station station;
    private final Object monitor = new Object();

    private int name;
    private int zoneStart;
    private int maxCapacityBus;
    private int countPassenger;
    private int movementInterval;
    private int travelSpeed;
    private int route;
    private boolean flag2; //fixed DeadLock

    public Bus() {
    }

    public Bus(int name, int zoneStart, int maxCapacityBus, int countPassenger, int movementInterval, int travelSpeed, int route, boolean flag2) {
        log.info("Автобус " + name + " поехал; " + "Мест " + maxCapacityBus + "; Скорость " + travelSpeed + "; Маршрут " + route);

        this.name = name;
        this.zoneStart = zoneStart;
        this.maxCapacityBus = maxCapacityBus;
        this.countPassenger = countPassenger;
        this.movementInterval = movementInterval;
        this.travelSpeed = travelSpeed;
        this.route = route;
        this.flag2 = flag2;
    }

    @Override
    public void run() {
        try {
            int countStations = Constants.STATIONS_LIST.size();

            for (int zone = 0, interval = getRoute(); true;) {
                if (zone == countStations) {
//                    this.setRoute(Utils.getRandomInt(Constants.BUS_ROUTE_MAX));

//                    zone = Utils.getRandomInt(Constants.STATIONS_COUNT_MAX);

                    zone = 0;
                }


                if (zone >= countStations) {
                    zone = 0;
                }

                travelNextStation();
                moveOnStation(zone, interval);

                zone += interval;

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void passengersInBus(int name, int zoneEnd) {
        try {
            log.info("Пассажир " + name +" сидит в автобусе " + getName());

            boolean flag = true;

            while (flag) {
                this.wait();

                if (this.getStation().getNumberStation() == zoneEnd) {
                    this.removePassenger();

                    log.info("Пассажир " + name + " вышел из автобуса " + getName());

                    flag = false;
                }

                if (this.getCountPassenger() == 0) {
                    this.notifyBus();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getFreePlacesBus() {
        return (maxCapacityBus - countPassenger);
    }

    public void addPassenger() {
        this.countPassenger++;
    }

    public void removePassenger() {
        this.countPassenger--;
    }

    public void notifyBus() {
        synchronized (this.monitor) {
            this.monitor.notify();
        }
    }

    public void waitBus() {
        synchronized (this.monitor) {
            try {
                if (this.isFlag2()) {
                    this.monitor.wait();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyAllPassengerInBus() {
        this.notifyAll();
    }

    private void travelNextStation() throws InterruptedException {
        Thread.sleep(getTravelSpeed());
    }

    private void moveOnStation(int i, int j) throws InterruptedException {
        synchronized (this.monitor) {
            log.info("Автобус " + getName() + " движется на остановку №" + (i + 1) + "; Пассажиров " + getCountPassenger());
            log.debug(i + "|" + j);

            Constants.STATIONS_LIST.get(i).busInStation(this);
        }
    }

    public int getName() {
        return name;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public int getZoneStart() {
        return zoneStart;
    }

    public int getMaxCapacityBus() {
        return maxCapacityBus;
    }

    public int getCountPassenger() {
        return countPassenger;
    }

    public int getMovementInterval() {
        return movementInterval;
    }

    public int getTravelSpeed() {
        return travelSpeed;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }
}