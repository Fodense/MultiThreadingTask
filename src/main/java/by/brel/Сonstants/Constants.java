package by.brel.Сonstants;

import by.brel.Entity.Bus;
import by.brel.Entity.Passenger;
import by.brel.Entity.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class Constants {

    public static List<Station> STATIONS_COUNT_LIST_FIRST_LINE = new ArrayList<>();
    public static List<Station> STATIONS_COUNT_LIST_LAST_LINE = new ArrayList<>();
    public static List<Passenger> PASSENGER_COUNT_LIST = new ArrayList<>();
    public static List<Bus> BUS_COUNT_LIST = new ArrayList<>();

    public static final int magicNumber = 1000000000;
    public static final int maxX = 1350;
    public static final int minX = 0;
    public static final Object superMonitor = new Object();

    public static AtomicInteger livePassengers = new AtomicInteger(0);

    public static int START_MODE              = Integer.parseInt(getProperties().getProperty("working.type"));                      // Вариант запуска
    public static int PASSENGERS_COUNT_MAX    = Integer.parseInt(getProperties().getProperty("passengers.count.max"));              // Общее число пассажиров
    public static int STATIONS_COUNT_MAX      = Integer.parseInt(getProperties().getProperty("stations.count.max"));                // Количество остановок в одну сторону (т.о. все остановки парные)
    public static int BUS_COUNT_MAX           = Integer.parseInt(getProperties().getProperty("bus.count.max"));                     // Количество автобусов
    public static int BUS_CAPACITY            = Integer.parseInt(getProperties().getProperty("bus.capacity"));                      // Вместимость каждого автобуса
    public static int BUS_MOVEMENT_INTERVAL   = Integer.parseInt(getProperties().getProperty("bus.movement.interval"));             // Интервал между автобусами
    public static double BUS_SPEED            = Double.parseDouble(getProperties().getProperty("bus.speed"));                       // Скорость движения
    public static int BUS_ROUTE_MAX           = Integer.parseInt(getProperties().getProperty("bus.route.max"));                     // Кол-во маршрутов

    private static Properties getProperties() {
        File file = new File("src/main/resources/config.properties");

        Properties properties = new Properties();

        try {
            properties.load(new FileReader(file));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
