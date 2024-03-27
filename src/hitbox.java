import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class hitbox extends JFrame {
    private static JTextField[][] attackRectFields = new JTextField[2][4];
    private static JTextField[][] hitRectFields = new JTextField[2][4];
    private static int[] attackRectDefaults = {95, 20, 15, 0};
    private static int[] hitRectDefaults = {95, 32, 10, 10};
    private static int[] tempAttackRectDefaults;
    private static int[] tempHitRectDefaults;

    public static int[] copyArray(int[] original) {
        int length = original.length;
        int[] copy = new int[length];

        for (int i = 0; i < length; i++) {
            copy[i] = original[i];
        }

        return copy;
    }

    public hitbox() {
        setTitle("HITBOX");
        setLayout(new GridLayout(3, 5)); // 2 rows of data plus header and 4 columns plus label

        // Header
        add(new JLabel("Hitbox"));
        add(new JLabel("mHeight"));
        add(new JLabel("mWidth"));
        add(new JLabel("mX"));
        add(new JLabel("mY"));

        // AttackRect row
        add(new JLabel("AttackRect"));
        for (int i = 0; i < 4; i++) {
            attackRectFields[0][i] = createTextFieldWithPlaceholder(String.valueOf(attackRectDefaults[i]));
            add(attackRectFields[0][i]);
        }

        // HitRect row
        add(new JLabel("HitRect"));
        for (int i = 0; i < 4; i++) {
            hitRectFields[0][i] = createTextFieldWithPlaceholder(String.valueOf(hitRectDefaults[i]));
            add(hitRectFields[0][i]);
        }
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    private static JTextField createTextFieldWithPlaceholder(String placeholderText) {
        JTextField textField = new JTextField();
        textField.setText(placeholderText);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholderText);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }
    public static void updateTextField(int[] attackRectValues, int[] hitRectValues) {
        for (int i = 0; i < 4; i++) {
            attackRectFields[0][i].setText(String.valueOf(attackRectValues[i]));
            hitRectFields[0][i].setText(String.valueOf(hitRectValues[i]));
        }
    }

    public static void clearTextFields() {
        for (int i = 0; i < 4; i++) {
            attackRectFields[0][i].setText(String.valueOf(attackRectDefaults[i]));
            hitRectFields[0][i].setText(String.valueOf(hitRectDefaults[i]));
        }
    }


    public int[] getAttackRectValues() {
        return getValuesFromFields(attackRectFields, attackRectDefaults);
    }

    public int[] getHitRectValues() {
        return getValuesFromFields(hitRectFields, hitRectDefaults);
    }

    private int[] getValuesFromFields(JTextField[][] fields, int[] defaults) {
        int[] values = new int[fields[0].length];
        for (int i = 0; i < fields[0].length; i++) {
            String text = fields[0][i].getText();
            values[i] = text.isEmpty() || text.equals(String.valueOf(defaults[i])) ? defaults[i] : Integer.parseInt(text);
        }
        return values;
    }

}
