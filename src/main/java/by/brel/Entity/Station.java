package by.brel.Entity;

import by.brel.Сonstants.Constants;
import org.apache.log4j.Logger;

import java.util.Objects;

public class Station {

    private static final Logger log = Logger.getLogger(Station.class);

    private Bus bus;
    private final Object monitor = new Object();

    private int numberStation;
    private int countPassengersInStation;
    private int x;
    private int y;

    public Station() {
    }

    public Station(int numberStation, int x) {
        this.numberStation = numberStation;
        this.x = x;
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

            // Возможен nullPointer
            if (bus.getX() >= this.x) {
                bus.setStation(this);
            }
            if (bus.getX() >= Constants.STATIONS_COUNT_LIST.get(Constants.STATIONS_COUNT_LIST.size() - 1).getX() * Constants.STATIONS_COUNT_LIST.size()) {
                bus.setX(100);
            }

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

    public void setNumberStation(int numberStation) {
        this.numberStation = numberStation;
    }

    public int getCountPassengersInStation() {
        return countPassengersInStation;
    }

    public void setCountPassengersInStation(int countPassengersInStation) {
        this.countPassengersInStation = countPassengersInStation;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
