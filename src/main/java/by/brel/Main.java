package by.brel;

import by.brel.Entity.Bus;
import by.brel.Entity.Passenger;
import by.brel.Entity.Station;
import by.brel.Utils.Util;
import by.brel.Сonstants.Constants;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        init();
    }

    private static void init() throws InterruptedException {
        createStation();
        createPassenger();
        createBus();
    }

    private static void createStation() {
        for (int i = 1; i <= Constants.STATIONS_COUNT_MAX; i++) {
            Constants.STATIONS_LIST.add(new Station(i));
        }
    }

    private static void createPassenger() {
        for (int i = 1; i <= Constants.PASSENGERS_COUNT_MAX; i++) {
            Random random = new Random();

            int zoneStart = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;
            int zoneStop = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;

            Passenger passenger = new Passenger(i, zoneStart, zoneStop);
            Thread threadPassenger = new Thread(passenger);

            threadPassenger.start();
        }
    }

    private static void createBus() throws InterruptedException {
        for (int i = 1; i <= Constants.BUS_COUNT_MAX; i++) {
            Thread.sleep(Constants.BUS_MOVEMENT_INTERVAL);

            Bus bus = new Bus(
                    i,
                    Constants.BUS_CAPACITY,
                    0,
                    Constants.BUS_SPEED,
                    Util.getRandomInt(Constants.BUS_ROUTE_MAX),
                    true,
                    Util.getRandomBoolean()
            );
            Thread threadBus = new Thread(bus);

            threadBus.start();
        }
    }
}
