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

    public Bus() {
    }

    public Bus(int name, int zoneStart, int maxCapacityBus, int countPassenger, int movementInterval, int travelSpeed) {
        log.info("Автобус " + name + " поехал; " + "Мест " + maxCapacityBus + "; Скорость " + travelSpeed + "; Интервал " + movementInterval);

        this.name = name;
        this.zoneStart = zoneStart;
        this.maxCapacityBus = maxCapacityBus;
        this.countPassenger = countPassenger;
        this.movementInterval = movementInterval;
        this.travelSpeed = travelSpeed;
    }

    @Override
    public void run() {
        try {
            int countStations = Constants.STATIONS_LIST.size();
            boolean flag = true;

            for (int index = getZoneStart(), j = getMovementInterval(); flag;) {

                //region Условие движения по кругу
                if (index == countStations) {
                    index = 0;
                }

                if (index == -1) {
                    index = countStations - 1;
                }

                travelNextStation();
                moveOnStation(index);

                index += j;
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

                if (this.getMovementInterval() == zoneEnd) {
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

    public int getCapacityBus() {
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
                this.monitor.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyAllPassenger() {
        this.notifyAll();
    }

    private void travelNextStation() throws InterruptedException {
        Thread.sleep(getTravelSpeed());
    }

    private void moveOnStation(int i) throws InterruptedException {
        log.info("Автобус " + getName() + " движется на остановку №" + (i + 1));

        Constants.STATIONS_LIST.get(i).busInStation(this);
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
}