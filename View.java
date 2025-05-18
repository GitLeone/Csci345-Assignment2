public interface View {
    void displayPlayerInfo(Player player);
    void displayPlayerLocation(Player player);
    void displayActResult(boolean success, int reward, String rewardType);
    void promptPlayerCount();
    void startGameMessage();
    void endGameMessage();
    void promptAction();
    void displayHelp();
    void displayMessage(String message);
    Role chooseFromAvailableRoles(Player player);
    void displayCurrentPlayer(Player player);
    void displayNeighbors(Set set);
}