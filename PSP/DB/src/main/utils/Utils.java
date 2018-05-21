package main.utils;

import main.entity.Flower;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class Utils {

    // static methods only
    private Utils() {
    }

    public static List<Flower> sortByName(List<Flower> flowers) {
        flowers.sort(new Comparator<Flower>() {
            @Override
            public int compare(Flower o1, Flower o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        return flowers;
    }

    public static List<Flower> sortByDateAsc(List<Flower> flowers) {
        flowers.sort(new Comparator<Flower>() {
            @Override
            public int compare(Flower o1, Flower o2) {
                return Long.compare(o1.incomingDate, o2.incomingDate);
            }
        });
        return flowers;
    }

    public static List<Integer> findByName(String name, List<Flower> flowers) {
        List<Integer> indices = new ArrayList<>();
        sortByName(flowers);
        for (int i = 0; i < flowers.size(); i++) {
            if (flowers.get(i).name.equals(name)) {
                indices.add(i);
            }
        }

        return indices;
    }
}
