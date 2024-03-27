import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Main {
    //main class
    public static String objclass;

    private static final String[] armors = new String[]{"Helmetless","Cone","Bucket","",""};;
    private static popanim anim = null;
    private static String zombieVariant = "basics";
    private static JComboBox<String> armorBox;
    private static JComboBox<String> world;
    private static JButton zombieTexture;
    private static hitbox rect;
    private final static String[] labels = {"CanSurrender:", "CanBeFlickedOff:", "CanSpawnPlantFood:", "CanTriggerZombieWin:",
            "CanBePlantTossedWeak:", "ChillInsteadOfFreeze:", "FlickIsLaneRestricted:", "CanBePlantTossedStrong:"};
    private static CheckBox[] checkBoxes = new CheckBox[8];
    private static Conditions conditions = new Conditions();
    private static JFrame frame = new JFrame("ULTIMATE CUSTOM ZOMBIE TOOL");
    private static TextField Speed = new TextField("Speed:", 50, 50,"Default Speed: 0.185" );
    private static TextField HP = new TextField("HP:", 50, 100,"Default HP: 190");
    private static TextField DPS = new TextField("Eat DPS:", 50, 150,"Default Eat DPS: 100");
    private static TextField Alias = new TextField("Props:",250,50,"\"Properties\": \"RTID(CustomZombie@.)\",");
    private static TextField CodeName = new TextField("CodeName:", 250, 100,"Default: custom");
    private static TextField fire = new TextField("Fire:", 250, 150,"FireDamageMultiplier: 1");
    private static TextField artScale = new TextField("ArtScale:", 50, 250,"Default ArtScale: 1");

    private static TextField ArtCenterX = new TextField("Artcenter X:", 50, 200,"Default: 90");
    private static TextField ArtCenterY = new TextField("Artcenter Y:", 250, 200,"Default: 125");
    private static zombieProps props = new zombieProps();
    private static zombieTypes types = new zombieTypes();
    public static int zombieNum = 0;

    public static boolean isFieldEmpty(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public static void setArmor(int value){
        types.setResources(value);
        armors[4] = "";
        armors[0] = "Helmetless";
        changeTexture("/basics/"+value +".png");
        types.setPopanim(value);
        switch (value){
            case 3: armors[0] = "CowboyHat";armors[3] = "Brick";break;
            case 5: armors[3] = "Helmet";break;
            case 7: armors[3] = "IceBlock";break;
            case 10: armors[3] = "Skull";armors[4] = "Brick";break;
            case 6:
            case 8:
            case 9: armors[3] = "";break;
            default: armors[3] = "Brick";break;
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
                Arrays.stream(armors)
                        .filter(item -> item != null && !item.trim().isEmpty())
                        .toArray(String[]::new)
        );
        armorBox.setModel(model);
    }

    public static void setAlias(String alias){
        props.setAlias(alias);
        types.setProps(alias);
    }

    public static void conditionsWindow() {
        conditions.setSize(479, 550);
        conditions.setVisible(true);
        /*hitbox.clearTextFields();*/
    }

    public static void generate(int factor) {
        if (!(Objects.equals(zombieVariant, "basics"))){
            props.setObjclass(objclass);
            specialZombies.updateProp();
            zombieTypes.setObjdata(zombieVariant+zombieNum);
            zombieProps.add2Objdata(specialZombies.getProp());
        }

        props.setHP(getField(HP, 190));
        props.setSpeed(getField(Speed, 0.185));
        props.setDPS(getField(DPS, 100));
        props.setArtCenter(getField(ArtCenterX,90),getField(ArtCenterY,125));
        props.setHitBox(rect.getAttackRectValues(), rect.getHitRectValues());
        zombieProps.addObject("FireDamageMultiplier",getField(fire,1));
        zombieProps.addObject("ArtScale",getField(artScale,1));
        types.codeName(getField(CodeName,"custom"));
        setAlias(getField(Alias,"CustomZombie"));

        String armor = (String) armorBox.getSelectedItem();

        if (!Objects.equals(armor, "Helmetless")){
            if (Objects.equals(armor, "Helmet")){
                props.addObject("ZombieArmorProps",new String[] {"RTID(ShoulderArmorDefault@ArmorTypes)","RTID(CrownDefault@ArmorTypes)"});
            }
            else props.addObject("ZombieArmorProps",new String[] {"RTID(" + armor + "Default@ArmorTypes)"});
        }

        for (int i = 0; i < 8; i++) {
            props.setBool(labels[i].substring(0,labels[i].length()-1),checkBoxes[i].getState());
        }

        for (int i = 0; i < conditions.getLength()-1; i++) {
            props.setCondition(conditions.arr[i],conditions.immunity[i]);
        }
        zombieActions action = new zombieActions();

        String zombieAction = action.toString(factor);
        String zombieType = types.toString(factor);
        String zombieProp = props.toString(factor);
        String all = zombieType+zombieProp+zombieAction;

        StringSelection selection = new StringSelection(all);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        zombieProps.clearObject();
        new zombieProps();
    }

    public static double getField(TextField field, double defaultValue) {
        if (isFieldEmpty(field.getTextField())) {
            return defaultValue;
        } else {
            return field.getNum();
        }
    }
    public static String getField(TextField field, String defaultValue) {
        if (isFieldEmpty(field.getTextField())) {
            return defaultValue;
        } else {
            return field.getStr();
        }
    }
    public static void addToFrame(JFrame frame, TextField textField) {
        frame.add(textField.getTextField());
        frame.add(textField.getLabel());
        if (textField.getDefaultTextLabel() != null) {
            frame.add(textField.getDefaultTextLabel());
        }
    }

    public static void addBox(CheckBox box){
        box.setState(true);
        frame.add(box.getCheckBox());
        frame.add(box.getLabel());
    }

    public static void addBox(CheckBox box,int i){
        box.setState(false);
        frame.add(box.getCheckBox());
        frame.add(box.getLabel());
    }

    public static void idleZombie(){
        if (!(zombieVariant.equals("modern"))&&!(zombieVariant.equals("carnie"))){
            checkBoxes[0].setState(true);
            checkBoxes[1].setState(false);
            checkBoxes[4].setState(false);
            checkBoxes[6].setState(true);
            checkBoxes[7].setState(false);
        }
    }

    public static String getZombieVariant() {
        return zombieVariant;
    }

    public static void main(String[] args) {
        rect = new hitbox();
        frame.setSize(900, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addToFrame(frame, Speed);
        addToFrame(frame, HP);
        addToFrame(frame, DPS);
        addToFrame(frame, CodeName);
        addToFrame(frame, fire);
        addToFrame(frame, artScale);
        addToFrame(frame, Alias);
        addToFrame(frame, ArtCenterX);
        addToFrame(frame, ArtCenterY);

        Alias.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            public void updateLabel() {
                String defaultText;
                String text = Alias.getTextField().getText();
                if (isFieldEmpty(Alias.getTextField())) {defaultText = "\"Properties\": \"RTID(CustomZombie@.)\",";}
                else defaultText = "\"Properties\": \"RTID(" + text + "@.)\",";
                Alias.getDefaultTextLabel().setText(defaultText);
            }
        });



        for (int i = 0; i < labels.length; i++) {
            checkBoxes[i] = new CheckBox(labels[i], 500, 50 + i * 25);
            if (i == 0||i == 5 || i == 6){addBox(checkBoxes[i],1);}
            else addBox(checkBoxes[i]);
        }

        //addPhoto(frame,".\\resources\\0.png",750,50);
        addImageToFrame("/basics/0.png",750,50);

        JButton displayConditions = new JButton("Conditions");
        displayConditions.setBounds(250, 250, 170, 30);
        displayConditions.addActionListener(e -> conditionsWindow());

        JButton copyCompressed = new JButton("Copy compressed code");
        copyCompressed.setBounds(425, 375, 175, 30);
        copyCompressed.addActionListener(e -> generate(0));

        JButton copyCode = new JButton("Copy code");
        copyCode.setBounds(325, 375, 100, 30);
        copyCode.addActionListener(e -> generate(2));

        frame.add(displayConditions);
        frame.add(copyCompressed);
        frame.add(copyCode);

        armorBox = addComboBox(750,200, new String[]{"Helmetless","Cone", "Bucket","Brick"});
        world = addComboBox(650,50, new String[]{"basics","egypt","pirate","cowboy","future",
                "dark","beach","iceage","lostcity","eighties","dino","modern","carnie"});
        world.setVisible(true);
        world.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Call your method here
                armorBox.setVisible(Objects.equals(e.getItem().toString(), "basics"));
                armorBox.setSelectedItem("Helmetless");
                zombieVariant = (e.getItem().toString());
                changeTexture("/"+zombieVariant+"/0.png");
            }
        });

        frame.add(armorBox);
        frame.add(world);
        frame.setVisible(true);

    }
    public static void setHP(int value){HP.setTxt(String.valueOf(value));}
    public static void setHP(){HP.setTxt("");}
    public static void setSpeed(String value){Speed.setTxt(value);}
    public static void setArtCenterX(int value){ArtCenterX.setTxt(String.valueOf(value));}
    public static void setArtCenterY(int value){ArtCenterY.setTxt(String.valueOf(value));}
    /*    public static void addPhoto(JFrame frame, String imagePath, int x, int y) {
            URL url = Main.class.getResource(imagePath);
            if (url != null) {
                try {
                    BufferedImage image = ImageIO.read(url);
                    ImageIcon icon = new ImageIcon(image);
                    zombieTexture = new JButton(icon);
                    zombieTexture.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());  // Set the location and size of the button
                    zombieTexture.addActionListener(e -> new popanim());
                    frame.getContentPane().add(zombieTexture);
                } catch (IOException e) {
                    System.out.println("Error reading image: " + e.getMessage());
                }
            } else {
                System.out.println("Image not found");
            }
        }*/
    public static void changeTexture(String imagePath) {
        try {
            BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream(imagePath)));
            ImageIcon image = new ImageIcon(myPicture);

            // Change the image of zombieTexture
            zombieTexture.setIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static JComboBox addComboBox(int x, int y, String[] items) {
        JComboBox box = new JComboBox<>(items);
        box.setBounds(x, y, 100, 20);  // Set the location and size of the combo box
        return box;
    }

    public static void addImageToFrame(String imagePath, int x, int y) {
        try {
            BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream(imagePath)));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setBounds(x, y, myPicture.getWidth(), myPicture.getHeight());
            ImageIcon image = new ImageIcon(myPicture);

            zombieTexture = new JButton(image);
            zombieTexture.setBounds(x, y, myPicture.getWidth(), myPicture.getHeight());  // Set the location and size of the button
            zombieTexture.addActionListener(e -> {
                if (anim == null || !anim.isVisible()) {
                    anim = new popanim(zombieVariant);
                }
                anim.toFront();
            });

            // Add the JLabel to the JFrame
            frame.getContentPane().add(zombieTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
