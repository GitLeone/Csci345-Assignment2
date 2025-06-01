import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private GameController gameController;
    private LocationManager locationManager;
    private Image boardImage;
    private Image cardBackImage;
    private Map<String, Point> locationCoordinates;

    public BoardPanel(GameController controller) {
        this.gameController = controller;
        this.locationManager = controller.getLocationManager();
        loadBoardImage();
        loadFaceDownCards();
        initializeLocationCoordinates();
        setPreferredSize(new Dimension(boardImage.getWidth(this), boardImage.getHeight(this)));
    }

    private void loadBoardImage() {
        setLayout(null);
        ImageIcon icon = new ImageIcon("images/board.jpg");
        boardImage = icon.getImage();
    }

    private void loadFaceDownCards() {
        ImageIcon icon = new ImageIcon("images/cardBack.png");
        cardBackImage = icon.getImage();
    }

    private void initializeLocationCoordinates() {
        locationCoordinates = new HashMap<>();
        // Initialize with coordinates from your XML or hardcoded
        for(Set value : locationManager.getSetList().values()){
            locationCoordinates.put(value.getName(), new Point(value.getXCord(), value.getYCord()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(boardImage, 0, 0, this);

        //Draws the face down cards
        for (Set set : locationManager.getSetList().values()) {
            if (set.isSet()) {
                Point pos = locationCoordinates.get(set.getName());
                if (pos != null) {
                    g.drawImage(cardBackImage, pos.x, pos.y, this);
                }
            }
        }
        
        // Draw players
        for (Player player : gameController.getPlayers()) {
            String img = player.getDieImage();
            ImageIcon icon = new ImageIcon("images/" + img);
            Image playerImage = icon.getImage().getScaledInstance(46, 46, Image.SCALE_SMOOTH);
            g.drawImage(playerImage, player.getXCord(), player.getYCord(), this);
        }
    }

    public void highlightPlayer(Player player) {
        repaint();
    }

}