package by.brel.Entity;

import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

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
    private boolean flag2;

    public Bus() {
    }

    public Bus(int name, int zoneStart, int maxCapacityBus, int countPassenger, int movementInterval, int travelSpeed, boolean flag2) {
        log.info("Автобус " + name + " поехал; " + "Мест " + maxCapacityBus + "; Скорость " + travelSpeed + "; Маршрут " + movementInterval);

        this.name = name;
        this.zoneStart = zoneStart;
        this.maxCapacityBus = maxCapacityBus;
        this.countPassenger = countPassenger;
        this.movementInterval = movementInterval;
        this.travelSpeed = travelSpeed;
        this.flag2 = flag2;
    }

    @Override
    public void run() {
        try {
            int countStations = Constants.STATIONS_LIST.size();

            for (int zone = getZoneStart(), interval = getMovementInterval(); true;) {
                if (zone == countStations) {
                    zone = 0;
                }

                if (zone == -1) {
                    zone = countStations - 1;
                }

                travelNextStation();
                moveOnStation(zone);

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

                    log.info("Пассажир " + name + " вышел");

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
            log.info("Автобус " + getName() + " движется на остановку №" + (i + 1));

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

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }
}