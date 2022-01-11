import org.apache.log4j.Logger;

import java.util.Random;

public class Passenger implements Runnable {

    private static final Logger log = Logger.getLogger(Passenger.class);

    int zoneStart;
    int zoneStop;

    @Override
    public void run() {
        log.info("Пассажир " + Thread.currentThread().getName() + " Стоит на остановке №" + zoneStart + " катится до остановки №" + zoneStop);

    }

    public Passenger() {
        Random random = new Random();

        this.zoneStart = random.nextInt(4) + 1;
        this.zoneStop = random.nextInt(4) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (zoneStart != passenger.zoneStart) return false;
        return zoneStop == passenger.zoneStop;
    }

    @Override
    public int hashCode() {
        int result = zoneStart;
        result = 31 * result + zoneStop;
        return result;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "zoneStart=" + zoneStart +
                ", zoneStop=" + zoneStop +
                '}';
    }
}
