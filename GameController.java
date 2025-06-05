import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

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
        dealSceneCards();
    }

    //deals one sceneCard to each set
    public void dealSceneCards(){
        SceneCard randCard;
        for(Set value : locationManager.getSetList().values()){
            if(value.isSet()){
                randCard = deck.drawRandomSceneCard();
                value.setSceneCard(randCard);
            }
        }
    }

    public void initializePlayers(int numPlayers) {
        Map<Integer, List<String>> colorMapping = new HashMap<>();
        List<String> blueDie   = Arrays.asList("b1.png", "b2.png", "b3.png", "b4.png", "b5.png", "b6.png");
        List<String> cyanDie   = Arrays.asList("c1.png", "c2.png", "c3.png", "c4.png", "c5.png", "c6.png");
        List<String> greenDie  = Arrays.asList("g1.png", "g2.png", "g3.png", "g4.png", "g5.png", "g6.png");
        List<String> orangeDie = Arrays.asList("o1.png", "o2.png", "o3.png", "o4.png", "o5.png", "o6.png");
        List<String> pinkDie   = Arrays.asList("p1.png", "p2.png", "p3.png", "p4.png", "p5.png", "p6.png");
        List<String> redDie    = Arrays.asList("r1.png", "r2.png", "r3.png", "r4.png", "r5.png", "r6.png");
        List<String> violetDie = Arrays.asList("v1.png", "v2.png", "v3.png", "v4.png", "v5.png", "v6.png");
        List<String> yellowDie = Arrays.asList("y1.png", "y2.png", "y3.png", "y4.png", "y5.png", "y6.png");
        colorMapping.put(0, blueDie);
        colorMapping.put(1, cyanDie);
        colorMapping.put(2, greenDie);
        colorMapping.put(3, orangeDie);
        colorMapping.put(4, pinkDie);
        colorMapping.put(5, redDie);
        colorMapping.put(6, violetDie);
        colorMapping.put(7, yellowDie);

        int startingCredits = 0;
        int startingDollars = 0;
        int startingRank = 1;
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
                String.valueOf(i+1),
                startingRank,             // starting rank
                startingCredits,
                startingDollars,
                0,            
                false,
                "trailer",
                colorMapping.get(i),
                i+1
                );
                players.add(player);
                locationManager.updatePlayerLocation(player, locationManager.getSet("trailer"));
            }
        view.updateBoard();
    }

    public boolean isDayOver(){
        return this.dayOver;
    }

    public void setDayOver(boolean dayOver){
        this.dayOver = dayOver;
    }

    public void endDay() {
        // Return all players to trailer and reset their stats
        for (Player player : players) {
            player.setLocation("trailer");
            player.setWorking(false);  // Clear any active roles
            player.setRole(null);
            player.setPracticeChips(0); // Reset rehearsal chips
            locationManager.updatePlayerLocation(player, locationManager.getSet("trailer"));
        }

        currentDay++;
    
        if (currentDay > maxDays) {
            endGame();
            return;
        }

        setDayOver(true);
        dealSceneCards();  // Refresh scenes for new day
        currentPlayerIndex = 0;  // Reset turn order
        view.displayMessage("\n=== DAY " + currentDay + " STARTED ===");
        view.displayCurrentPlayer(getActivePlayer());
    }

    public void setView(View view){
        this.view = view;
    }

    //This processAction will have switch statements for all player actions
    public boolean processAction(String input){
        //Right now input is case sensitive
        Player currentPlayer = getActivePlayer();
        Set currentPlayerLocation = locationManager.getSet(currentPlayer.getLocation());
        SceneCard currentScene = currentPlayerLocation.getSceneCard();
        String action;
        String[] lineSplit = input.split(" ", 2);

        if(input.equals("take role")){
            action = input;
        }
        else{
            action = lineSplit[0];
        }
        switch (action) {
            case "move":
                if(lineSplit.length < 2){
                    view.displayMessage("Usage: move <location>");
                    return false;
                }
                String location = lineSplit[1];
                if(!currentPlayer.move(location, locationManager)){
                    view.displayMessage("That move is invalid.");
                    return false;
                }
                view.displayMessage("Successful move to " + currentPlayer.getLocation());
                view.updateBoard();
                processAction("take role");
                break;

            case "where":
                view.displayPlayerLocation(currentPlayer);
                break;

            case "who":
                view.displayPlayerInfo(currentPlayer);
                break;

            case "rehearse":
                if (currentPlayer.getRole() == null) {
                    view.displayMessage("You must take a role before rehearsing.");
                    return false;
                }
    
                boolean rehearsed = currentPlayer.rehearse( currentPlayerLocation, currentPlayerLocation.getSceneCard());
    
                if (rehearsed) {
                    view.displayMessage("Rehearsed! Practice chips: " + currentPlayer.getPracticeChips() + 
                    "/" + (currentPlayerLocation.getSceneCard().getBudget() - 1));
                    endTurn();
                } else {
                    //Explains failure
                    if (currentPlayer.getPracticeChips() >= 
                        currentPlayerLocation.getSceneCard().getBudget() - 1) {
                        view.displayMessage("Max rehearsals reached for this scene, you must act!");
                    } else {
                        view.displayMessage("Cannot rehearse right now.");
                    }
                    return false;
                }
                break;

            case "upgrade":
                // 1. Input validation
                if (lineSplit.length < 2) {
                    view.displayMessage("Usage: upgrade <rank> <currency>");
                    return false;
                }
    
                String[] upgradeArgs = lineSplit[1].split(" ");
                if (upgradeArgs.length < 2) {
                    view.displayMessage("Usage: upgrade <rank> <currency>\nExample: upgrade 3 dollar");
                    return false;
                }

                try {
                    int newRank = Integer.parseInt(upgradeArgs[0]);
                    String currency = upgradeArgs[1].toLowerCase();
                    if (!currency.equals("dollar") && !currency.equals("credit")) {
                    view.displayMessage("Currency must be 'dollar' or 'credit'");
                    return false;
                    }

                    if (!currentPlayer.upgrade(newRank, currency, bank, locationManager)) {
                        view.displayMessage("Upgrade failed. Check:\n" + "- Are you in the Casting Office?\n"
                        + "- Is the rank higher than your current rank?\n"
                        + "- Do you have enough " + currency + "s?");
                        return false;
                    }
                    view.displayMessage("★ Upgraded to rank " + newRank + "!");
                    endTurn();
                } 
                catch (NumberFormatException e) {
                    view.displayMessage("Rank must be a number (2-6)");
                    return false;
                }
                break;

            case "take role":
                if (!currentPlayerLocation.isSet()) {
                    view.displayMessage("You must be on a set to take a role");
                    return false;
                }
                if(currentPlayer.getWorking()){
                    view.displayMessage("You are already working, you must finish your current scene");
                    return false;
                }
                Role chosenRole = view.chooseFromAvailableRoles(currentPlayer);
                if (chosenRole == null) {
                    view.displayMessage("Invalid role selection");
                return false;
                }
                if (!currentPlayer.takeRole(chosenRole, currentPlayerLocation)) {
                    view.displayMessage("Cannot take role. Required rank: " + chosenRole.getRankRequired() + ", Your rank: " + currentPlayer.getRank());
                    return false;
                }
                // Assigns the role to player
                if (chosenRole.getStarring()) {
                    view.displayMessage("You're now starring as: " + chosenRole.getName());
                    endTurn();
                } else {
                    view.displayMessage("You're now an extra as: " + chosenRole.getName());
                    endTurn();
                }
                break;

            case "act":
                boolean actResult = false;
                boolean onCard = false;
                if (currentPlayer.getRole() == null) {
                    view.displayMessage("You must take a role before acting.");
                    return false;
                }
                if(currentPlayer.getRole().getStarring()){
                    onCard = true;
                }
                if(currentPlayer.act(dice, currentPlayerLocation, currentScene)){
                    actResult = true;
                }
                view.displayActResult(currentPlayer, actResult, onCard);
                if(currentPlayerLocation.getShotsRemaining() == 0){
                    wrapScene(currentPlayerLocation);
                }
                endTurn();
                break;

            case "locations":
                locationManager.displayAllPlayerLocations(players, getActivePlayer());
                break;

            case "end":
                endTurn();
                break;

            case "help":
                view.displayHelp();
                break;

            case "neighbors":
                view.displayNeighbors(currentPlayerLocation);
                break;

            case "endgame":
                endGame();

            default:
                view.displayMessage("That is not a valid command, try again");
                break;
        }
        view.updateBoard();
        return true;
    }

    public Player getActivePlayer(){
        return players.get(currentPlayerIndex);
    }

    public void wrapScene(Set set){
       Player curPlayer;
       SceneCard scene = set.getSceneCard();
       scene.sortActingPlayers();
       Collections.reverse(scene.getActingPlayers());

       view.displayMessage("Thats a wrap!");

       List<Player> onCardActors = scene.getActingPlayers();
       List<Integer> diceRolls = new ArrayList<>();
        
       for(int i=0; i < scene.getBudget(); i++){
            diceRolls.add(dice.roll());
       }
       Collections.sort(diceRolls, Collections.reverseOrder());
       if(!onCardActors.isEmpty()){
            //Payout for onCard roles
            for(int i=0; i < diceRolls.size() ; i++){
                curPlayer = onCardActors.get(i % onCardActors.size());
                curPlayer.setDollars(curPlayer.getDollars() + diceRolls.get(i));
                view.displayMessage("Player " + curPlayer.getName() + " got $" + diceRolls.get(i) + " from a dice roll! Rank ->" + curPlayer.getRole().getRankRequired());
                curPlayer.setWorking(false);
                curPlayer.setRole(null);
            }
       }

       //Payout for offCard roles
        for(int i=0; i < set.getActingPlayers().size(); i++){
            curPlayer = set.getActingPlayers().get(i);
            if(!onCardActors.isEmpty()){
                Role playerRole = curPlayer.getRole();
                curPlayer.setDollars(curPlayer.getDollars() + playerRole.getRankRequired());
                view.displayMessage("Player " + curPlayer.getName() + " got $" + playerRole.getRankRequired() + " from their off card role!");
            }
            curPlayer.setWorking(false);
            curPlayer.setRole(null);
        }  
        set.setSceneCard(null);
        scene.setWrapped(true);    
    }

    // Ends the player's turn on their command
    public void endTurn(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player curPlayer = players.get(currentPlayerIndex);
        view.displayCurrentPlayer(curPlayer);
        view.updatePlayerPanel(curPlayer);
    }

    // Ends the entire game and calculates score and winner
    public void endGame() {
        view.displayMessage("Game Over! Final Scores:");
        int highestScore = -1; // Initialize high
        Player winner = null;
        // Calculate and display final scores
        for (Player player : players) {
            int score = player.getDollars() + player.getCredits() + (player.getRank() * 5);
            view.displayMessage(player.getName() + " - $" + player.getDollars() + ", " + player.getCredits() + " credits, rank " + player.getRank() + " => Total Score: " + score);

            if (score > highestScore) {
                highestScore = score;
                winner = player;
            }
        }

        if (winner != null) {
            view.displayMessage("Winner: " + winner.getName() + " with " + highestScore + " points!");
        }
        setGameOver(true);
        System.exit(0);
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

    public View getView() {
    return view;
}

public List<Player> getPlayers() {
    return players;
}

// public void handleActButton() {
//     Player player = getActivePlayer();
//     Set location = locationManager.getSet(player.getLocation());
    
//     if (player.getRole() == null) {
//         view.displayMessage("You need a role to act!");
//         return;
//     }
    
//     boolean success = player.act(dice, location, location.getSceneCard());
//     view.displayActResult(player, success, player.getRole().getStarring());
    
//     if (location.getShotsRemaining() == 0) {
//         wrapScene(location);
//     }
    
//     endTurn();
// }

//     public void handleRehearseButton() {
//         Player player = getActivePlayer();
//         Set location = locationManager.getSet(player.getLocation());
    
//         if (player.getRole() == null) {
//             view.displayMessage("You need a role to rehearse!");
//             return;
//         }
    
//         if (player.rehearse(location, location.getSceneCard())) {
//             view.displayMessage("Rehearsed! Chips: " + player.getPracticeChips());
//             endTurn();
//         } else {
//             view.displayMessage("Can't rehearse right now");
//         }
//     }

//     // public void initiateTakeRole() {
//     //     Player player = getActivePlayer();
//     //     Set location = locationManager.getSet(player.getLocation());
    
//     //     if (!location.isSet()) {
//     //         view.displayMessage("You must be on a set to take a role!");
//     //         return;
//     //     }
    
//     //     if (player.getWorking()) {
//     //         view.displayMessage("You are already working on a role!");
//     //         return;
//     //     }
    
//     //     view.chooseFromAvailableRoles(player);
//     // }

//     public void handleTakeRoleButton() {
//     Player player = getActivePlayer();
//     Set location = locationManager.getSet(player.getLocation());

//     if (!location.isSet()) {
//         view.displayMessage("You must be on a set to take a role!");
//         return;
//     }

//     if (player.getWorking()) {
//         view.displayMessage("You are already working on a role!");
//         return;
//     }

//     Role chosenRole = view.chooseFromAvailableRoles(player);
//     if (chosenRole == null) {
//         view.displayMessage("No role selected or available.");
//         return;
//     }

//     if (!player.takeRole(chosenRole, location)) {
//         view.displayMessage("Cannot take role. Required rank: " + chosenRole.getRankRequired() +
//                 ", Your rank: " + player.getRank());
//         return;
//     }

//     if (chosenRole.getStarring()) {
//         view.displayMessage("You're now starring as: " + chosenRole.getName());
//     } else {
//         view.displayMessage("You're now an extra as: " + chosenRole.getName());
//     }

//     endTurn();
//     }

//     public void handleUpgradeButton() {
//     Player player = getActivePlayer();
//     Set location = locationManager.getSet(player.getLocation());
    
//     if (!location.getName().equalsIgnoreCase("office")) {
//         view.displayMessage("You must be in the Casting Office to upgrade!");
//         return;
//     }
    
//     //view.promptUpgrade();
// }

}
