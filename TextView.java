import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextView implements View{
    private Scanner scanner;
    private GameController moderator;
    private LocationManager lm;

    public TextView(GameController moderator, LocationManager lm){
        this.lm = lm;
        this.moderator = moderator;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void startGameMessage(){
        System.out.println("=== DEADWOOD ===");
        System.out.println("Welcome to Deadwood! Please enter the number of players to start the game: ");
        System.out.println("Type 'help' for a list of commands");
    }

    @Override
    public void displayStartingStats(Player player){
        System.out.printf("Every player will start at rank %d with %d dollars, and %d credits%n",
        player.getRank(),
        player.getDollars(),
        player.getCredits()
        );
    }

    @Override
    public void endGameMessage(){
        System.out.println("\n=== GAME OVER ===");
        moderator.endGame();
    }

    @Override
    public void promptAction(){
        Boolean valid = false;
        while(!valid){
            System.out.print("> ");
            String action = scanner.nextLine();
            valid = moderator.processAction(action);
        }
    }

    @Override
    public void promptPlayerCount(){
        int numPlayers = 0;
        while(numPlayers < 2 || numPlayers > 8){
            System.out.print("How many players? > ");
            numPlayers = Integer.parseInt(scanner.nextLine());
            if(numPlayers < 2 || numPlayers > 8){
                System.out.println("Deadwood only supports 2-8 players");
            }
        }
        moderator.initializePlayers(numPlayers);
        System.out.println(numPlayers + " players");
    }
    
    @Override
    public void displayPlayerLocation(Player player) {
        String playerLocationName = player.getLocation();
        Set playerLocation = lm.getSet(playerLocationName);
        if(playerLocation.isSet()){
            if(playerLocation.getSceneCard().getFlipped()){
                System.out.printf("in %s shooting %s scene %d%n", 
                playerLocationName,
                playerLocation.getSceneCard().getName(),
                playerLocation.getSceneCard().getSceneNumber()
                );
            }
            else{
                System.out.printf("in %s wrapped%n", playerLocationName);
            }
        }
        else{
            System.out.printf("in %s%n", playerLocationName);
        }
    }
    
    //Informs player of acting result
    @Override
    public void displayActResult(Player player, boolean success) {
        if (success) {
            if(player.getRole().getStarring()){
                System.out.println("Success! You got 2 credits!");
            }
            else{
                System.out.println("Success! You got 1 credit and 1 dollar!");
            }
            
        } else {
            if(player.getRole().getStarring()){
                System.out.println("Your acting was unsuccessful");
            }
            else{
                System.out.println("Your acting was unsuccessful, you got 1 dollar");
            }
        }
    }

    @Override
    //Displays the player information formatted with dollars and credits
    public void displayPlayerInfo(Player player) {
        if(player.getWorking()){
            System.out.printf(
                "The active player is %s. They have $%d, %d credits and %d rehearsal chips. " +
                "They are working %s, \"%s\"%n",
                player.getName(), 
                player.getDollars(),
                player.getCredits(),
                player.getPracticeChips(),
                player.getRole().getName(), 
                player.getRole().getLine()
            );
        }
        else{
            System.out.printf(
                "Player %s ($%d, %dcr)%n",
                player.getName(),
                player.getDollars(),
                player.getCredits()
            );
        }
    }

    @Override
    public void displayNeighbors(Set set){
        System.out.println(set.getAdjacentSets());
    }

    @Override
    public void displayCurrentPlayer(Player player){
        System.out.println("Player " + player.getName() + "'s turn!");
    }

    @Override
    public Role chooseFromAvailableRoles(Player player) {
        Set location = lm.getSet(player.getLocation());
        Map<String, Role> allRoles = new HashMap<>();
    
        // Show off card roles with 
        System.out.println("\nOff-Card Roles:");
        int index = 1;
        for (Role role : location.getOffRoles().values()) {
            if (role.isAvailable()) {
                System.out.printf("%d. %s (Rank %d)\n", index++, role.getName(), role.getRankRequired());
                allRoles.put(String.valueOf(index-1), role);
            }
        }

    // Show on card roles 
        System.out.println("\nOn-Card Roles:");
        if (location.getSceneCard() != null) {
            for (Role role : location.getSceneCard().getRoleList().values()) {
                if (role.isAvailable()) {
                    System.out.printf("%d. %s (Rank %d)\n", index++, role.getName(), role.getRankRequired());
                    allRoles.put(String.valueOf(index-1), role);
            }
        }
        }
        System.out.print("Choose a role by number: ");
        String choice = scanner.nextLine();
        return allRoles.get(choice);
    }

    @Override
    public void displayHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("move <location>  - Move to adjacent set");
        System.out.println("act              - Attempt to act in current role");
        System.out.println("rehearse         - Practice for your role");
        System.out.println("take role        - Choose an available role");
        System.out.println("upgrade <rank> <currency> - Increase your rank");
        System.out.println("where            - Show current location");
        System.out.println("who              - Show player status");
        System.out.println("neighbors        - Show neighboring locations");
        System.out.println("end              - Finish your turn");
        System.out.println("endgame          - End the game and proceed to scoring");
        System.out.println("help             - Show this message");
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
