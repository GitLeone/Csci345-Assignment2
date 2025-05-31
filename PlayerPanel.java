import javax.swing.*;

public class PlayerPanel extends JPanel {
    private JLabel nameLabel, rankLabel, creditsLabel, dollarsLabel, locationLabel, roleLabel;

    public PlayerPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Player Info"));
        
        nameLabel = new JLabel("Name: ");
        rankLabel = new JLabel("Rank: ");
        creditsLabel = new JLabel("Credits: ");
        dollarsLabel = new JLabel("Dollars: ");
        locationLabel = new JLabel("Location: ");
        roleLabel = new JLabel("Role: None");
        
        add(nameLabel);
        add(rankLabel);
        add(creditsLabel);
        add(dollarsLabel);
        add(locationLabel);
        add(roleLabel);
    }

    public void updatePlayerInfo(Player player) {
        nameLabel.setText("Name: " + player.getName());
        rankLabel.setText("Rank: " + player.getRank());
        creditsLabel.setText("Credits: " + player.getCredits());
        dollarsLabel.setText("Dollars: " + player.getDollars());
        locationLabel.setText("Location: " + player.getLocation());
        roleLabel.setText("Role: " + (player.getRole() != null ? player.getRole().getName() : "None"));
    }
    
    public void setCurrentPlayer(String name) {
        setBorder(BorderFactory.createTitledBorder(name + " (Current)"));
    }
}