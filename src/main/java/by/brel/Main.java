package by.brel;

import by.brel.Entity.Bus;
import by.brel.Entity.Passenger;
import by.brel.Entity.Station;
import by.brel.Сonstants.Constants;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        init();
    }

    private static void init() {
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
//            Random random = new Random();
//
//            int zoneStart = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;
//            int zoneStop = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;

            Passenger passenger = new Passenger(i, 1, 2);
            Thread threadPassenger = new Thread(passenger);

            threadPassenger.start();
        }
    }

    private static void createBus() {
        for (int i = 1; i <= Constants.BUS_COUNT_MAX; i++) {
            Random random = new Random();

            int interval = random.nextInt(2);

            Bus bus = new Bus(i, 0, Constants.BUS_CAPACITY, 0, interval, Constants.BUS_SPEED);
            Thread threadBus = new Thread(bus);

            threadBus.start();
        }
    }
}
