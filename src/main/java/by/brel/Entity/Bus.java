package by.brel.Entity;

import by.brel.Main;
import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Bus implements Runnable {

    private static final Logger log = Logger.getLogger(Bus.class);

    private Station station;
    private final Object monitor = new Object();

    private int name;
    private int maxCapacityBus;
    private int countPassenger;
    private double travelSpeed;
    private int route;
    private boolean flag2; //fixed DeadLock
    private boolean direction;
    private double x;
    private int y;
    private int maxX = 1250;
    private int minX = 0;

    public Bus() {
    }

    public Bus(int name, int maxCapacityBus, int countPassenger, double travelSpeed, boolean flag2, boolean direction) {
        log.info("Автобус " + name + " поехал; " + "Мест " + maxCapacityBus + "; Скорость " + travelSpeed + "; Маршрут " + route);

        this.name = name;
        this.maxCapacityBus = maxCapacityBus;
        this.countPassenger = countPassenger;
        this.travelSpeed = travelSpeed;
        this.flag2 = flag2;
        this.direction = direction;
    }

    @Override
    public void run() {
        try {
            log.info("Автобус " + getName() + " начал новый круг");

            moveFirstLine();

            log.info("Автобус " + getName() + " приехал на конечную");

            moveLastLine();

            travelNextStation();
            log.info("Автобус " + getName() + " закончил маршрут");

            if (Constants.livePassengers.get() != 0) {
                log.info("Автобус " + getName() + " поехал на новый круг");

                Main.startAll();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveFirstLine() throws InterruptedException {
        int i = 0;

        x = 0;
        route = 0;

        while (x <= maxX) {
            if (i < Constants.STATIONS_COUNT_MAX) {
                if (Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).getX() <= x) {
                    travelNextStation();

                    log.info(
                            "В| Автобус " + getName() +
                                    " приехал на остановку №" + i +
                                    "; Пассажиров " + getCountPassenger() +
                                    "; Мест " + getFreePlacesBus() +
                                    "; Маршрут " + getRoute()
                    );

                    Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).setBus(this);

                    i++;
                }
            }

            x += travelSpeed;
        }
    }

    private void moveLastLine() throws InterruptedException {
        x = 0;
        route = 1;

        while (x >= minX) {
            for (int i = Constants.STATIONS_COUNT_LIST_FIRST_LINE.size() - 1; i >= 0;) {
                if (Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).getX() >= x) {
                    travelNextStation();

                    log.info(
                            "Н| Автобус " + getName() +
                                    " приехал на остановку №" + i +
                                    "; Пассажиров " + getCountPassenger() +
                                    "; Мест " + getFreePlacesBus() +
                                    "; Маршрут " + getRoute()
                    );

                    Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).setBus(this);

                    i--;
                }
            }

            x -= travelSpeed;
        }
    }

    private void travelNextStation() throws InterruptedException {
        Thread.sleep(Constants.BUS_MOVEMENT_INTERVAL);
    }

    public synchronized void passengersInBus(int name, int zoneEnd) {
        try {

            boolean flag = true;

            while (flag) {
                this.wait();

                if (this.getStation().getNumberStation() == zoneEnd) {
                    Constants.livePassengers.decrementAndGet();

                    log.info("Пассажир " + name + " вышел из автобуса " + getName() + " Вышел на остановке " + this.getStation().getNumberStation());

                    flag = false;


                    this.removePassenger();
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
            this.monitor.notifyAll();
        }
    }

    public void waitBus() {
        synchronized (this) {
            try {
                if (this.isFlag2()) {
                    this.wait();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyAllPassengerInBus() {
        this.notifyAll();
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

    public double getTravelSpeed() {
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}