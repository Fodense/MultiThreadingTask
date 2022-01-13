package by.brel.Utils;

import by.brel.Сonstants.Constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Util {

    public static int getRandomInt(int max) {
        Random random = new Random();

        return random.nextInt(max) + 1;
    }

    public static int getRandomIntOutExp(int min, int max, int exp) {
        while (true) {
            int result = (int) (Math.random() * (max - min + 1) + min);

            if (result != exp) {
                return result;
            }
        }
    }

    public static Set getRouteSet() {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < Constants.STATIONS_COUNT_MAX; i++) {
            set.add(getRandomInt(Constants.STATIONS_COUNT_MAX));
        }

        System.out.println(set);

        return set;
    }
}
