import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Разработать приложение, обеспечивающее поиск в двух списках несовпадающих фрагментов текста.
 * Строки, в которых будут найдены искомые фрагменты, должны быть выведены в диалогове окно (Dialog)
 * (предполагается, что несколько строк может иметь такие фрагменты). Помимо этого приложение должно
 * обеспечивать управление содержимым списков – добавление нового элемента, редактирование, удаление.
 *
 *  в каждом списке есть текст, содержащий строки. Мы берем поочередно каждое слово первой строчки
 *  и сравниваем со словами первой строчки второго списка (и только!).
 *  Если ни одного совпадения нет - выводим эти строки, если какое-то слово первой строки первого
 *  списка встретилось в первом предложении второго списка, то предложения не выводим в диалог.
 *  Потом слова из второго предложения первого списка сравниваем со словами второго предложения
 *  второго списка. Проверка на отсутствие необходимого количества предложений, то есть например
 *  в первом списке 10 предложений, а во втором - 8, то только будет 8 сравнений.
 *  Или 7 - в первом, 10- во втором, то будет 7 сравнений
 *
 * Created by Ganeeva Diana in Май, 2018
 * for Lists
 */
public class Main {
    public static final String SAVE = "Save";
    public static final String ADD = "Add";
    private JFrame mainFrame;
    private JPanel root;
    private JButton compareBtn;
    private JList<String> leftList;
    private JList<String> rightList;

    private JButton leftEditBtn;
    private JButton leftRemoveBtn;
    private JTextField leftET;
    private JButton leftAddBtn;
    private Integer leftEditIndex;

    private JButton rightEditBtn;
    private JButton rightRemoveBtn;
    private JTextField rightET;
    private JButton rightAddBtn;
    private Integer rightEditIndex;

    private ArrayList<String> leftListStrings = new ArrayList<>();
    private ArrayList<String> rightListStrings = new ArrayList<>();

    public static void main(String[] args) {
        Main ui = new Main();
        ui.start();
    }

    private void start() {
        mainFrame = new JFrame("Main");
        mainFrame.setContentPane(this.root);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

        init();

    }

