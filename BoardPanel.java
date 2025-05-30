import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private final GUIController model;
    
    public BoardPanel(GUIController model) {
        this.model = model;
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
    }
}
