import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PairwiseComparison extends JFrame implements ActionListener {
    private JFrame jFrame;
    private JPanel mainPanel;
    private JRadioButton z1z2;
    private JRadioButton z1z3;
    private JRadioButton z2z3;
    private JRadioButton z2z1;
    private JRadioButton z3z1;
    private JRadioButton z3z2;
    private JButton okButton;
    private JLabel c1;
    private JLabel c2;
    private JLabel c3;
    private JLabel w1;
    private JLabel w2;
    private JLabel w3;
    private JLabel answer;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int c1_sum = 0;
        if (z1z2.isSelected()) c1_sum++;
        if (z1z3.isSelected()) c1_sum++;
        c1.setText(String.valueOf(c1_sum));
        int c2_sum = 0;
        if (z2z1.isSelected()) c2_sum++;
        if (z2z3.isSelected()) c2_sum++;
        c2.setText(String.valueOf(c2_sum));
        int c3_sum = 0;
        if (z3z2.isSelected()) c3_sum++;
        if (z3z1.isSelected()) c3_sum++;
        c3.setText(String.valueOf(c3_sum));

        int sum = c1_sum + c2_sum + c3_sum;
        float w1 = (float) c1_sum/sum;
        float w2 = (float) c2_sum/sum;
        float w3 = (float) c3_sum/sum;
        this.w1.setText(String.format("%.2f", w1));
        this.w2.setText(String.format("%.2f", w2));
        this.w3.setText(String.format("%.2f", w3));

        float max = Math.max(w1, w2);
        max = Math.max(max, w3);
        int result;
        if (max == w1) result = 1;
        else if (max == w2) result = 2;
        else result = 3;
        answer.setText("Z" + result);
}

    public void initUI() {
        ButtonGroup group12 = new ButtonGroup();
        group12.add(z1z2);
        group12.add(z2z1);
        z1z2.setSelected(true);
        ButtonGroup group13 = new ButtonGroup();
        group13.add(z1z3);
        group13.add(z3z1);
        z1z3.setSelected(true);
        ButtonGroup group23 = new ButtonGroup();
        group23.add(z2z3);
        group23.add(z3z2);
        z2z3.setSelected(true);

        okButton.addActionListener(this);
    }

    public void start() {
        jFrame = new JFrame("KR2");
        jFrame.setContentPane(this.mainPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        initUI();

    }
    public static void main(String[] args) {
        PairwiseComparison ui = new PairwiseComparison();
        ui.start();
    }
}
