package by.brel.Entity;

import org.apache.log4j.Logger;

import java.util.Objects;

public class Station {

    private static final Logger log = Logger.getLogger(Station.class);

    private Bus bus;
    private final Object monitor = new Object();

    private int numberStation;
    private int countPassengersInStation;

    public Station() {
    }

    public Station(int numberStation) {
        this.numberStation = numberStation;
    }

    public synchronized Bus passengersInStation(int name, int route) {
        log.info("Пассажир " + name + " прибыл на остановку " +  getNumberStation() + "; Маршрут " + route);

        boolean flag = true;

        try {
            this.countPassengersInStation++;

            while (flag) {
                this.wait();

                if (bus.getRoute() == route && bus.getFreePlacesBus() > 0) {
                    bus.addPassenger();
                    this.countPassengersInStation--;

                    flag = false;

                    log.info("Пассажир " + name + " сел в автобус " + bus.getName());

                }

                if (bus.getFreePlacesBus() == 0 || this.countPassengersInStation == 0) {
                    bus.notifyBus();

                } else if (bus.getCountPassenger() == 0) {
                    bus.waitBus();
                }

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
            bus.setFlag2(false);
            this.notifyAll();
        }
    }

    public int getNumberStation() {
        return numberStation;
    }
}