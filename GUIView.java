import java.awt.*;
import javax.swing.*;

public class GUIView implements View {
    private GameController gameController;
    private LocationManager locationManager;
    private DeadwoodFrame frame;

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
        //Here we can prompt each player to choose their color and 
        if (choice != null) {
            gameController.initializePlayers(choice);
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
        JPanel rolePanel = new JPanel(new GridLayout(0, 1));
        
        // Add off-card roles
        location.getOffRoles().values().stream()
            .filter(Role::isAvailable)
            .forEach(role -> addRoleButton(rolePanel, role));
        
        // Add on-card roles
        if (location.getSceneCard() != null) {
            location.getSceneCard().getRoleList().values().stream()
                .filter(Role::isAvailable)
                .forEach(role -> addRoleButton(rolePanel, role));
        }

        JScrollPane scrollPane = new JScrollPane(rolePanel);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        JOptionPane.showMessageDialog(frame, scrollPane, "Choose a Role", JOptionPane.PLAIN_MESSAGE);
        return null;
    }

    private void addRoleButton(JPanel panel, Role role) {
        JButton button = new JButton(String.format("%s (Rank %d)", role.getName(), role.getRankRequired()));
        button.addActionListener(e -> {
            gameController.processAction("take role " + role.getName());
            ((Window)SwingUtilities.getRoot(button)).dispose();
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
}