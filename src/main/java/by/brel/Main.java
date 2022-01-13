package by.brel;

import by.brel.Entity.Bus;
import by.brel.Entity.Passenger;
import by.brel.Entity.Station;
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

//            int zoneStart = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;
//            int zoneStop = getRandomInt(zoneStart, Constants.STATIONS_COUNT_MAX, zoneStart);

            Passenger passenger = new Passenger(i, zoneStart, zoneStop);
            Thread threadPassenger = new Thread(passenger);

            threadPassenger.start();
        }
    }

//    private static int getRandomInt(int min, int max, int exp) {
//        boolean flag = true;
//
//        while (flag) {
//            int result = (int) (Math.random() * (max - min + 1) + min);
//
//            if (result != exp) {
//                return result;
//            }
//        }
//
//        return max;
//    }

    private static void createBus() throws InterruptedException {
        for (int i = 1; i <= Constants.BUS_COUNT_MAX; i++) {
            Bus bus = new Bus(i, 0, Constants.BUS_CAPACITY, 0, Constants.BUS_MOVEMENT_INTERVAL, Constants.BUS_SPEED, getRandomInt(), true);
            Thread threadBus = new Thread(bus);

            threadBus.start();
        }
    }

    private static int getRandomInt() {
        Random random = new Random();

        return random.nextInt(2) + 1;
    }
}
