package by.brel.Entity;

import org.apache.log4j.Logger;

public class Station {

    private static final Logger log = Logger.getLogger(Station.class);
    private Bus bus;

    private int numberStation;
    private int countPassenger;

    public Station() {
    }

    public Station(int numberStation) {
        this.numberStation = numberStation;
    }

    public synchronized Bus passengerWaitBus(int numberStation) {
        this.countPassenger++;
        boolean flag = true;

        try {
            while (flag) {
                log.info("Пассажир " + Thread.currentThread().getName() + " прибыл на остановку и ожидает автобус");
                this.wait();

                if (bus.getCapacityBus() > 0 && bus.getMovementInterval() == numberStation) {
                    bus.addPassenger();
                    bus.notifyBus();

                    this.countPassenger--;

                    flag = false;

                    log.info("Пассажир сел в автобус");

                } else {
                    log.info("Мест в автобусе " + bus + " нет");

                    this.wait();
                }

                return bus;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void busWaitPassenger(Bus bus) {
        synchronized (this) {

            bus.setStation(this);

            if (bus.getCountPassenger() != 0) {
                bus.notifyAllPassenger();
                bus.waitBus();
            }

            this.bus = bus;
            this.notifyAll();
        }
    }
}
