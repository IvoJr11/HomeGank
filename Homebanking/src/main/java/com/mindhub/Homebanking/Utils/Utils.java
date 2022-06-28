package com.mindhub.Homebanking.Utils;


import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static int getRandomNumber(int min, int max) {

        List<Integer> cont = new ArrayList<>();

        Integer numberss;
        do {

            numberss = (int) ((Math.random() * (max - min)) + min);
        } while (cont.contains(numberss));

        cont.add(numberss);
        return numberss;
    }


}
