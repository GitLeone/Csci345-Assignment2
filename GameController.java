import java.util.ArrayList;
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
    public boolean processAction(String input){
        //Right now input is case sensitive
        Player currentPlayer = getActivePlayer();
        Set currentPlayerLocation = locationManager.getSet(currentPlayer.getLocation());
        SceneCard currentScene = currentPlayerLocation.getSceneCard();
        String action;
        String[] lineSplit = input.split(" ", 2);

        
        System.out.println(currentPlayer.getName() + " please perform a valid action.");

        if(input.equals("take role")){
            action = input;
        }
        else{
            action = lineSplit[0];
        }
        switch (action) {
            case "move":
                if(lineSplit.length < 2){
                    return false;
                }
                String location = lineSplit[1];
                if(!currentPlayer.move(location, locationManager)){
                    view.displayMessage("That move is invalid.");
                    return false;
                }
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
                } else {
                    view.displayMessage("You cannot rehearse right now.");
                    return false;
                }
                break;

            case "upgrade":
                if (lineSplit.length < 2) {
                    view.displayMessage("Usage: upgrade <rank> <currency>");
                    return false;
                }
                String[] upgradeArgs = lineSplit[1].split(" ");
                if (upgradeArgs.length < 2) {
                    view.displayMessage("Usage: upgrade <rank> <currency>");
                    return false;
                }
                try {
                    int newRank = Integer.parseInt(upgradeArgs[0]);
                    String currency = upgradeArgs[1];
                    if(currentPlayer.upgrade(newRank, currency, bank, locationManager)){
                        view.displayMessage("Upgrade failed");
                        return false;
                    }
                } 
                catch (NumberFormatException e) {
                    view.displayMessage("Rank must be a number.");
                    return false;
                }
                break;

            case "take role":
                if(!currentPlayerLocation.isSet()){
                    view.displayMessage("You are not in a set");
                    return false;
                }
                Role chosenRole = view.chooseFromAvailableRoles(currentPlayer);
                if(!currentPlayer.takeRole(chosenRole)){
                    view.displayMessage("You can not take that role");
                    return false;
                }
                break;

            case "act":
                if (currentPlayer.getRole() == null) {
                    System.out.println("You must take a role before acting.");
                    return false;
                }   
                if(!currentPlayer.act(dice, currentPlayerLocation, currentScene)){
                    System.out.println("You can not act right now.");
                    return false;
                }
                break;

            case "help":
                view.displayHelp();
                break;

            default:
                break;
        }
        return true;
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
