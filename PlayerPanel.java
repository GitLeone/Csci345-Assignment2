import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;

public class PlayerPanel extends JPanel {
    private JLabel nameLabel, rankLabel, creditsLabel, dollarsLabel, locationLabel, roleLabel;
    private final Color PANEL_BG = new Color(250, 250, 252);
    private final Color HIGHLIGHT_BG = new Color(255, 248, 220);
    private final Color BORDER_COLOR = new Color(180, 180, 180);
    private final Color TEXT_COLOR = new Color(60, 60, 60);
    private final Color ROLE_COLOR = new Color(0, 120, 86);
    
    public PlayerPanel() {
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
        
        // Main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(12, 16, 12, 16));
        contentPanel.setBackground(PANEL_BG);
        
        // Title label
        JLabel titleLabel = new JLabel("PLAYER DETAILS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(100, 100, 100));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 1, 4, 4));
        infoPanel.setOpaque(false);
        
        nameLabel = createInfoLabel("Name");
        rankLabel = createInfoLabel("Rank");
        creditsLabel = createInfoLabel("Credits");
        dollarsLabel = createInfoLabel("Dollars");
        locationLabel = createInfoLabel("Location");
        roleLabel = createInfoLabel("Role");
        roleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
        
        infoPanel.add(nameLabel);
        infoPanel.add(rankLabel);
        infoPanel.add(creditsLabel);
        infoPanel.add(dollarsLabel);
        infoPanel.add(locationLabel);
        infoPanel.add(roleLabel);
        
        contentPanel.add(titleLabel);
        contentPanel.add(infoPanel);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text + ": ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_COLOR);
        label.setBorder(new EmptyBorder(2, 4, 2, 4));
        return label;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Paint rounded background
        int arc = 12;
        RoundRectangle2D rounded = new RoundRectangle2D.Float(
            0, 0, getWidth(), getHeight(), arc, arc);
        
        g2.setColor(getBackground());
        g2.fill(rounded);
        
        // Paint border
        g2.setColor(BORDER_COLOR);
        g2.setStroke(new BasicStroke(1.2f));
        g2.draw(rounded);
        
        g2.dispose();
    }

    public void updatePlayerInfo(Player player) {
        nameLabel.setText("Name: " + player.getName());
        rankLabel.setText("Rank: " + player.getRank());
        creditsLabel.setText("Credits: " + player.getCredits());
        dollarsLabel.setText("Dollars: " + player.getDollars());
        locationLabel.setText("Location: " + player.getLocation());
        
        if (player.getRole() != null) {
            roleLabel.setText("Role: " + player.getRole().getName());
            roleLabel.setForeground(ROLE_COLOR);
        } else {
            roleLabel.setText("Role: None");
            roleLabel.setForeground(new Color(120, 120, 120));
        }
    }
    
    public void setCurrentPlayer(String name) {
        setBackground(HIGHLIGHT_BG);
        setBorder(new CompoundBorder(
            new LineBorder(new Color(255, 190, 50), 2),
            new EmptyBorder(8, 8, 8, 8)
        ));
    }
}