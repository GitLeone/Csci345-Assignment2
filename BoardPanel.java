import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private final GameController moderator;
    private JLabel boardLabel;
    
    public BoardPanel(GameController moderator) {
        this.moderator = moderator;
        setLayout(null);
        ImageIcon board = new ImageIcon("images/board.jpg");
        boardLabel = new JLabel(board);
        boardLabel.setBounds(0, 0, board.getIconWidth(), board.getIconHeight());

        add(boardLabel);
        setPreferredSize(new Dimension(board.getIconWidth(), board.getIconHeight()));
    }
}
