package main.ui;

import main.entity.Flower;
import main.ui.FlowersTableModel;
import main.utils.DBHelper;
import main.utils.FileUtils;
import main.utils.Utils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class Ui {
    private JFrame mainFrame;
    private JPanel root;
    private JTable flowersTable;
    private JTextField findTF;
    private JButton findBtn;
    private JButton findClearBtn;
    private JComboBox sortCB;
    private JButton sortBtn;
    private JSpinner countSpinner;
    private JButton countBtn;
    private JButton removeBtn;
    private JTextField newNameTF;
    private JSpinner newCountSpinner;
    private JButton addNewBtn;
    private JButton saveToFileBtn;

    private FlowersTableModel tableModel;
    private List<Flower> flowers = new ArrayList<>();

    private String[] sortOptions = {"By name", "By date"};

    public void start() {
        mainFrame = new JFrame("Main");
        mainFrame.setContentPane(this.root);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

        init();

    }

    private void init() {
        // init table
        tableModel = new FlowersTableModel();
        flowersTable.setModel(tableModel);
        ListSelectionModel selectionModel = flowersTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectionModel.addListSelectionListener(tableSelectListener);

        // set models
        sortCB.setModel(new DefaultComboBoxModel<String>(sortOptions));
        sortCB.setSelectedIndex(0);
        SpinnerNumberModel model = new SpinnerNumberModel(10, 0, 200, 10);
        newCountSpinner.setModel(model);

        // set action listeners
        sortBtn.addActionListener(sortListener);
        removeBtn.addActionListener(removeListener);
        addNewBtn.addActionListener(addListener);
        countBtn.addActionListener(countListener);
        findBtn.addActionListener(findListener);
        findClearBtn.addActionListener(clearListener);
        saveToFileBtn.addActionListener(saveToFileListener);

        // load db data
        tableModel.setFlowers(flowers);
        updateTable();
    }

    private ActionListener sortListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sortFlowers(flowers, sortCB.getSelectedIndex());
            tableModel.fireTableDataChanged();
        }
    };

    // remove selected indices
    private ActionListener removeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListSelectionModel lsm = flowersTable.getSelectionModel();
            List<Integer> removeIndices = getSelectedIndices(lsm);
            if (!removeIndices.isEmpty()) {
                for (int i = removeIndices.size() - 1; i >=0; i--) {
                    DBHelper.getInstance().removeFlower(flowers.get(removeIndices.get(i)).id);
                    updateTable();
                }
            }
            removeBtn.setEnabled(false);
            countBtn.setEnabled(false);
        }
    };

    // add new Flower data
    private ActionListener addListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = newNameTF.getText();
            int count = (int) newCountSpinner.getValue();
            if (!name.isEmpty() && count != 0) {
                DBHelper.getInstance().addFlower(
                        new Flower(name, new Date().getTime(), count));
                updateTable();
            }
            newNameTF.setText(""); // clear
        }
    };

    // updates count for every selected flower
    // or
    // if count == 0 deletes
    private ActionListener countListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ListSelectionModel lsm = flowersTable.getSelectionModel();
            List<Integer> indices = getSelectedIndices(lsm);
            int count = (int) countSpinner.getValue();
            if (!indices.isEmpty()) {
                for (int i = indices.size() - 1; i >=0; i--) {
                    int flowerId = flowers.get(indices.get(i)).id;
                    if (count == 0) {
                        DBHelper.getInstance().removeFlower(flowerId);
                    } else {
                        DBHelper.getInstance().updateFlower(flowerId, count);
                    }
                }
                updateTable();
            }
            removeBtn.setEnabled(false);
            countBtn.setEnabled(false);
        }
    };

    // find flower by entered name
    private ActionListener findListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = findTF.getText();
            if (!query.isEmpty()) {
                List<Integer> indices = Utils.findByName(query, flowers);
                List<Flower> found = new ArrayList<>(indices.size());
                for (int i : indices) {
                    found.add(flowers.get(i));
                }
                flowers.clear();
                flowers.addAll(found);
                sortFlowers(flowers, sortCB.getSelectedIndex());
                tableModel.fireTableDataChanged();
            }
        }
    };

    // clears find field, shows all flowers in table
    private ActionListener clearListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            findTF.setText("");
            updateTable();
        }
    };

    // saves visible now strings to file
    private ActionListener saveToFileListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder data = new StringBuilder();
            for (Flower f : flowers) {
                data.append(f.toString()).append("\n");
            }
            String filename = "Flowers_"
                    + new SimpleDateFormat("dd.MM.yyyy_hh.mm.ss").format(new Date())
                    + ".txt";
            try {
                FileUtils.writeToFile(filename, data.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    // make remove & edit btns available if smthng selected
    private ListSelectionListener tableSelectListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = flowersTable.getSelectionModel();
            if (!lsm.isSelectionEmpty()) {
                countBtn.setEnabled(true);
                removeBtn.setEnabled(true);

                List<Integer> indices = getSelectedIndices(lsm);
                countSpinner.setValue(flowers.get(indices.get(0)).count);
            } else {
                countBtn.setEnabled(false);
                removeBtn.setEnabled(false);
            }
        }
    };

    /**
     * utility function for retrieving selected indices from ListSelectionModel
     * @param lsm
     * @return List<Integer> of selected indices
     *         empty List<Integer> if nothing selected
     */
    private List<Integer> getSelectedIndices(ListSelectionModel lsm) {
        List<Integer> removeIndices = new ArrayList<>();
        if (!lsm.isSelectionEmpty()) {
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                    removeIndices.add(i);
                }
            }
        }
        return removeIndices;
    }

    /**
     * loads data from DB
     */
    private void updateTable() {
        flowers.clear();
        flowers.addAll(DBHelper.getInstance().getFlowersList());
        sortFlowers(flowers, sortCB.getSelectedIndex());
        tableModel.fireTableDataChanged();
    }

    private void sortFlowers(List<Flower> flowers, int selectedIndex) {
        switch (selectedIndex) {
            case 1:
                Utils.sortByDateAsc(flowers);
                break;
            case 0:
            default:
                Utils.sortByName(flowers);
                break;
        }
    }
}
