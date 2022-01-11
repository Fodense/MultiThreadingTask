package by.brel.Entity;

import org.apache.log4j.Logger;

public class Bus implements Runnable {

    private static final Logger log = Logger.getLogger(Bus.class);

    private Station station;
    private final Object sleepObj = new Object();

    private int zoneStart;
    private int maxCapacityBus;
    private int countPassenger;
    private int movementInterval;
    private int travelSpeed;
    int idleCount = 0;

    public Bus() {
    }

    public Bus(int zoneStart, int maxCapacityBus, int countPassenger, int movementInterval, int travelSpeed) {
        this.zoneStart = zoneStart;
        this.maxCapacityBus = maxCapacityBus;
        this.countPassenger = countPassenger;
        this.movementInterval = movementInterval;
        this.travelSpeed = travelSpeed;
    }

    @Override
    public void run() {
        log.info("Автобус " + Thread.currentThread().getName() + " поехал; " + "Мест " + maxCapacityBus + "; Скорость " + travelSpeed);

        try {
            int countStations = Main.stationsList.size();
            boolean flag = true;
            int maxIdleCount = countStations * 2 + 2;//макс. пустых остановок= полный маршрут +2 ост.

            for (int index = zoneStart, j = movementInterval; flag;) {

                //region Условие движения по кругу
                if (index == countStations) {
                    index = 0;
                }

                if (index == -1) {
                    index = countStations - 1;
                }

                if (countPassenger == 0)
                    idleCount++;
                else
                    idleCount = 0;

                if (idleCount >= maxIdleCount)
                    flag = false;


                move();
                step(index);
                index += j;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void passengersInBus(int zoneEnd) {
        try {
            log.debug(Thread.currentThread().getName() + " Сидел в автобусе");

            boolean flag = true;

            while (flag) {
                this.wait();

                if (this.movementInterval == zoneEnd) {
                    this.removePassenger();

                    log.info(Thread.currentThread().getName()+" вышел");

                    flag = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getCapacityBus() {
        return (maxCapacityBus - countPassenger);
    }

    public synchronized void addPassenger() {
        this.countPassenger++;
    }

    public synchronized void removePassenger() {
        this.countPassenger--;
    }

    public synchronized void notifyBus() {
        synchronized (this.sleepObj) {
            this.sleepObj.notify();
        }
    }

    public void waitBus() {
        synchronized (this.sleepObj) {
            try {
                this.sleepObj.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void waitPassenger() {
        try {
            this.wait();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void notifyAllPassenger() {
        this.notifyAll();
    }

    private void move() throws InterruptedException {
        Thread.sleep(getTravelSpeed());
    }

    private void step(int i) throws InterruptedException {
        log.info("Автобус " + Thread.currentThread().getName() + " движется на остановку №" + i);

        Main.stationsList.get(i).busWaitPassenger(this);
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

    public void setZoneStart(int zoneStart) {
        this.zoneStart = zoneStart;
    }

    public int getMaxCapacityBus() {
        return maxCapacityBus;
    }

    public void setMaxCapacityBus(int maxCapacityBus) {
        this.maxCapacityBus = maxCapacityBus;
    }

    public int getCountPassenger() {
        return countPassenger;
    }

    public void setCountPassenger(int countPassenger) {
        this.countPassenger = countPassenger;
    }

    public int getMovementInterval() {
        return movementInterval;
    }

    public void setMovementInterval(int movementInterval) {
        this.movementInterval = movementInterval;
    }

    public int getTravelSpeed() {
        return travelSpeed;
    }

    public void setTravelSpeed(int travelSpeed) {
        this.travelSpeed = travelSpeed;
    }
}
