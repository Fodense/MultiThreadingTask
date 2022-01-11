import org.apache.log4j.Logger;

import java.util.Random;

public class Bus implements Runnable {

    private static final Logger log = Logger.getLogger(Bus.class);

    int countPassenger;

    @Override
    public void run() {
        log.info("Автобус № " + Thread.currentThread().getName() + " поехал; " + "Мест " + countPassenger);
    }

    public Bus() {
        Random random = new Random();

        this.countPassenger = random.nextInt(4) + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bus bus = (Bus) o;

        return countPassenger == bus.countPassenger;
    }

    @Override
    public int hashCode() {
        return countPassenger;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "countPassenger=" + countPassenger +
                '}';
    }
}
