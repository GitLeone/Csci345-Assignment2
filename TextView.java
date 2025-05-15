import java.util.Scanner;

public class TextView implements View{
    private Scanner scanner;
    private GameController moderator;

    public TextView(GameController moderator){
        this.moderator = moderator;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayActivePlayerWorking(Player player, Role role) {
        System.out.printf(
            "The active player is %s. They have $%d, %d credits and %d rehearsal chips. " +
            "They are working %s, \"%s\"%n",
            player.getName(), 
            player.getDollars(),
            player.getCredits(),
            player.getPracticeChips(),
            role.getName(), 
            role.getLine()
        );
    }

    @Override
    public void promptAction(){
        System.out.println("> ");
        String action = scanner.nextLine();
        moderator.processAction(action);
    }

    @Override
    public void promptPlayerCount(){
        System.out.println("How many players? > ");
        int playerCount = scanner.nextInt();
        moderator.initializePlayers(playerCount);
    }
    
    @Override
    public void displayLocation(Set set) {
        
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
        System.out.printf(
            "player %s ($%d, %dcr)%n",
            player.getName(),
            player.getDollars(),
            player.getCredits()
        );
    }
}
