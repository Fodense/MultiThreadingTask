package by.brel.Entity;

import org.apache.log4j.Logger;

public class Station {

    private static final Logger log = Logger.getLogger(Station.class);

    private Bus bus;

    public Object passengerOut = new Object();
    public Object passengerEntered = new Object();

    private int numberStation;
    private int countPassengersInStation;
    private int x;
    private int y;

    public Station() {
    }

    public Station(int numberStation, int x, int countPassengersInStation) {
        this.numberStation = numberStation;
        this.x = x;
        this.countPassengersInStation = countPassengersInStation;
    }

    public synchronized Bus passengersInStation(int name, int route) {
        log.info("Пассажир " + name + " прибыл на остановку " +  getNumberStation() + "; Маршрут " + route);

        boolean flag = true;

        try {
            while (flag) {
                this.wait();

                if (bus.getRoute() == route && bus.getFreePlacesBus() > 0) {
                    bus.addPassenger();

                    this.countPassengersInStation--;

                    flag = false;

                    log.info("Пассажир " + name + " сел в автобус " + bus.getName() + " Сел на остановке " + this.getNumberStation() + "; asd " + bus.getRoute());
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

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;

        synchronized (this) {
            bus.setStation(this);

            if (bus.getCountPassenger() != 0) {
                bus.notifyAllPassengerInBus();
                bus.waitBus();
            }

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