    private void init() {
        // init lists
        leftListStrings.add("Jane Doe");
        leftListStrings.add("John Smith");
        leftListStrings.add("Kathy Green");
        rightListStrings.add("Jane Doe");
        rightListStrings.add("John Smith");
        rightListStrings.add("Kathy Green");
        updateLeft();
        updateRight();

        // add string from textField, if not empty
        // if editing, saves changes
        leftAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newString = leftET.getText();
                if (!newString.isEmpty()) {
                    leftET.setText("");
                    if (leftAddBtn.getText().equals(SAVE)) {
                        leftListStrings.set(leftEditIndex, newString);
                        leftAddBtn.setText(ADD);
                    } else {
                        leftListStrings.add(0, newString);
                    }
                    updateLeft();
                }
            }
        });
        rightAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newString = rightET.getText();
                if (!newString.isEmpty()) {
                    rightET.setText("");
                    if (rightAddBtn.getText().equals(SAVE)) {
                        rightListStrings.set(rightEditIndex, newString);
                        rightAddBtn.setText(ADD);
                    } else {
                        rightListStrings.add(0, newString);
                    }
                    updateRight();
                }
            }
        });

        // set first selected index to textField, make addBtn text "Save"
        leftEditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListSelectionModel lsm = leftList.getSelectionModel();
                List<Integer> selectedIndices = getSelectedIndices(lsm);
                if (selectedIndices.isEmpty()) {
                    leftRemoveBtn.setEnabled(false);
                } else {
                    leftEditIndex = selectedIndices.get(0);
                    String forEdit = leftListStrings.get(leftEditIndex);
                    leftET.setText(forEdit);
                    leftAddBtn.setText(SAVE);

                    leftEditBtn.setEnabled(false);
                    leftRemoveBtn.setEnabled(false);
                }
            }
        });
        rightEditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListSelectionModel lsm = rightList.getSelectionModel();
                List<Integer> selectedIndices = getSelectedIndices(lsm);
                if (selectedIndices.isEmpty()) {
                    rightRemoveBtn.setEnabled(false);
                } else {
                    rightEditIndex = selectedIndices.get(0);
                    String forEdit = rightListStrings.get(rightEditIndex);
                    rightET.setText(forEdit);
                    rightAddBtn.setText(SAVE);

                    rightEditBtn.setEnabled(false);
                    rightRemoveBtn.setEnabled(false);
                }
            }
        });

        // remove selected strings
        leftRemoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListSelectionModel lsm = leftList.getSelectionModel();
                List<Integer> removeIndices = getSelectedIndices(lsm);
                if (!removeIndices.isEmpty()) {
                    for (int i = removeIndices.size() - 1;
                            i >=0; i--) {
                        leftListStrings.remove((int) removeIndices.get(i));
                    }
                    updateLeft();
                }
                leftEditBtn.setEnabled(false);
                leftRemoveBtn.setEnabled(false);
            }
        });
        rightRemoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListSelectionModel lsm = rightList.getSelectionModel();
                List<Integer> removeIndices = getSelectedIndices(lsm);
                if (!removeIndices.isEmpty()) {
                    for (int i = removeIndices.size() - 1;
                         i >=0; i--) {
                        rightListStrings.remove((int) removeIndices.get(i));
                    }
                    updateRight();
                }
                rightEditBtn.setEnabled(false);
                rightRemoveBtn.setEnabled(false);
            }
        });

        compareBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // compare arrays
                String[] unmatched = compare(leftListStrings.toArray(new String[0]), rightListStrings.toArray(new String[0]));

                // create single String with results
                StringBuilder unmatchStrings = new StringBuilder();
                if (unmatched.length == 0) {
                    unmatchStrings.append("All strings are have matching words!");
                } else {
                    for (String s : unmatched) {
                        unmatchStrings.append(s).append("\n");
                    }
                }
                // show dialog with results
                JOptionPane.showMessageDialog(mainFrame,
                        unmatchStrings.toString(),
                        "Unmatching strings",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // if something selected, set edit and remove enabled
        leftList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = leftList.getSelectionModel();
                if (!lsm.isSelectionEmpty()) {
                    leftEditBtn.setEnabled(true);
                    leftRemoveBtn.setEnabled(true);
                } else {
                    leftEditBtn.setEnabled(false);
                    leftRemoveBtn.setEnabled(false);
                }
            }
        });
        rightList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = rightList.getSelectionModel();
                if (!lsm.isSelectionEmpty()) {
                    rightEditBtn.setEnabled(true);
                    rightRemoveBtn.setEnabled(true);
                } else {
                    rightEditBtn.setEnabled(false);
                    rightRemoveBtn.setEnabled(false);
                }
            }
        });
    }

    /**
     * set strings from ArrayList to list model
     */
    private void updateLeft() {
        DefaultListModel<String> leftListModel = createListModelFromList(leftListStrings);
        leftList.setModel(leftListModel);
    }
    private void updateRight() {
        DefaultListModel<String> leftListModel = createListModelFromList(rightListStrings);
        rightList.setModel(leftListModel);
    }

    /**
     * creates DefaultListModel for JList from ArrayList<Strings>
     * @param stringsList - source String list
     * @return DefaultListModel for JList
     */
    private DefaultListModel<String> createListModelFromList(ArrayList<String> stringsList) {
        DefaultListModel<String> leftListModel = new DefaultListModel<>();
        for (String s : stringsList) {
            leftListModel.addElement(s);
        }
        return leftListModel;
    }

    /**
     * в каждом списке есть текст, содержащий строки. Мы берем поочередно каждое слово первой строчки
     * и сравниваем со словами первой строчки второго списка (и только!).
     * Если ни одного совпадения нет - выводим эти строки, если какое-то слово первой строки первого
     * списка встретилось в первом предложении второго списка, то предложения не выводим в диалог.
     * Потом слова из второго предложения первого списка сравниваем со словами второго предложения
     * второго списка. Проверка на отсутствие необходимого количества предложений, то есть например
     * в первом списке 10 предложений, а во втором - 8, то только будет 8 сравнений.
     * Или 7 - в первом, 10- во втором, то будет 7 сравнений
     * @param first list to compare
     * @param second list to compare
     * @return array of unmatching strings
     */

    private String[] compare(String[] first, String[] second) {
        ArrayList<String> results = new ArrayList<>();
        if (first == null || second == null ||
                first.length == 0 || second.length == 0) {
            return (String[]) results.toArray();
        }
        for (int i = 0; i < first.length && i < second.length; i++) {
            if (first[i].equals(second[i])) {
                continue;
            }
            String[] firstWords = first[i].split(" ");
            String[] secondWords = second[i].split(" ");
            boolean hasEqualWords = false;
            for (int j = 0; j < firstWords.length; j++) {
                String word = firstWords[j];
                for (int k = 0; k < secondWords.length; k++) {
                    if (word.equals(secondWords[k])) {
                        hasEqualWords = true;
                        break;
                    }
                }
                if (hasEqualWords) {
                    break;
                }
            }
            if (!hasEqualWords) {
                results.add(first[i] + " " + second[i]);
            }
        }
        return results.toArray(new String[0]);
    }

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
}
