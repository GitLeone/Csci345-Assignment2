import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        if(currentDay > maxDays){
            endGame();
        }
        setDayOver(true);
        currentDay++;
        dealSceneCards(); //Deals new scene cards
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
                endTurn();
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
                boolean rehearsed = currentPlayer.rehearse(currentPlayerLocation, currentPlayerLocation.getSceneCard(), dice);
                if (rehearsed) {
                    view.displayMessage("You rehearsed successfully.");
                    endTurn();
                } else {
                    view.displayMessage("You cannot rehearse right now.");
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

    public void endTurn(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        view.displayCurrentPlayer(players.get(currentPlayerIndex));
    }

    public void endGame() {
        view.displayMessage("Game Over! Final Scores:");
        int highestScore = -1;
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
}
