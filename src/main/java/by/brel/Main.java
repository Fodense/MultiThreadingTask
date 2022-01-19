package by.brel;

import by.brel.Entity.Bus;
import by.brel.Entity.Passenger;
import by.brel.Entity.Station;
import by.brel.UI.MainFrame;
import by.brel.Utils.Util;
import by.brel.Сonstants.Constants;

import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (Constants.START_MODE <= 1) {
            init();
            startPassenger();
            startAll();

        } else {
            MainFrame.main();
        }
    }

    public static void startAll(){
        startBus();
    }

    public static void init() throws InterruptedException {
        createPassenger();
        createStation();
        createBus();
    }

    public static void createPassenger() {
        for (int i = 0; i < Constants.PASSENGERS_COUNT_MAX; i++) {
            Random random = new Random();

            int zoneStart = random.nextInt(Constants.STATIONS_COUNT_MAX);
            int zoneStop = random.nextInt(Constants.STATIONS_COUNT_MAX);
//            int zoneStart = 2;
//            int zoneStop = 0;

            if (zoneStart == zoneStop) {
                i--;

            } else {
                Constants.livePassengers.incrementAndGet();

                int route;

                if ((zoneStart - zoneStop) < 0) {
                    route = 0;

                } else {
                    route = 1;
                }

                Constants.PASSENGER_COUNT_LIST.add(
                        new Passenger(
                                i,
                                zoneStart,
                                zoneStop,
                                route
                        )
                );
            }
        }
    }

    public static void createStation() {

        for (int i = 0; i < Constants.STATIONS_COUNT_MAX; i++) {
            if (i == 0) {
                Constants.STATIONS_COUNT_LIST_FIRST_LINE.add(
                        new Station(
                                i,
                                100
                        )
                );

            } else {
                Constants.STATIONS_COUNT_LIST_FIRST_LINE.add(
                        new Station(
                                i,
                                Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i - 1).getX() + 100
                        )
                );
            }
        }
    }

    public static void createBus() throws InterruptedException {
        for (int i = 0; i < Constants.BUS_COUNT_MAX; i++) {
            Constants.BUS_COUNT_LIST.add(
                    new Bus(
                        i,
                        Constants.BUS_CAPACITY,
                        0,
                        Constants.BUS_SPEED,
                        true,
                        Util.getRandomBoolean()
                    )
            );
        }
    }

    public static void startPassenger() {
        Thread[] threadsPassenger = new Thread[Constants.PASSENGER_COUNT_LIST.size()];

        for (int i = 0; i < threadsPassenger.length; i++) {
            threadsPassenger[i] = new Thread(Constants.PASSENGER_COUNT_LIST.get(i));

            threadsPassenger[i].start();
        }
    }

    public static void startBus() {
        Thread[] threadsBus = new Thread[Constants.BUS_COUNT_LIST.size()];

        for (int i = 0; i < threadsBus.length; i++) {
            threadsBus[i] = new Thread(Constants.BUS_COUNT_LIST.get(i));

            try {
                Thread.sleep(Constants.BUS_MOVEMENT_INTERVAL);
                threadsBus[i].start();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
