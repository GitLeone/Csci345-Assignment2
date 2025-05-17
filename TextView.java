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
            if(!valid){
                invalidAction();
            }
        }
    }

    @Override
    public void promptPlayerCount(){
        System.out.print("How many players? > ");
        int numPlayers = Integer.parseInt(scanner.nextLine());
        moderator.initializePlayers(numPlayers);
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
    public void displayActResult(boolean success, int reward, String rewardType) {
        if (success) {
            System.out.printf("success! You got %d %s%n", reward, rewardType);
        } else {
            System.out.println("Your acting was unsuccessful");
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
                "player %s ($%d, %dcr)%n",
                player.getName(),
                player.getDollars(),
                player.getCredits()
            );
        }
    }

    @Override
    public Role chooseFromAvailableRoles(Player player){
        String playerLocationName = player.getLocation();
        Set playerLocation = lm.getSet(playerLocationName);
        Role role;

        System.out.println("\nAvailable off card roles:"); 
        Map<String, Role> offCardRoles = playerLocation.getOffRoles();
        for (String key : offCardRoles.keySet()) {
            if(offCardRoles.get(key).isAvailable()){
                System.out.println("Role: " + key + " requires rank " + offCardRoles.get(key).getRankRequired());
            }
        }

        System.out.println("\nAvailable on card roles:");
        Map<String, Role> onCardRoles = playerLocation.getSceneCard().getRoleList();
        for (String key : onCardRoles.keySet()) {
            if(onCardRoles.get(key).isAvailable()){
                System.out.println("Role: " + key + " requires rank " + onCardRoles.get(key).getRankRequired());
            }
        }

        System.out.printf("Which role would you like: > ");
        String chosenRole = scanner.nextLine();
        if(onCardRoles.containsKey(chosenRole)){
            role = playerLocation.getSceneCard().getRole(chosenRole);
        }
        else{
            role = playerLocation.getOffRole(chosenRole);
        }
        return role;
    }

    //When we make other invalid action statements, they can go here. We can add a String parameter for the type of invalid action taken to print the right statement
    //Something like that
    @Override
    public void invalidAction(){
        System.out.println("That action is not valid, try again");
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
        System.out.println("end              - Finish your turn");
        System.out.println("help             - Show this message");
    }
    
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}
