import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameController {
    private int currentDay;
    private int maxDays;
    private Bank bank;
    private List<Player> players;
    private List<Set> sets;
    private LocationManager locationManager;
    private View view;
    private int currentPlayerIndex;
    private Dice dice;
    private boolean gameOver;

    public GameController(int numPlayers, View view) {
        this.players = new ArrayList<>();
        this.sets = new ArrayList<>();
        this.bank = new Bank();
        this.locationManager = new LocationManager(new HashMap<>(), new HashMap<>());
        this.dice = new Dice();
        this.view = new TextView();
        this.currentDay = 1;

        initializeGameBoard();
    }

    private void initializeGameBoard() {
    // Initialize all sets
    Set trailers = new Set("trailers", 0);
    Set castingOffice = new Set("casting office", 0);
    Set ranch = new Set("ranch", 3);
    Set saloon = new Set("saloon", 2);
    Set bankSet = new Set("bank", 1); 
    Set mainStreet = new Set("main street", 2);
    Set hotel = new Set("hotel", 3);
    Set church = new Set("church", 2);
    Set jail = new Set("jail", 1);
    Set generalStore = new Set("general store", 2);
    Set trainStation = new Set("train station", 2);
    Set secretHideout = new Set("secret hideout", 1);

    sets.addAll(Arrays.asList(
        trailers, castingOffice, ranch, saloon, bankSet,
        mainStreet, hotel, church, jail, generalStore,
        trainStation, secretHideout));
    }

    private void initializePlayers(int numPlayers) {
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
            "trailers",    
            null);
            players.add(player);
            locationManager.updatePlayerLocation(player, "trailers");
        }
    }

    public void startGame() {
    view.displayMessage("=== DEADWOOD ===");
    /* 
    while (!gameOver) {
        startDay();
        while (!isDayOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);
            handlePlayerTurn(currentPlayer);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        endDay();
    }
    endGame();
    */
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
