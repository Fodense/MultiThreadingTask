import by.brel.Entity.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class Main {

//    public static ArrayList<Station> stationsList = new ArrayList<>();



    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        int countPassengers = Integer.parseInt(properties.getProperty("passengers.count.max"));   // Общее число пассажиров
        int countStations = Integer.parseInt(properties.getProperty("stations.count.max"));       // Количество остановок в одну сторону (т.о. все остановки парные)
        int countBuses = Integer.parseInt(properties.getProperty("bus.count.max"));               // Количество автобусов
        int maxCapacityBus = Integer.parseInt(properties.getProperty("bus.capacity"));            // Вместимость каждого автобуса
        int movementInterval = Integer.parseInt(properties.getProperty("movement.interval"));     // Интервал движения
        int busSpeed = Integer.parseInt(properties.getProperty("bus.speed"));                     // Скорость движения

        Random random = new Random();

        Runnable bus = null;
        Runnable passenger = null;
        Thread threadBus = null;
        Thread threadPassenger = null;

        //Инженерим автобусы


        //Строим остановки


        //Рожаем пассажиров

    }

    private static void init() {

    }

//    private static void createBus() {
//        for (int i = 1; i <= countBuses; i++) {
//            bus = new Entity.Bus(1, maxCapacityBus, 0, movementInterval, busSpeed);
//            threadBus = new Thread(bus);
//
//            threadBus.start();
//        }
//    }
//
//    private static void createPassenger() {
//        for (int i = 1; i <= countPassengers; i++) {
//            int zoneStart = random.nextInt(countStations);
//            int zoneStop = random.nextInt(countStations);
//
//            passenger = new Entity.Passenger(zoneStart, zoneStop);
//            threadPassenger = new Thread(passenger);
//
//            threadPassenger.start();
//        }
//    }
//
//    private static void createStation() {
//        for (int i = 1; i <= countStations; i++) {
//            stationsList.add(new Entity.Station(i));
//        }
//    }
}
