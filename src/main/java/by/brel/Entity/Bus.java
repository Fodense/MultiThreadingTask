package by.brel.Entity;

import by.brel.Main;
import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

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

    public Bus(int name, int maxCapacityBus, int countPassenger, double travelSpeed, int route, boolean flag2, boolean direction) {
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
        boolean flag = true;//уберу меби

        if (Constants.livePassengers.get() == 0) {
            flag = false;
        }

        x = 0;
        route = 0;
        moveFirstLine();

        x = 0;
        route = 1;
        moveLastLine();


        System.out.println(Constants.livePassengers.get());
        if (Constants.livePassengers.get() != 0) {
            Main.startAll();
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

    private void moveFirstLine() {
        int i = 0;

        while (x <= maxX) {
            if (i < Constants.STATIONS_COUNT_MAX) {
                if (Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).getX() <= x) {
                    try {
                        Thread.sleep(Constants.BUS_MOVEMENT_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    log.info("В| Автобус " + getName() + " приехал на остановку №" + i + "; Пассажиров " + getCountPassenger() + "; Мест " + getFreePlacesBus() + "; Маршрут " + getRoute());

                    Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i).setBus(this);

                    i++;
                }
            }

            x += travelSpeed;
        }
    }

    private void moveLastLine() {
        int i = 0;

        while (x <= maxX) {
            if (i < Constants.STATIONS_COUNT_MAX) {
                if (Constants.STATIONS_COUNT_LIST_LAST_LINE.get(i).getX() <= x) {
                    try {
                        Thread.sleep(Constants.BUS_MOVEMENT_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    log.info("Н| Автобус " + getName() + " приехал на остановку №" + i + "; Пассажиров " + getCountPassenger() + "; Мест " + getFreePlacesBus() + "; Маршрут " + getRoute());

                    Constants.STATIONS_COUNT_LIST_LAST_LINE.get(i).setBus(this);

                    i++;
                }
            }

            x += travelSpeed;
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