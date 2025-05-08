
public interface View {
    //Later implementation for player inputs
    //private Scanner scanner = new Scanner(System.in);

    public void displayActivePlayerWorking(Player player, Role role);
    
    public void displayLocation(Set set);
    
    //Informs player of acting result
    public void displayActResult(boolean success, int reward, String rewardType);

    //Displays the player information formatted with dollars and credits
    public void displayPlayerInfo(Player player);
}
