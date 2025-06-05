import java.awt.*;
import javax.swing.*;

public class DeadwoodFrame extends JFrame {
    private GameController gameController;
    private BoardPanel boardPanel;
    private PlayerPanel playerPanel;
    private JTextArea messageArea;

    public DeadwoodFrame(GameController controller) {
        this.gameController = controller;
        setTitle("Deadwood");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        boardPanel = new BoardPanel(gameController);
        playerPanel = new PlayerPanel();
        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);

        // Wrap boardPanel in scroll pane if large
        JScrollPane boardScrollPane = new JScrollPane(boardPanel);
        boardScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        boardScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Combine player panel and buttons in side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout());

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5)); // vertical spacing

        JButton actButton = new JButton("Act");
        actButton.addActionListener(e -> gameController.handleActButton());

        JButton rehearseButton = new JButton("Rehearse");
        rehearseButton.addActionListener(e -> gameController.handleRehearseButton());

        JButton moveButton = new JButton("Move");
        moveButton.addActionListener(e -> showMoveDialog());

        JButton takeRoleButton = new JButton("Take Role");
        takeRoleButton.addActionListener(e -> controller.handleTakeRoleButton());

        JButton endButton = new JButton("End Turn");
        endButton.addActionListener(e -> gameController.processAction("end"));

        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(e -> gameController.getView().displayHelp());

        // Add buttons
        buttonPanel.add(actButton);
        buttonPanel.add(rehearseButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(takeRoleButton);
        buttonPanel.add(endButton);
        buttonPanel.add(helpButton);

        // Nest player info and buttons vertically
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(playerPanel);
        eastPanel.add(Box.createVerticalStrut(10));
        eastPanel.add(buttonPanel);

        // Message area at bottom
        JScrollPane messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add everything to the frame
        add(boardScrollPane, BorderLayout.CENTER);
        playerPanel.setPreferredSize(new Dimension(250, boardPanel.getHeight()));
        add(playerPanel, BorderLayout.WEST);
        add(eastPanel, BorderLayout.EAST);
        messageScrollPane.setPreferredSize(new Dimension(400, 100));
        add(messageScrollPane, BorderLayout.SOUTH);

        pack(); // Resize window to fit contents
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Optional: maximize window
        setVisible(true);
    }

    private void showMoveDialog() {
        Player player = gameController.getActivePlayer();
        Set currentSet = gameController.getLocationManager().getSet(player.getLocation());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        for (String neighbor : currentSet.getAdjacentSets()) {
            JButton locationButton = new JButton(neighbor);
            locationButton.addActionListener(e -> {
                gameController.processAction("move " + neighbor);
                ((Window) SwingUtilities.getRoot(locationButton)).dispose();
            });
            panel.add(locationButton);
        }

        JOptionPane.showOptionDialog(
                this,
                panel,
                "Select Move Destination",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{},
                null
        );
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    
    
}
