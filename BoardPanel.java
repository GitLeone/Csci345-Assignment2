import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private GameController gameController;
    private Image boardImage;
    private Map<String, Point> locationCoordinates;

    public BoardPanel(GameController controller) {
        this.gameController = controller;
        loadBoardImage();
        initializeLocationCoordinates();
        setPreferredSize(new Dimension(boardImage.getWidth(this), boardImage.getHeight(this)));
    }

    private void loadBoardImage() {
        ImageIcon icon = new ImageIcon("board.jpg");
        boardImage = icon.getImage();
    }

    private void initializeLocationCoordinates() {
        locationCoordinates = new HashMap<>();
        // Initialize with coordinates from your XML or hardcoded
        locationCoordinates.put("trailer", new Point(100, 100));
        locationCoordinates.put("office", new Point(200, 200));
        // Add all other locations...
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, this);
        
        // Draw players
        for (Player player : gameController.getPlayers()) {
            Point pos = locationCoordinates.get(player.getLocation());
            if (pos != null) {
                g.setColor(Color.RED);
                g.fillOval(pos.x, pos.y, 20, 20);
                g.setColor(Color.BLACK);
                g.drawString(player.getName(), pos.x, pos.y - 5);
            }
        }
    }

    public void highlightPlayer(Player player) {
        repaint();
    }
}