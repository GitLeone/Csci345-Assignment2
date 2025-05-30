import javax.swing.*;
import java.awt.*;

public class ImageTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Adjust this path to your image file relative to your project root
        String imagePath = "images/board.jpg";

        ImageIcon icon = new ImageIcon(imagePath);
        JLabel label = new JLabel(icon);

        frame.add(label);
        frame.setVisible(true);
    }
}
