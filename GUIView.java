import java.awt.*;
import javax.swing.*;

public class GUIView implements View {
    private GameController gameController;
    private LocationManager locationManager;
    private DeadwoodFrame frame;
    private Role selectedRole;

    public GUIView(GameController moderator, LocationManager lm) {
        this.gameController = moderator;
        this.locationManager = lm;
        SwingUtilities.invokeLater(() -> {
            frame = new DeadwoodFrame(gameController);
            gameController.setView(this);
            displayStartScreen();
        });
    }

    private void displayStartScreen() {
        promptPlayerCount();
    }

    @Override
    public void displayStartingStats(Player player) {
        String message = String.format(
            "Starting stats - Rank: %d, Credits: %d, Dollars: $%d",
            player.getRank(), player.getCredits(), player.getDollars()
        );
        displayMessage(message);
    }

    @Override
    public void displayPlayerInfo(Player player) {
        SwingUtilities.invokeLater(() -> {
            frame.getPlayerPanel().updatePlayerInfo(player);
        });
    }

    @Override
    public void displayPlayerLocation(Player player) {
        Set location = locationManager.getSet(player.getLocation());
        String locationInfo = player.getLocation();
        
        if (location.isSet() && location.getSceneCard() != null) {
            locationInfo += location.getSceneCard().getFlipped() ? 
                " - Shooting: " + location.getSceneCard().getName() : 
                " - Scene wrapped";
        }
        
        SwingUtilities.invokeLater(() -> {
            frame.getPlayerPanel().updatePlayerInfo(gameController.getActivePlayer());
            frame.getBoardPanel().highlightPlayer(player);
        });
    }

    @Override
    public void displayActResult(Player player, boolean success, boolean onCard) {
        String result = success ? 
            (onCard ? "Success! Earned 2 credits" : "Success! Earned 1 credit and $1") :
            (onCard ? "Acting failed" : "Acting failed but earned $1");
        displayMessage(result);
    }

    @Override
    public void promptPlayerCount() {
        Integer[] options = {2, 3, 4, 5, 6, 7, 8};
        Integer choice = (Integer)JOptionPane.showInputDialog(
            frame,
            "Select number of players:",
            "Player Count",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (choice != null) {
            gameController.initializePlayers(choice);
            Player firstPlayer = gameController.getActivePlayer();
            updatePlayerPanel(firstPlayer);
            frame.getBoardPanel().repaint();
            startGameMessage();
        } else {
            promptPlayerCount();
        }
    }

    @Override
    public void startGameMessage() {
        displayMessage("=== GAME STARTED ===");
        displayCurrentPlayer(gameController.getActivePlayer());
    }

    @Override
    public void endGameMessage() {
        displayMessage("=== GAME OVER ===");
    }

    @Override
    public void promptAction() {
        // Handled by buttons
    }

    @Override
    public void displayHelp() {
        String helpText = "Available Commands:\n" +
            "- Move: Click move button then select destination\n" +
            "- Act: Perform your role\n" +
            "- Rehearse: Practice for your role\n" +
            "- Take Role: Select from available roles\n" +
            "- Upgrade: Increase your rank in casting office";
        
        JOptionPane.showMessageDialog(frame, helpText, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            frame.getMessageArea().append(message + "\n");
            frame.getMessageArea().setCaretPosition(frame.getMessageArea().getDocument().getLength());
        });
    }

    @Override
    public Role chooseFromAvailableRoles(Player player) {
        Set location = locationManager.getSet(player.getLocation());
        // Array to hold the selected role (workaround for final requirement)
        final Role[] selectedRole = {null};
    
        //Creates a dialog with role selection options
        JDialog roleDialog = new JDialog(frame, "Choose a Role", true);
        roleDialog.setLayout(new BorderLayout());
    
        JPanel rolePanel = new JPanel(new GridLayout(0, 1));
        rolePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        //Adds off-card roles
        location.getOffRoles().values().stream()
            .filter(Role::isAvailable)
            .forEach(role -> addRoleButton(rolePanel, role, selectedRole, roleDialog));
    
        //Adds on-card roles if scene exists and isn't wrapped
        if (location.getSceneCard() != null && !location.getSceneCard().getFlipped()) {
            location.getSceneCard().getRoleList().values().stream()
                .filter(Role::isAvailable)
                .forEach(role -> addRoleButton(rolePanel, role, selectedRole, roleDialog));
        }
    
        JScrollPane scrollPane = new JScrollPane(rolePanel);
        scrollPane.setPreferredSize(new Dimension(350, 250));
    
        //Adds cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> roleDialog.dispose());
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
    
        roleDialog.add(scrollPane, BorderLayout.CENTER);
        roleDialog.add(buttonPanel, BorderLayout.SOUTH);
        roleDialog.pack();
        roleDialog.setLocationRelativeTo(frame);
        roleDialog.setVisible(true); // This will block until dialog is closed
    
        return selectedRole[0];
    }

    private void addRoleButton(JPanel panel, Role role, Role[] selectedRole, JDialog dialog) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
    
        // Main role info
        JLabel roleLabel = new JLabel(
            String.format("<html><b>%s</b><br>Rank: %d<br>Line: %s</html>",
                role.getName(),
                role.getRankRequired(),
                role.getLine()));
        roleLabel.setHorizontalAlignment(SwingConstants.LEFT);
    
        //Highlight if player qualifies
        if (gameController.getActivePlayer().getRank() >= role.getRankRequired()) {
            button.setBackground(new Color(220, 255, 220));
        } else {
            button.setBackground(new Color(255, 220, 220));
            roleLabel.setText(roleLabel.getText() + "<br><font color=red>Rank too low!</font>");
        }
        button.add(roleLabel, BorderLayout.CENTER);
        button.addActionListener(e -> {
        if (gameController.getActivePlayer().getRank() >= role.getRankRequired()) {
            //Stores the selected role
            selectedRole[0] = role;
            //Closes the dialog
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(dialog, 
                "You need rank " + role.getRankRequired() + " for this role!", 
                "Rank Too Low", 
                JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(button);
    }

    @Override
    public void displayCurrentPlayer(Player player) {
        SwingUtilities.invokeLater(() -> {
            frame.getPlayerPanel().setCurrentPlayer(player.getName());
            displayMessage("\n=== " + player.getName() + "'s turn ===");

        });
    }

    @Override
    public void displayNeighbors(Set set) {
        StringBuilder neighbors = new StringBuilder("Neighboring locations:\n");
        set.getAdjacentSets().forEach(neighbor -> neighbors.append("- ").append(neighbor).append("\n"));
        displayMessage(neighbors.toString());
    }

    @Override
    public void updateBoard(){
        frame.getBoardPanel().repaint();
    }

    @Override
    public void updatePlayerPanel(Player player){
        frame.getPlayerPanel().updatePlayerInfo(player);
    }
}