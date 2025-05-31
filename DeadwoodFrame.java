import javax.swing.*;

public class DeadwoodFrame extends JFrame {
    private final GameController moderator;
    private BoardPanel boardPanel;
    private PlayerPanel playerPanel;

    public DeadwoodFrame(GameController moderator) {
        this.moderator = moderator;
        setTitle("Deadwood (Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel = new BoardPanel(moderator);
        playerPanel = new PlayerPanel(moderator);

        // Main layout
        JSplitPane splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            boardPanel,
            playerPanel
        );
        add(splitPane);

        pack();
        setVisible(true);
    }
}
