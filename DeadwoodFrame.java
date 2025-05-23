import javax.swing.*;

public class DeadwoodFrame extends JFrame {
    private final GameController model;
    private BoardPanel boardPanel;
    private PlayerPanel playerPanel;

    public DeadwoodFrame(GameController model) {
        this.model = model;
        setTitle("Deadwood (Swing)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel = new BoardPanel(model);
        playerPanel = new PlayerPanel(model);

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
