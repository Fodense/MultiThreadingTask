package by.brel.Сonstants;

import by.brel.Entity.Station;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Constants {

    public static ArrayList<Station> STATIONS_LIST = new ArrayList<>();

    public static final int PASSENGERS_COUNT_MAX = Integer.parseInt(getProperties().getProperty("passengers.count.max"));              // Общее число пассажиров
    public static final int STATIONS_COUNT_MAX = Integer.parseInt(getProperties().getProperty("stations.count.max"));                  // Количество остановок в одну сторону (т.о. все остановки парные)
    public static final int BUS_COUNT_MAX = Integer.parseInt(getProperties().getProperty("bus.count.max"));                            // Количество автобусов
    public static final int BUS_CAPACITY = Integer.parseInt(getProperties().getProperty("bus.capacity"));                              // Вместимость каждого автобуса
    public static final int BUS_MOVEMENT_INTERVAL = Integer.parseInt(getProperties().getProperty("bus.movement.interval"));            // Интервал движения
    public static final int BUS_SPEED = Integer.parseInt(getProperties().getProperty("bus.speed"));                                    // Скорость движения

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
