package by.brel.Entity;

import by.brel.Utils.Util;
import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

import java.util.List;

public class Bus implements Runnable {

    private static final Logger log = Logger.getLogger(Bus.class);

    private Station station;
    private final Object monitor = new Object();

    private int name;
    private int maxCapacityBus;
    private int countPassenger;
    private int movementInterval;
    private int travelSpeed;
    private int route;
    private boolean flag2; //fixed DeadLock
    private boolean direction;

    public Bus() {
    }

    public Bus(int name, int maxCapacityBus, int countPassenger, int travelSpeed, int route, boolean flag2, boolean direction) throws InterruptedException {
        log.info("Автобус " + name + " поехал; " + "Мест " + maxCapacityBus + "; Скорость " + travelSpeed + "; Маршрут " + route);

        this.name = name;
        this.maxCapacityBus = maxCapacityBus;
        this.countPassenger = countPassenger;
        this.travelSpeed = travelSpeed;
        this.route = route;
        this.flag2 = flag2;
        this.direction = direction;
    }

    @Override
    public void run() {
        try {
            int countStations = Constants.STATIONS_LIST.size();

            for (int i = 0; true;) {
                int interval = getRoute();

                if (i >= countStations) {
                    this.direction = Util.getRandomBoolean();

                    i = 0;
                }

                if (isDirection() && interval % 2 == 0) {
                    interval = 1;
                    this.direction = Util.getRandomBoolean();
                }

                if (!isDirection()) {
                    interval = 2;
                    this.direction = Util.getRandomBoolean();
                }

                travelNextStation();
                moveOnStation(i);

                i += interval;

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

    private void moveOnStation(int i) throws InterruptedException {
        synchronized (this.monitor) {
            log.info("Автобус " + getName() + " движется на остановку №" + (i + 1) + "; Пассажиров " + getCountPassenger() + "; Мест " + getFreePlacesBus());

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

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }
}