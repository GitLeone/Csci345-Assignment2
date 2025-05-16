import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.ParserConfigurationException;

import javax.management.DescriptorKey;

public class GameController {
    private int currentDay;
    private int maxDays;
    private Bank bank;
    private List<Player> players;
    private LocationManager locationManager;
    private View view;
    private int currentPlayerIndex;
    private Dice dice;
    private Deck deck;
    private boolean gameOver;
    private boolean dayOver;

    public GameController() {
        this.players = new ArrayList<>();
        this.dice = new Dice();
        this.currentDay = 1;
        initializeGameBoard();
    }

    public LocationManager getLocationManager(){
        return this.locationManager;
    }

    private void initializeGameBoard() {
        //The parse should fill its maps and the bank, locationManager, and deck, which are then used to fill this classes maps
        //The parse creates the objects themselves and all related classes Ex. The parse creates the deck and all 40 scenes with their respective attributes
        ParseXML parse = new ParseXML();
        try {
            Document cardData = parse.getDocFromFile("cards.xml");
            Document boardData = parse.getDocFromFile("board.xml");
            parse.readCardData(cardData);
            parse.readBoardData(boardData);
        }
        catch(ParserConfigurationException e){
            System.err.println("Error");
        }catch(ArrayIndexOutOfBoundsException e){
            System.err.println("Error: Wrong number of arguments\n Proper usage: java cards.xml board.xml");
        }
        catch(NullPointerException e){
            System.err.println("Error: Wrong use of arguments\n Proper usage: java cards.xml board.xml");
        }

        //Initializes bank, locationManager, deck
        this.bank = new Bank(parse.getDollarMap(), parse.getCreditMap());
        this.locationManager = new LocationManager(parse.getSetList());
        this.deck = new Deck(parse.getSceneDeck());
    }

    //deals one sceneCard to each set
    public void dealSceneCards(){
        for(Set value : locationManager.getSetList().values()){
            SceneCard randCard = deck.drawRandomSceneCard();
            value.setSceneCard(randCard);
        }
    }

    public void initializePlayers(int numPlayers) {
        int startingCredits = 0;
        int startingDollars = 0;
        int startingRank = 0;
        this.currentPlayerIndex = 0;

        if(numPlayers == 5){
            startingCredits = 2;
        }
        else if(numPlayers == 6){
            startingCredits = 4;
        }
        else if(numPlayers == 7 || numPlayers == 8){
            startingRank = 2;
        }
        setMaxDays((numPlayers == 2 || numPlayers == 3) ? 3 : 4);
        
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player(
                "Player " + (i+1),
                startingRank,             // starting rank
                startingCredits,
                startingDollars,
                0,            
                false,         
                "trailer"
                );
                players.add(player);
                locationManager.updatePlayerLocation(player, locationManager.getSet("trailer"));
            }
    }

    public boolean isDayOver(){
        return this.dayOver;
    }
    public void setDayOver(boolean dayOver){
        this.dayOver = dayOver;
    }
    public void endDay(){
        setDayOver(true);
        currentDay++;
        if(currentDay > maxDays){
            setGameOver(true);
        }
    }
    public void setView(View view){
        this.view = view;
    }

    //This processAction will have switch statements for all player actions
    public void processAction(String input){
        //Right now input is case sensitive
        String[] line = input.split(" ", 2);
        String action = line[0];
        Player currentPlayer = getActivePlayer();
        switch (action) {
            case "move":
                if(line.length < 2){
                    view.invalidAction();
                    break;
                }
                String location = line[1];
                if(!currentPlayer.move(location, locationManager)){
                    view.invalidAction();
                }
                break;

            case "where":
                view.displayPlayerLocation(currentPlayer);
                break;

            case "who":
                view.displayPlayerInfo(currentPlayer);
                break;

            default:
                break;
        }
    }

    public Player getActivePlayer(){
        return players.get(currentPlayerIndex);
    }

    public void endTurn(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void endGame() {
        // Calculate and display final scores
        for (Player player : players) {
            int score = player.getDollars() + player.getCredits() + (player.getRank() * 5);
            //view.displayMessage(player.getName() + ": " + score + " points");
        }
    }
    public boolean getGameOver(){
        return this.gameOver;
    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    public void setMaxDays(int maxDays){
        this.maxDays = maxDays;
    }
}
