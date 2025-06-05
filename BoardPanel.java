import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.util.List;

public class BoardPanel extends JPanel {
    private GameController gameController;
    private LocationManager locationManager;
    private Image boardImage;
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
    
    //
    private void initializeSceneCardCoordinates() {
        locationCoordinates = new HashMap<>();
        // Initialize with coordinates from the XML parse
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
                    if(set.getSceneCard() != null){
                        loadSceneCard(set);
                        g.drawImage(sceneImage, pos.x, pos.y, this);                        
                    }
                }
                loadShotImg();
                for (Take take : set.getTakes()){
                    g.drawImage(shotImage, take.getXCord(), take.getYCord(), this);
                } 
            }
        }

        drawPlayers(g);
    }
    //Draws every player at their current location
    private void drawPlayers(Graphics g) {
        List<Player> players = gameController.getPlayers();
        for (Player player : players) {
            // String location = player.getLocation();
            // Point basePos = locationCoordinates.get(location);
            String imageName = player.getDieImage(); // or player's current face index

            ImageIcon icon = new ImageIcon("images/Dice/" + imageName);
            g.drawImage(icon.getImage(), player.getXCord(), player.getYCord(), this);
        }
    }

    //loads the scenecards on every set
    //If the scenecard has not been flipped, it displays the back of the card
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

    //loads the png of the takes
    public void loadShotImg(){
        ImageIcon icon = new ImageIcon("images/shot.png");
        shotImage = icon.getImage();
    }

    public void highlightPlayer(Player player) {
        repaint();
    }
    
}