import java.awt.*;
import javax.swing.*;

public class DeadwoodFrame extends JFrame {
    private GameController gameController;
    private BoardPanel boardPanel;
    private PlayerPanel playerPanel;
    private JTextArea messageArea;
    private JScrollPane messageScrollPane;

    public DeadwoodFrame(GameController controller) {
        this.gameController = controller;
        setTitle("Deadwood");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Initialize components with basic styling
        boardPanel = new BoardPanel(gameController);
        playerPanel = new PlayerPanel();
        messageArea = new JTextArea(10, 40);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        
        // Simple border for message area
        messageScrollPane = new JScrollPane(messageArea);
        messageScrollPane.setBorder(BorderFactory.createTitledBorder("Game Log"));
        
        // Create button panel with basic styling
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] buttonLabels = {"Act", "Rehearse", "Move", "Take Role", "End Turn", "Help", "endgame"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.addActionListener(e -> handleButtonClick(label));
            buttonPanel.add(button);
        }

        JButton upgradeButton = new JButton("Upgrade");
        upgradeButton.addActionListener(e -> {
        String rankStr = JOptionPane.showInputDialog("Enter new rank (2-6):");
        if (rankStr == null) return;


        String[] options = {"dollar", "credit"};
        String currency = (String) JOptionPane.showInputDialog(
        null,
        "Select currency:",
        "Currency Type",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]
        );
        if (currency == null) return;


        controller.processAction("upgrade " + rankStr + " " + currency);
        });

        buttonPanel.add(upgradeButton);


        // Main layout
        add(new JScrollPane(boardPanel), BorderLayout.CENTER);
        add(playerPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.EAST);
        add(messageScrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleButtonClick(String action) {
        switch (action) {
            case "Act":
                gameController.processAction("act");
                break;
            case "Rehearse":
                gameController.processAction("rehearse");
                break;
            case "Move":
                showSimpleMoveDialog();
                break;
            case "Take Role":
                gameController.processAction("take role");
                break;
            case "Upgrade":
                showSimpleUpgradeDialog();
                break;
            case "End Turn":
                gameController.processAction("end");
                break;
            case "Help":
                gameController.getView().displayHelp();
                break;
            case "endgame":
                gameController.processAction("endgame");
        }
    }

    private void showSimpleMoveDialog() {
        Player player = gameController.getActivePlayer();
        Set currentSet = gameController.getLocationManager().getSet(player.getLocation());
        
        String[] options = currentSet.getAdjacentSets().toArray(new String[0]);
        String destination = (String) JOptionPane.showInputDialog(
            this,
            "Where would you like to move?",
            "Move",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (destination != null) {
            gameController.processAction("move " + destination);
        }
    }

    private void showSimpleUpgradeDialog() {
        Player player = gameController.getActivePlayer();
        String rank = JOptionPane.showInputDialog(this, "Enter desired rank (2-6):");
        if (rank == null) return;
        
        String[] options = {"dollar", "credit"};
        String currency = (String) JOptionPane.showInputDialog(
            this,
            "Select currency type:",
            "Upgrade",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (currency != null) {
            gameController.processAction("upgrade " + rank + " " + currency);
        }
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