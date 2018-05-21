package main.utils;

import main.entity.Flower;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class DBHelper {
    private static final String TABLE_NAME = "Flowers";

    private static volatile DBHelper instance;

    private Connection db;
    private Statement statement;

    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    try {
                        instance = new DBHelper();
                    } catch (ClassNotFoundException|SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    public DBHelper() throws ClassNotFoundException, SQLException {
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        File dbFile = new File("flowers.mdb");
        String path = dbFile.getAbsolutePath();
        db = DriverManager.getConnection("jdbc:ucanaccess://" + path);
        statement = db.createStatement();
    }

    public List<Flower> getFlowersList() {
        List<Flower> list = new ArrayList<>();
        try {
            String sq_str = "SELECT * FROM " + TABLE_NAME;
            ResultSet rs = statement.executeQuery(sq_str);
            while (rs.next()) {
                Flower f  = new Flower();
                f.id = rs.getInt("ID");
                f.name = rs.getString("FlowerName");
                f.incomingDate = rs.getDate("IncomingDate").getTime();
                f.count = rs.getInt("Count");
                list.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addFlower(Flower f) {
        String sq_str = "INSERT INTO " + TABLE_NAME + "(FlowerName, IncomingDate, Count)  VALUES ('"
                + f.name + "','"
                + new Date(f.incomingDate) + "','"
                + f.count + "')";
        try {
            int rs = statement.executeUpdate(sq_str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateFlower(int id, int newCount) {
        String sq_str = "UPDATE " + TABLE_NAME
                + " SET Count='" + newCount
                + "' WHERE ID='" + id + "'";
        try {
            int rs = statement.executeUpdate(sq_str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFlower(int id) {
        String sq_str = "DELETE FROM " + TABLE_NAME + " WHERE ID ='" + id + "'";
        try {
            int rs = statement.executeUpdate(sq_str);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // test
    public static void main(String[] args) {
        Flower f = new Flower("Rome", new java.util.Date().getTime(), 100);
        DBHelper.getInstance().addFlower(f);
        DBHelper.getInstance().updateFlower(6, 10);
//        main.utils.DBHelper.getInstance().removeFlower(5);
        List<Flower> list = DBHelper.getInstance().getFlowersList();
        DBHelper.getInstance().close();

    }
}
