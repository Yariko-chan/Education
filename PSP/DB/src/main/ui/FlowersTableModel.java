package main.ui;

import main.entity.Flower;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class FlowersTableModel extends AbstractTableModel {
    private List<Flower> flowers = new ArrayList<>();

    public FlowersTableModel() {
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
    }

    @Override
    public int getRowCount() {
        return flowers.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0 : return flowers.get(rowIndex).name;
            case 1 : return flowers.get(rowIndex).getDate();
            case 2 : return flowers.get(rowIndex).count;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0 : return "Name";
            case 1 : return "Incoming date";
            case 2 : return "Count";
        }
        return super.getColumnName(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
