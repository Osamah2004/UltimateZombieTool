import javax.swing.*;
import java.awt.*;

public class TextField {
    private JTextField textField;
    private JLabel label;
    private JLabel defaultTextLabel;

    public TextField(String labelText, int x, int y, String defaultText) {
        label = new JLabel(labelText);
        label.setBounds(x, y, 1000, 30);

        textField = new JTextField();
        textField.setBounds(x + 70, y, 100, 30);

        defaultTextLabel = new JLabel(defaultText);
        defaultTextLabel.setBounds(x, y + 20, 200, 30);
        defaultTextLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Font size changed to 8
    }

    public JTextField getTextField() {
        return this.textField;
    }
    public Double getNum() throws NumberFormatException {
        return Double.parseDouble(this.textField.getText());
    }
    public String getStr() throws NumberFormatException {
        return this.textField.getText();
    }

    public void setTxt(String txt){
        textField.setText(txt);
    }

    public Component getLabel() {
        return this.label;
    }

    public JLabel getDefaultTextLabel() {
        return this.defaultTextLabel;
    }
}
