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
        drawPlayers(g);
    }

    private void drawPlayers(Graphics g) {
        int diceSize = 40;  // size of the dice images
        int offsetStep = 45; // offset so players don't overlap exactly

        java.util.List<Player> players = gameController.getPlayers();

        Map<String, Integer> playerCountAtLocation = new HashMap<>();


        for (Player player : players) {
            String location = player.getLocation();
            Point basePos = locationCoordinates.get(location);

            if (basePos != null) {
                // Count how many players already drawn at this location to offset dice
                int count = playerCountAtLocation.getOrDefault(location, 0);

                // Calculate position offset for each player
                int x = basePos.x + count * offsetStep;
                int y = basePos.y;

                // Load the dice image for player's current face (for example, first face)
                // Assuming player.getDieImages() returns List<String> with filenames like "b1.png"
                String imageName = player.getDieImage(); // or player's current face index

                ImageIcon icon = new ImageIcon("images/" + imageName);
                Image diceImage = icon.getImage().getScaledInstance(diceSize, diceSize, Image.SCALE_SMOOTH);

                g.drawImage(diceImage, x, y, this);

                // Update count for this location
                playerCountAtLocation.put(location, count + 1);
            }
        }
    }
    



    public void highlightPlayer(Player player) {
        repaint();
    }

    
}