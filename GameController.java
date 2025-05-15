import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.management.DescriptorKey;

public class GameController {
    private int currentDay;
    private int maxDays;
    private Bank bank;
    private List<Player> players;
    // private List<Set> sets; location manager has all of the sets
    private LocationManager locationManager;
    //private View view; Doesnt need a view object, view makes a GameController object and calls its functions
    private int currentPlayerIndex;
    private Dice dice;
    private Deck deck;
    private boolean gameOver;

    public GameController() {
        this.players = new ArrayList<>();
        //this.sets = new ArrayList<>();
        this.dice = new Dice();
        //this.view = new TextView();
        this.currentDay = 1;
        initializeGameBoard();
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

        this.bank = new Bank(parse.getDollarMap(), parse.getCreditMap());
        this.locationManager = new LocationManager(parse.getSetList());
        this.deck = new Deck(parse.getSceneDeck());
        // Initialize all sets - The parse fills all of this out -
        // Set trailers = new Set("trailers", 0);
        // Set castingOffice = new Set("casting office", 0);
        // Set ranch = new Set("ranch", 3);
        // Set saloon = new Set("saloon", 2);
        // Set bankSet = new Set("bank", 1); 
        // Set mainStreet = new Set("main street", 2);
        // Set hotel = new Set("hotel", 3);
        // Set church = new Set("church", 2);
        // Set jail = new Set("jail", 1);
        // Set generalStore = new Set("general store", 2);
        // Set trainStation = new Set("train station", 2);
        // Set secretHideout = new Set("secret hideout", 1);

        // sets.addAll(Arrays.asList(
        //     trailers, castingOffice, ranch, saloon, bankSet,
        //     mainStreet, hotel, church, jail, generalStore,
        //     trainStation, secretHideout));
    }

    public void initializePlayers(int numPlayers) {
    int startingCredits = 0;
    int startingDollars = (numPlayers <= 3) ? 2 : 0;
    
    for (int i = 0; i < numPlayers; i++) {
        Player player = new Player(
            "Player " + (i+1),
            1,             // starting rank
            startingCredits,
            startingDollars,
            0,            
            false,         
            locationManager.getSet("trailer")
            );
            players.add(player);
            locationManager.updatePlayerLocation(player, locationManager.getSet("trailer"));
        }
    }

    public void startGame() {
    view.displayMessage("=== DEADWOOD ===");
    /* 
    while (!gameOver) {
        startDay();
        while (!isDayOver()) {
            Player currentPlayer = getActivePlayer();
            handlePlayerTurn(currentPlayer);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        endDay();
    }
    endGame();
    */
}
    //This processAction will have switch statements for all player actions
    public void processAction(String input){
        String[] line = input.split(" ", 2);
        String action = line[0];
        String arg = line[1];
        action = action.toLowerCase();
        switch (action) {
            case "move":
                Player currentPlayer = getActivePlayer();
                Set location = locationManager.getSet(arg);
                currentPlayer.move(location, locationManager);
                break;
        
            default:
                break;
        }
    }

    public Player getActivePlayer(){
        return players.get(currentPlayerIndex);
    }

    private void endGame() {
    view.displayMessage("\n=== GAME OVER ===");
    // Calculate and display final scores
    for (Player player : players) {
        int score = player.getDollars() + player.getCredits() + (player.getRank() * 5);
        view.displayMessage(player.getName() + ": " + score + " points");
    }
}

    
    
}
