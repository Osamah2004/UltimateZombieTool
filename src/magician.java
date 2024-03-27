import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

public class magician {
    private static String[] list = new String[3];
    private static int i = 0;

    private static final String[] actions = {
            "ZombieCarnieMagicianTeleportActionOther",
            "ZombieCarnieMagicianTeleportActionSelf",
            "ZombieSpawnActionDefinition",
            "ZombieOctopusProjectileAction",
            "ZombieIceAgeProjectileAction",
            "ZombiePushGridItemAction",
            "ZombieRomanHealerHeal",
            "ZombieDarkWizardZap"
    };
    public static void displayArray(){
        for (int j = 0; j < list.length; j++) {
            System.out.println(list[j]);
        }
    }

    public static void actionManager(String action){
        new zombieActions(action);
        displayArray();
    }

    public static void addAction(JSONObject action,String name){
        list[i] = action.getJSONObject(name) + ",";
        i++;
    }

    public magician() {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setTitle("MAGICIAN ACTIONS");
        frame.setSize(654,268);
        int x = 25;
        int y = 25;
        /*JButton button_ = new JButton("get size");
        button_.setBounds(175,150,250,30);
        button_.addActionListener(e -> System.out.println(frame.getSize()));
        frame.add(button_);*/
        for (int i = 0; i < actions.length; i++) {
            JButton button = new JButton(actions[i]);
            button.setBounds(x,y,280,30);
            int finalI = i;
            button.addActionListener(e -> actionManager(actions[finalI]));
            frame.add(button);
            if (i % 2 != 0) {
                y += 50;
                x = 25;
            } else {
                x += 320;
            }
        }
        frame.setVisible(true);
    }

    public static String getList(int factor){
        JSONObject alias1 = new JSONObject(list[0]);
        JSONObject alias2 = new JSONObject(list[1]);
        JSONObject alias3 = new JSONObject(list[2]);
        JSONArray alias1_ = alias1.getJSONArray("aliases");
        JSONArray alias2_ = alias2.getJSONArray("aliases");
        JSONArray alias3_ = alias3.getJSONArray("aliases");
        zombieProps.addObject("Actions",new String[]{
                "RTID("+alias1_.get(0)+"@.)",
                "RTID("+alias2_.get(0)+"@.)",
                "RTID("+alias3_.get(0)+"@.)"
        });
        return alias1.toString(factor) + "," + alias2.toString(factor) + "," + alias3.toString(factor)+ ",";
    }

}
