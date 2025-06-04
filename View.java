public interface View {
    void displayPlayerInfo(Player player);
    void displayPlayerLocation(Player player);
    void displayActResult(Player player, boolean success, boolean onCard);
    void promptPlayerCount();
    void startGameMessage();
    void endGameMessage();
    void promptAction();
    void displayHelp();
    void displayMessage(String message);
    Role chooseFromAvailableRoles(Player player);
    void displayCurrentPlayer(Player player);
    void displayNeighbors(Set set);
    void displayStartingStats(Player player);
    default void updateBoard(){}
}