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
        if (Constants.START_MODE == 1) {
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
            Constants.livePassengers.incrementAndGet();

            Random random = new Random();

            int zoneStart = random.nextInt(Constants.STATIONS_COUNT_MAX);
            int zoneStop = random.nextInt(Constants.STATIONS_COUNT_MAX);

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

    public static void createStation() {
        for (int i = 0; i < Constants.STATIONS_COUNT_MAX; i++) {

            int countPassengersInStationsFirstLine = 0;
            int countPassengersInStationsLastLine = 0;

            for (int j = 0; j < Constants.PASSENGER_COUNT_LIST.size(); j++) {

                if (Constants.PASSENGER_COUNT_LIST.get(j).getZoneStart() == i && Constants.PASSENGER_COUNT_LIST.get(j).getRoute() == 0) {
                    countPassengersInStationsFirstLine++;
                }

                if (Constants.PASSENGER_COUNT_LIST.get(j).getZoneStart() == i && Constants.PASSENGER_COUNT_LIST.get(j).getRoute() == 1) {
                    countPassengersInStationsLastLine++;
                }
            }

            if (i == 0) {
                Constants.STATIONS_COUNT_LIST_FIRST_LINE.add(
                        new Station(
                                i,
                                100,
                                countPassengersInStationsFirstLine
                        )
                );

                Constants.STATIONS_COUNT_LIST_LAST_LINE.add(
                        new Station(
                                i,
                                100,
                                countPassengersInStationsLastLine
                        )
                );

            } else {
                Constants.STATIONS_COUNT_LIST_FIRST_LINE.add(
                        new Station(
                                i,
                                Constants.STATIONS_COUNT_LIST_FIRST_LINE.get(i - 1).getX() + 100,
                                countPassengersInStationsFirstLine
                        )
                );

                Constants.STATIONS_COUNT_LIST_LAST_LINE.add(
                        new Station(
                                i,
                                Constants.STATIONS_COUNT_LIST_LAST_LINE.get(i - 1).getX() + 100,
                                countPassengersInStationsLastLine
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
                        0,
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
