package by.brel.Utils;

import by.brel.Сonstants.Constants;

import java.util.*;

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

    public static List getRouteList() {
        int[] ints = new int[5];

        for (int i = 0; i < 5; i++) {
            ints[i] = getRandomInt(5);
        }

        return getUniqueList(ints);
    }

    private static List getUniqueList(int[] arr) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }

        List list = new ArrayList(set);;

        return list;
    }

    public static boolean getRandomBoolean() {
        Random random = new Random();

        return random.nextBoolean();
    }
}
