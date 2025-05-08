
public class View {
    //Later implementation for player inputs
    //private Scanner scanner = new Scanner(System.in);

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
    
    public void displayLocation(Set set) {

    }
    
    //Informs player of acting result
    public void displayActResult(boolean success, int reward, String rewardType) {
        if (success) {
            System.out.printf("success! You got %d %s%n", reward, rewardType);
        } else {
            System.out.println("Your acting was unsuccessful");
        }
    }

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
