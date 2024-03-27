import javax.swing.*;
import java.awt.*;

class Conditions extends JFrame {
    private JTextField[] textFields;
    private JLabel[] labels;
    private JButton[] buttons;
    public String[] arr = {"chill","freeze","stun","sapped",
            "stalled","sapped","butter","decaypoison","shrinking","terrified","hungered",
            "shrunken","gummed","dazeystunned","stackableslow","hypnotized","potiontoughness1",
            "potiontoughness2","potiontoughness3","speeddown1","speeddown2","speeddown3","potionspeed1",
            "potionspeed2","potionspeed3","potionsuper1","potionsuper2","potionsuper3","unsuspendable",
            "suncarrier50","suncarrier100","suncarrier250"};
    public int[] immunity = new int[arr.length+1];

    public Conditions() {
        int j;
        for (int i = 0; i < arr.length; i++) {
            immunity[i] = 1;
        }
        setResizable(false);
        setLayout(null); // Use a null layout
        setTitle("CONDITIONS");

        textFields = new JTextField[arr.length];
        labels = new JLabel[arr.length];
        buttons = new JButton[arr.length];

        for (int i = 0; i < arr.length; i += 2) {
            j = i * 15;
            labels[i] = new JLabel(arr[i]);
            labels[i].setBounds(10, j, 120, 30); // Set the bounds of the label
            add(labels[i]);

            textFields[i] = new JTextField(10);
            textFields[i].setBounds(125, j, 20, 30); // Set the bounds of the text field
            textFields[i].setText("1");
            add(textFields[i]);

            buttons[i] = new JButton("Set to 0");
            buttons[i].setBounds(150, j, 80, 30); // Set the bounds of the button
            int finalI = i;
            buttons[i].addActionListener(e -> {immunity[finalI] = 0;textFields[finalI].setText("0");});
            add(buttons[i]);

            labels[i+1] = new JLabel(arr[i+1]);
            labels[i+1].setBounds(245, j, 120, 30); // Set the bounds of the label
            add(labels[i+1]);

            textFields[i+1] = new JTextField(10);
            textFields[i+1].setBounds(365, j, 20, 30); // Set the bounds of the text field
            textFields[i+1].setText("1");
            add(textFields[i+1]);


            buttons[i+1] = new JButton("Set to 0");
            buttons[i+1].setBounds(385, j, 80, 30); // Set the bounds of the button
            buttons[i+1].addActionListener(e -> {immunity[finalI+1] = 0;textFields[finalI+1].setText("0");});
            add(buttons[i+1]);
        }

        JButton button1 = new JButton("Apply");
        button1.setBounds(155, arr.length * 15 + 4, 155, 30); // Set the bounds of the button
        button1.addActionListener(e -> modifyConditions());
        add(button1);

        JButton button2 = new JButton("All 0");
        button2.setBounds(0, arr.length * 15 + 4, 155, 30); // Set the bounds of the button
        button2.addActionListener(e -> all0());
        add(button2);

        JButton button3 = new JButton("All 1");
        button3.setBounds(155 *2, arr.length * 15 + 4, 155, 30); // Set the bounds of the button
        button3.addActionListener(e -> all1());
        add(button3);
    }

    public void all0(){
        for (int i = 0; i < arr.length; i++) {
            textFields[i].setText("0");
            immunity[i] = 0;
        }
    }

    public void all1(){
        for (int i = 0; i < arr.length; i++) {
            textFields[i].setText("1");
            immunity[i] = 1;
        }
    }
    public int getLength(){return immunity.length;}

    public void modifyConditions() {
        for (int i = 0; i < arr.length; i++) {
            immunity[i] = Integer.parseInt(textFields[i].getText());
        }
    }
}
