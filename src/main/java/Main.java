import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(new FileReader(file));

        int numberPassengers = Integer.parseInt(properties.getProperty("number.passengers"));   // Общее число пассажиров
        int numberStops = Integer.parseInt(properties.getProperty("number.stops"));             // Количество остановок в одну сторону (т.о. все остановки парные)
        int numberBuses = Integer.parseInt(properties.getProperty("number.buses"));             // Количество автобусов
        int capacityBus = Integer.parseInt(properties.getProperty("capacity.bus"));             // Вместимость каждого автобуса
        int movementInterval = Integer.parseInt(properties.getProperty("movement.interval"));   // Интервал движения
        int travelSpeed = Integer.parseInt(properties.getProperty("travel.speed"));             // Скорость движения

        Runnable bus = null;
        Runnable passenger = null;
        Thread threadBus = null;
        Thread threadPassenger = null;

        for (int i = 0; i < 4; i++) {
            bus = new Bus();
            threadBus = new Thread(bus);

            threadBus.start();
        }

        for (int i = 0; i < 4; i++) {
            passenger = new Passenger();
            threadPassenger = new Thread(passenger);

            threadPassenger.start();
        }
    }
}
