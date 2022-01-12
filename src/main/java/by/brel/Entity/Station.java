package by.brel.Entity;

import org.apache.log4j.Logger;

import java.util.Objects;

public class Station {

    private static final Logger log = Logger.getLogger(Station.class);
    private Bus bus;
    private final Object monitor = new Object();

    private int numberStation;

    public Station() {
    }

    public Station(int numberStation) {
        this.numberStation = numberStation;
    }

    public synchronized Bus passengersInStation(int name, int zoneStart, int number) {
        log.info("Пассажир " + name + " прибыл на остановку " +  numberStation + " и ожидает автобус " + number);

        boolean flag = true;

        try {
            while (flag) {
                this.wait();

                if (bus.getMovementInterval() == number && bus.getFreePlacesBus() > 0) {
                    bus.addPassenger();

                    flag = false;

                    log.info("Пассажир " + name + " сел в автобус " + bus.getName());

                }

                bus.notifyBus();

                if (!flag) {
                    return bus;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void busInStation(Bus bus) {
        synchronized (this) {

            bus.setStation(this);

            if (bus.getCountPassenger() != 0) {
                bus.notifyAllPassengerInBus();
                bus.waitBus();
            }

            this.bus = bus;
            this.notifyAll();
        }
    }

    public int getNumberStation() {
        return numberStation;
    }
}
