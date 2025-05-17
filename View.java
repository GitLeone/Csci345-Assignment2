public interface View {
    void displayPlayerInfo(Player player);
    void displayPlayerLocation(Player player);
    void displayActResult(boolean success, int reward, String rewardType);
    int promptPlayerCount();
    void startGameMessage();
    void endGameMessage();
    void promptAction();
    void invalidAction();
    void displayHelp();
    void displayMessage(String message);
    Role chooseFromAvailableRoles(Player player);
}