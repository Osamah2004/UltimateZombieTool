import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class popanim extends JFrame{

    private static String variant;

    public popanim(String variant){
        this.variant = variant;
        setSize(665, 345);
        switch (variant){
            case "basics": setTitle("POPANIMS");break;
            case "dino": setTitle("This is the only unique zombie in JM lol");break;
            default: setTitle("SPECIAL ZOMBIES");break;
        }
        setLayout(null);  // Set layout to null
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add photos to the frame
        for (int i = 0; i <= 11; i++) {
            int x = (i%2 == 0) ? i * 55 : i * 55 - 55;
            int y = (i%2 == 0) ? 0 : 160;
            addPhoto(this, "/" + variant + "/" + i + ".png", i, x, y);
        }
    }

    public void addPhoto(JFrame frame, String imagePath, int imageNumber, int x, int y) {
        try {
            BufferedImage myPicture = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream(imagePath)));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setBounds(x, y, myPicture.getWidth(), myPicture.getHeight());
            ImageIcon image = new ImageIcon(myPicture);
            JButton button = new JButton(image);
            button.setBounds(x, y, image.getIconWidth(), image.getIconHeight());  // Set the location and size of the button
            button.addActionListener(e -> {
                if (Objects.equals(variant, "basics")) {Main.setArmor(imageNumber);}
                else {
                    Main.zombieNum = imageNumber;
                    Main.changeTexture("/"+Main.getZombieVariant()+"/"+imageNumber+".png");
                    new specialZombies(Main.getZombieVariant() + imageNumber + "_prop");
                }
            });
            // Add the button to the frame
            frame.getContentPane().add(button);
        } catch (NullPointerException ignored) {}
        catch (IOException e) {
            System.out.println("Error reading image: " + e.getMessage());
        }
    }



}
