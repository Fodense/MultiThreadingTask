package by.brel;

import by.brel.Entity.Bus;
import by.brel.Entity.Passenger;
import by.brel.Entity.Station;
import by.brel.Utils.Util;
import by.brel.Сonstants.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        init();
    }

    public static void startAll(){
        startPassenger();
        startBus();
    }

    public static void init() throws InterruptedException {
        createStation();
        createPassenger();
        createBus();
    }

    public static void createStation() {
        for (int i = 1; i <= Constants.STATIONS_COUNT_MAX; i++) {
            Constants.STATIONS_COUNT_LIST.add(new Station(i));
        }
    }

    public static void createPassenger() {
        for (int i = 1; i <= Constants.PASSENGERS_COUNT_MAX; i++) {
            Constants.livePassengers.incrementAndGet();

            Random random = new Random();

            int zoneStart = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;
            int zoneStop = random.nextInt(Constants.STATIONS_COUNT_MAX) + 1;

            Constants.PASSENGER_COUNT_LIST.add(
                    new Passenger(
                            i,
                            zoneStart,
                            zoneStop
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

    public static void createBus() throws InterruptedException {
        for (int i = 1; i <= Constants.BUS_COUNT_MAX; i++) {
            Constants.BUS_COUNT_LIST.add(
                    new Bus(
                        i,
                        Constants.BUS_CAPACITY,
                        0,
                        Constants.BUS_SPEED,
                        Util.getRandomInt(Constants.BUS_ROUTE_MAX),
                        true,
                        Util.getRandomBoolean()
                    )
            );
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
