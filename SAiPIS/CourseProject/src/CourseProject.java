import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseProject implements ActionListener {
    private JPanel mainPanel;
    private JSpinner a1;
    private JSpinner a2;
    private JSpinner a3;
    private JSpinner a4;
    private JSpinner a5;
    private JSpinner s1;
    private JSpinner s2;
    private JSpinner s3;
    private JSpinner s4;
    private JSpinner s5;
    private JSpinner loss;
    private JSpinner price;
    private JSpinner rawPrice;
    private JSpinner probability;
    private JLabel vald;
    private JLabel savidje;
    private JLabel gurwiz;
    private JButton okButton;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int profit = (int) price.getValue() - (int) rawPrice.getValue();
        int loss = (int) this.loss.getValue();
        JSpinner[] productionOptions = {a1, a2, a3, a4, a5};
        JSpinner[] demandOptions = {s1, s2, s3, s4, s5};
        int pOptCount = productionOptions.length;
        int dOptCount = demandOptions.length;

        // матрица прибыли
        int[][] profitMatrix = new int[pOptCount][dOptCount];
        for (int i = 0; i < pOptCount; i++) {
            for (int j = 0; j < dOptCount; j++) {
                int production = (int) productionOptions[i].getValue();
                int demand = (int) demandOptions[j].getValue();
                profitMatrix[i][j] = Math.min(production, demand) * profit -
                        (production > demand ? production - demand : 0) * loss;
            }
        }

        int[] minProfitVector = new int[pOptCount];
        for (int i = 0; i < pOptCount; i++) {
            minProfitVector[i] = min(profitMatrix[i]);
        }
        int valdCriteriaOption = maxIndex(minProfitVector);

        // матрица потерь
        int[][] lossMatrix = new int[pOptCount][dOptCount];
        for (int j = 0; j < dOptCount; j++) {
            int columnMax = max(profitMatrix[j]);
            for (int i = 0; i < pOptCount; i++) {
                lossMatrix[i][j] = columnMax - profitMatrix[i][j];
            }
        }
        int[] maxLossVector = new int[pOptCount];
        for (int i = 0; i < pOptCount; i++) {
            maxLossVector[i] = max(lossMatrix[i]);
        }
        int savidjeCriteriaOption = minIndex(maxLossVector);

        //гурвиц
        double alpha = (double) probability.getValue();
        int[] maxProfitVector = new int[pOptCount];
        for (int i = 0; i < pOptCount; i++) {
            maxProfitVector[i] = max(profitMatrix[i]);
        }
        double[] gurwitzCriterias = new double[pOptCount];
        for (int i = 0; i < pOptCount; i++) {
            gurwitzCriterias[i] = alpha*maxProfitVector[i] + (1 - alpha)*minProfitVector[i];
        }
        int gurwitzCriteriaOption = maxIndex(gurwitzCriterias);

        vald.setText("" + (valdCriteriaOption + 1));
        savidje.setText("" + (savidjeCriteriaOption + 1));
        gurwiz.setText("" + (gurwitzCriteriaOption + 1));
    }

    private void initUI() {
        okButton.addActionListener(this);

        a1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        a2.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        a3.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        a4.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        a5.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        s1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        s2.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        s3.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        s4.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        s5.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        loss.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        price.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        rawPrice.setModel(new SpinnerNumberModel(0, 0, 100, 1));
        probability.setModel(new SpinnerNumberModel(0.0f, 0f, 1f, 0.1f));
    }

    private void start() {
        JFrame jFrame = new JFrame("KR1");
        jFrame.setContentPane(this.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        initUI();

    }
    public static void main(String[] args) {
        CourseProject ui = new CourseProject();
        ui.start();
    }

    private static int min(int[] array) {
        int minValue = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }

    private static int max(int[] array) {
        int maxValue = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return maxValue;
    }

    private static int minIndex(int[] array) {
        int minIndex = 0;
        int minValue = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private static int maxIndex(int[] array) {
        int maxIndex = 0;
        int maxValue = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static int maxIndex(double[] array) {
        int maxIndex = 0;
        double maxValue = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
