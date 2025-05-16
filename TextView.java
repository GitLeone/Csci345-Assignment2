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
        System.out.print("> ");
        String action = scanner.nextLine();
        moderator.processAction(action);
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
    public void invalidAction(){
        System.out.println("That action is not valid, try again");
        promptAction();
    }
}
