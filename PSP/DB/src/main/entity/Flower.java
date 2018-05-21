package main.entity;

import java.text.SimpleDateFormat;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class Flower {

    public int id;
    public String name;
    public long incomingDate;
    public int count;

    public Flower() {
    }

    public Flower(String name, long incomingDate, int count) {
        this.name = name;
        this.incomingDate = incomingDate;
        this.count = count;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(incomingDate);
    }

    @Override
    public String toString() {
        return name + " came " + getDate() + ", count " + count;
    }
}
