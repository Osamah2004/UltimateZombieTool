import javax.swing.*;
import org.json.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class specialZombies {
    private static JSONObject prop;

    private static final JComboBox<String> box = new JComboBox<>(new String[] {"jam_punk","jam_rap","jam_pop",
            "jam_ballad","jam_8bit","jam_metal"});
    private static final List<JTextField> textFields = new ArrayList<>();
    private static final List<JCheckBox> checkBoxes = new ArrayList<>();
    private static final List<String> keys = new ArrayList<>();
    private static final jsonData data = new jsonData();
    private static JFrame frame;
    private static JFrame booleanFrame;
    private int[] AttackRect = new int[4];
    private int[] HitRect = new int[4];
    private boolean rectModified = false;
    public void displayJson(JSONObject json) {
        keys.clear();
        checkBoxes.clear();
        textFields.clear();

        Main.setSpeed("");
        Main.setHP();

        boolean hasObjclass = false;
        boolean hasBool = false;

        frame = new JFrame("INTEGERS EDITOR");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setMinimumSize(new Dimension(250, 50));
        frame.setResizable(false);

        booleanFrame = new JFrame("BOOLEAN EDITOR");
        booleanFrame.setLayout(new BoxLayout(booleanFrame.getContentPane(), BoxLayout.Y_AXIS));
        booleanFrame.setMinimumSize(new Dimension(250, 50));
        booleanFrame.setResizable(false);

        for(String key : json.keySet()) {
            Object value = json.get(key);
            JLabel label = new JLabel(key+":");
            keys.add(key);
            switch (key) {
                case "objclass" -> {
                    Main.objclass = json.getString("objclass");
                    hasObjclass = true;
                }
                case "Hitpoints" -> Main.setHP(json.getInt("Hitpoints"));
                case "Speed" -> Main.setSpeed(String.valueOf(json.getDouble("Speed")));
                case "AttackRect", "HitRect" -> {
                    JSONObject rect = json.getJSONObject(key);
                    int[] array = key.equals("AttackRect") ? AttackRect : HitRect;
                    array[0] = rect.getInt("mHeight");
                    array[1] = rect.getInt("mWidth");
                    array[2] = rect.getInt("mX");
                    array[3] = rect.getInt("mY");
                    rectModified = true;
                }
                case "JamStyle" ->{
                    frame.add(label);
                    frame.add(box);
                }
                case "Actions" -> {
                    JSONArray a;
                    a = prop.getJSONArray("Actions");
                    String actionName = a.getString(0).substring(5,a.getString(0).indexOf('@'));
                    switch (actionName){
                        case "ZombieEightiesArcadePushAction":
                        case "ZombiePushGridItemAction":
                            continue;
                    }
                    JButton action = new JButton("action");
                    action.addActionListener(e -> displayAction(actionName));
                    frame.add(label);
                    frame.add(action);
                }
                case "ArtCenter" -> {
                    JSONObject artCenter = json.getJSONObject("ArtCenter");
                    Main.setArtCenterY(artCenter.getInt("y"));
                    Main.setArtCenterX(artCenter.getInt("x"));
                }
            }
            if(value instanceof Number) {
                switch (key){
                    case "Hitpoints":
                    case "Speed":
                        continue;
                }
                String textValue = value instanceof Double ? String.format("%.2f", value) :  value.toString();
                JTextField textField = new JTextField(textValue);
                textField.setForeground(Color.GRAY);
                textField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (textField.getText().equals(textValue)) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (textField.getText().isEmpty()) {
                            textField.setText(textValue);
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });
                textFields.add(textField);
                frame.add(label);
                frame.add(textField);
            } else if(value instanceof Boolean) {
                if (key.equals("SkipHeadDropState")){
                    Main.idleZombie();
                }
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((Boolean) value);
                checkBoxes.add(checkBox);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(label, BorderLayout.WEST);
                panel.add(checkBox, BorderLayout.EAST);
                booleanFrame.add(panel);
            }
        }
        if (!hasObjclass){Main.objclass = "";}
        if (rectModified) {
            hitbox.updateTextField(AttackRect, HitRect);
            rectModified = false;
        } else {
            hitbox.clearTextFields();
        }
        json.remove("Hitpoints");
        json.remove("Speed");

        frame.pack();
        frame.setVisible(true);
        if (!checkBoxes.isEmpty()) {
            booleanFrame.pack();
            booleanFrame.setVisible(true);
        }
    }

    private void displayAction(String actionName) {
        if (Main.getZombieVariant().equals("carnie")){
            new magician();
        }
        else new zombieActions(actionName);
    }

    public static void printArrayList(ArrayList<String> arrayList) {
        for (String element : arrayList) {
            System.out.println(element);
        }
    }
    public static void updateProp() {
        int textFieldIndex = 0;
        int checkBoxIndex = 0;
        for (String key : keys) {
            switch (key) {
                case "JamStyle":
                    prop.put(key,box.getSelectedItem());
                case "Speed":
                case "Hitpoints":
                    continue;
            }
            Object value = prop.get(key);
            if (value instanceof Number) {
                String text = textFields.get(textFieldIndex).getText();
                if (text.isEmpty()) {
                    prop.put(key, value); // Reset to original value
                } else {
                    // Update prop with new value
                    prop.put(key, text);
                }
                textFieldIndex++;
            } else if (value instanceof Boolean) {
                // Update prop with new value
                prop.put(key, checkBoxes.get(checkBoxIndex).isSelected());
                checkBoxIndex++;
            }
        }
    }


    public specialZombies(String zombie){
        if(frame != null && frame.isVisible()) {
            frame.dispose();
        }
        if(booleanFrame != null) {
            booleanFrame.dispose();
        }
        JSONObject prop = new JSONObject(data.getAnim());
        specialZombies.prop = prop.getJSONObject(zombie);
        displayJson(specialZombies.prop);
    }

    public static JSONObject getProp() {
        return prop;
    }
}
