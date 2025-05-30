import javax.swing.*;

public class PlayerPanel extends JPanel {
    private JLabel dollarsLabel, creditsLabel, rankLabel;

    public PlayerPanel(GUIController model) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        dollarsLabel = new JLabel("$0");
        creditsLabel = new JLabel("0 credits");
        rankLabel = new JLabel("Rank 1");
        
        add(new JLabel("Player Stats"));
        add(dollarsLabel);
        add(creditsLabel);
        add(rankLabel);
        
        // Update when model changes
        //model.addPropertyChangeListener(e -> updateStats());
    }
}
