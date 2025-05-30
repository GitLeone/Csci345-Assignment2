import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private final GUIController model;
    private JLabel boardLabel;
    
    public BoardPanel(GUIController model) {
        this.model = model;
        setLayout(null);
        ImageIcon board = new ImageIcon("images/board.jpg");
        boardLabel = new JLabel(board);
        boardLabel.setBounds(0, 0, board.getIconWidth(), board.getIconHeight());

        add(boardLabel);
        setPreferredSize(new Dimension(board.getIconWidth(), board.getIconHeight()));
    }
}
