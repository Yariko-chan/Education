import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpertWeighting implements ActionListener {
    private JPanel mainPanel;
    private JSpinner e1z1;
    private JSpinner e1z2;
    private JSpinner e1z3;
    private JSpinner e2z1;
    private JSpinner e2z2;
    private JSpinner e2z3;
    private JSpinner e3z1;
    private JSpinner e3z2;
    private JSpinner e3z3;
    private JSpinner e4z1;
    private JSpinner e4z2;
    private JSpinner e4z3;
    private JSpinner r1;
    private JSpinner r2;
    private JSpinner r3;
    private JSpinner r4;
    private JLabel ro1;
    private JLabel ro2;
    private JLabel ro3;
    private JLabel ro4;
    private JLabel answer;
    private JButton okButton;
    private JLabel w1;
    private JLabel w2;
    private JLabel w3;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        double r1 =(double) this.r1.getValue();
        double r2 =(double) this.r2.getValue();
        double r3 =(double) this.r3.getValue();
        double r4 =(double) this.r4.getValue();
        double rSum = r1 + r2 + r3;
        double ro1 = r1/rSum;
        double ro2 = r2/rSum;
        double ro3 = r3/rSum;
        double ro4 = r4/rSum;
        this.ro1.setText(String.format("%.2f", ro1));
        this.ro2.setText(String.format("%.2f", ro2));
        this.ro3.setText(String.format("%.2f", ro3));
        this.ro4.setText(String.format("%.2f", ro4));

        double w1 = (int)e1z1.getValue()*ro1
                + (int)e2z1.getValue()*ro2
                + (int)e3z1.getValue()*ro3
                + (int)e4z1.getValue()*ro4;
        double w2 = (int)e1z2.getValue()*ro1
                + (int)e2z2.getValue()*ro2
                + (int)e3z2.getValue()*ro3
                + (int)e4z2.getValue()*ro4;
        double w3 = (int)e1z3.getValue()*ro1
                + (int)e2z3.getValue()*ro2
                + (int)e3z3.getValue()*ro3
                + (int)e4z3.getValue()*ro4;
        this.w1.setText(String.format("%.2f", w1));
        this.w2.setText(String.format("%.2f", w2));
        this.w3.setText(String.format("%.2f", w3));

        double max = Math.max(w1, w2);
        max = Math.max(max, w3);
        int result;
        if (max == w1) result = 1;
        else if (max == w2) result = 2;
        else result = 3;
        answer.setText("Z" + result);
    }

    private void initUI() {
        okButton.addActionListener(this);

        r1.setModel(new SpinnerNumberModel(0.0, 0, 1000.0, 0.5));
        r2.setModel(new SpinnerNumberModel(0.0, 0, 1000.0, 0.5));
        r3.setModel(new SpinnerNumberModel(0.0, 0, 1000.0, 0.5));
        r4.setModel(new SpinnerNumberModel(0.0, 0, 1000.0, 0.5));
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
        ExpertWeighting ui = new ExpertWeighting();
        ui.start();
    }
}
