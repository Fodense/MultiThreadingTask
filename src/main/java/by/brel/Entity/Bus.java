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
//            x = 0;
//            route = 0;
//            moveFirstLine();

            x = 0;
            route = 1;
            moveLastLine();

            if (Constants.livePassengers.get() != 0) {
                Main.startAll();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//        try {
//            int countStations = Constants.STATIONS_COUNT_LIST_FIRST_LINE.size();
//
//            for (int i = 0; true;) {
//                int interval = 1;
//
//                if (countStations <= i) {
//                    x = 100;
//                    i = 0;
//
//                    if (Constants.livePassengers.get() == 0) {
//                        Thread.sleep(5000);
//
//                        System.exit(0);
//                    }
//                }
//
//                travelNextStation();
//                moveOnStation(i);
//
//                i += interval;
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void moveFirstLine() throws InterruptedException {
        int i = 0;

        while (x <= Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(Constants.STATIONS_COUNT_LIST_FIRST_LINE.size() - 1).getX() + 100) {
            if (i < Constants.STATIONS_COUNT_MAX) {
                if (Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).getX() <= x) {
                    travelNextStation();

                    Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).setBus(this);

                    log.info(
                            "В| Автобус " + getName() +
                                    " приехал на остановку №" + i +
                                    "; Пассажиров " + getCountPassenger() +
                                    "; Мест " + getFreePlacesBus() +
                                    "; Маршрут " + getRoute()
                    );

                    i++;
                }
            }

            x += travelSpeed;
        }
    }

    private void moveLastLine() throws InterruptedException {
        int i = 0;

        while (x <= maxX) {
            if (i < Constants.STATIONS_COUNT_LIST_LAST_LINE.size()) {

                if (Constants.STATIONS_COUNT_LIST_LAST_LINE.get(i).getX() <= x) {
                    travelNextStation();

                    Constants.STATIONS_COUNT_LIST_LAST_LINE.get(i).setBus(this);

                    log.info(
                            "Н| Автобус " + getName() +
                                    " приехал на остановку №" + i +
                                    "; Пассажиров " + getCountPassenger() +
                                    "; Мест " + getFreePlacesBus() +
                                    "; Маршрут " + getRoute()
                    );

                    i++;
                }
            }

            x = x + 100;
        }
    }

    private void travelNextStation() throws InterruptedException {
        Thread.sleep(Constants.BUS_MOVEMENT_INTERVAL);
    }

    private void moveOnStation(int i) throws InterruptedException {
        synchronized (this.monitor) {
            x = x + travelSpeed;

            Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).setBus(this);

            log.info("Автобус " + getName() + " движется на остановку №" + i + "; Пассажиров " + getCountPassenger() + "; Мест " + getFreePlacesBus());
        }
    }

    public synchronized void passengersInBus(int name, int zoneEnd) {
        try {

            boolean flag = true;

            while (flag) {
                this.wait();

                if (this.getStation().getNumberStation() == zoneEnd) {
                    Constants.livePassengers.decrementAndGet();

                    this.removePassenger();

                    log.info("Пассажир " + name + " вышел из автобуса " + getName() + " Вышел на остановке " + this.getStation().getNumberStation());

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