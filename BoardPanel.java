import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private final GameController model;

    public BoardPanel(GameController model) {
        this.model = model;
        setPreferredSize(new Dimension(800, 600));
    }

   
}
