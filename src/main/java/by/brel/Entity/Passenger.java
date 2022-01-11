package by.brel.Entity;

import org.apache.log4j.Logger;

public class Passenger implements Runnable {

    private static final Logger log = Logger.getLogger(Passenger.class);

    private int zoneStart;
    private int zoneStop;

    @Override
    public void run() {
        log.info("Пассажир " + Thread.currentThread().getName() + " Идёт на остановку №" + zoneStart + " хочет доехать до остановки №" + zoneStop);

        int vector;

        if ((zoneStart - zoneStop) > 0)
            vector = -1;
        else
            vector = 1;

//        Main.stationsList.get(zoneStart).passengerWaitBus(vector).passengersInBus(zoneStop);
    }

    public Passenger() {
    }

    public Passenger(int zoneStart, int zoneStop) {
        this.zoneStart = zoneStart;
        this.zoneStop = zoneStop;
    }
}
