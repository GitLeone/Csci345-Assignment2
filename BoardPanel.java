import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.util.List;

public class BoardPanel extends JPanel {
    private GameController gameController;
    private LocationManager locationManager;
    private Image boardImage;
    private Image cardBackImage;
    private Image sceneImage;
    private Image shotImage;
    private Map<String, Point> locationCoordinates;

    public BoardPanel(GameController controller) {
        this.gameController = controller;
        this.locationManager = controller.getLocationManager();
        loadBoardImage();
        initializeSceneCardCoordinates();
        setPreferredSize(new Dimension(boardImage.getWidth(this), boardImage.getHeight(this)));
    }

    private void loadBoardImage() {
        setLayout(null);
        ImageIcon icon = new ImageIcon("images/board.jpg");
        boardImage = icon.getImage();
    }

    private void initializeSceneCardCoordinates() {
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
                    loadSceneCard(set);
                    g.drawImage(sceneImage, pos.x, pos.y, this);
                }
                loadShotImg();
                for (Take take : set.getTakes()){
                    g.drawImage(shotImage, take.getXCord(), take.getYCord(), this);
                } 
            }
        }

        drawPlayers(g);
    }

    private void drawPlayers(Graphics g) {
        int diceSize = 40;  // size of the dice images
        int offsetStep = 45; // offset so players don't overlap exactly

        List<Player> players = gameController.getPlayers();

        Map<String, Integer> playerCountAtLocation = new HashMap<>();


        for (Player player : players) {
            // String location = player.getLocation();
            // Point basePos = locationCoordinates.get(location);
            String imageName = player.getDieImage(); // or player's current face index

            ImageIcon icon = new ImageIcon("images/Dice/" + imageName);
            Image diceImage = icon.getImage().getScaledInstance(diceSize, diceSize, Image.SCALE_SMOOTH);
            g.drawImage(icon.getImage(), player.getXCord(), player.getYCord(), this);
        }
    }

    public void loadSceneCard(Set set){
        SceneCard card = set.getSceneCard();
        ImageIcon icon;
        if (card.getFlipped()){
            icon = new ImageIcon("images/Card/" + card.getImg());
            sceneImage = icon.getImage();
        }
        else{
            icon = new ImageIcon("images/cardBack.png");
            sceneImage = icon.getImage();
        }
    }

    public void loadShotImg(){
        ImageIcon icon = new ImageIcon("images/shot.png");
        shotImage = icon.getImage();
    }

    public void highlightPlayer(Player player) {
        repaint();
    }
    
}