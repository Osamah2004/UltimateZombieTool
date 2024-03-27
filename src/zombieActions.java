import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class zombieActions {
    private JFrame frame;
    private static List<FieldInfo> fields = new ArrayList<>();
    private static JSONObject action;
    private static String name;
    private static JTextField alias;

    private static class FieldInfo {
        JTextField textField;
        JSONObject jsonObject;
        String key;

        FieldInfo(JTextField textField, JSONObject jsonObject, String key) {
            this.textField = textField;
            this.jsonObject = jsonObject;
            this.key = key;
        }
    }
    public void setFrame(){
        if(frame != null && frame.isVisible()) {
            frame.dispose();
        }
        assert frame != null;
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setMinimumSize(new Dimension(250, 50));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public zombieActions(){}


    public zombieActions(String actionName){
        name = actionName;
        alias = new JTextField(actionName);
        action = new JSONObject(jsonData.getActions(actionName));
        frame = new JFrame("ACTION EDITOR");  // Create a new JFrame for each instance
        JSONObject action = this.action.getJSONObject(actionName);
        action = action.getJSONObject("objdata");
        JLabel alias = new JLabel("Aliases:");
        frame.add(alias);
        frame.add(zombieActions.alias);
        for(String key : action.keySet()) {
            Object value = action.get(key);
            switch (key){
                case "PushableGridItems":
                case "PushBlockingGridItems":
                case "NotSquashedPlants":
                case "UnhealableZombies":
                case "ZombieTargetExcludeList":
                case "PlantTargetExcludeList":
                    continue;
            }
            if (value instanceof JSONObject){
                JSONObject innerObj = action.getJSONObject(key);

                for (String innerKey : innerObj.keySet()) {
                    JLabel tempLabel = new JLabel(key+innerKey+":");
                    JTextField tempField = new JTextField(String.valueOf(innerObj.get(innerKey)));
                    frame.add(tempLabel);
                    frame.add(tempField);
                    // Store the reference to the JTextField, the JSONObject, and the key
                    fields.add(new FieldInfo(tempField, innerObj, innerKey));
                }
            } else if (value instanceof Integer) {
                JLabel tempLabel = new JLabel(key);
                JTextField tempField = new JTextField(String.valueOf(value));
                frame.add(tempLabel);
                frame.add(tempField);
                // Store the reference to the JTextField, the JSONObject, and the key
            }
        }
        if (Main.getZombieVariant().equals("carnie")){
            JButton button = new JButton("Apply");
            button.addActionListener(e -> magician.addAction(getModifiedAction(),name));
            frame.add(button);
        }
        setFrame();
    }

    public JSONObject getModifiedAction() {
        if (name == null){
            return null;
        }
        JSONObject temp = action.getJSONObject(name);
        temp.put("aliases",new String[] {alias.getText()});
        action.put(name,temp);
        if (action == null) {
            return null;  // or return new JSONObject(); depending on your use case
        }
        for (FieldInfo fieldInfo : fields) {
            try {
                // Get the modified text
                Double modifiedText = Double.valueOf(fieldInfo.textField.getText());
                // Update the corresponding JSONObject
                fieldInfo.jsonObject.put(fieldInfo.key, modifiedText);
            } catch (NumberFormatException e) {
                System.err.println("Could not convert text field value to Double: " + e.getMessage());
            }
        }
        return action;
    }

    public String toString(int factor){
        JSONObject modifiedAction = getModifiedAction();
        if (modifiedAction == null) {
            return "";  // or return ""; depending on your use case
        }
        modifiedAction = modifiedAction.getJSONObject(name);
        if (Main.getZombieVariant().equals("carnie")){return magician.getList(factor);}
        zombieProps.addObject("Actions",new String[] {"RTID("+alias.getText()+"@.)"});
        return modifiedAction.toString(factor) + ",";
    }


}
