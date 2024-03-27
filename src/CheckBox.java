import javax.swing.*;

public class CheckBox {
    private JCheckBox checkBox;
    private JLabel label;

    public CheckBox(String labelText, int x, int y) {
        label = new JLabel(labelText);
        label.setBounds(x, y, labelText.length() * 7, 30); // 7 is an approximate width of a character

        checkBox = new JCheckBox();
        checkBox.setBounds(x + labelText.length() * 7 + 10, y, 20, 30); // 10 pixels gap
    }

    public JCheckBox getCheckBox() {
        return this.checkBox;
    }
    public void setState(boolean state){this.checkBox.setSelected(state);}
    public boolean getState(){return this.checkBox.isSelected();}

    public JLabel getLabel() {
        return this.label;
    }
}
