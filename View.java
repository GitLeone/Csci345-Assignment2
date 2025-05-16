public interface View {
    void displayPlayerInfo(Player player);
    void displayPlayerLocation(Player player);
    void displayActResult(boolean success, int reward, String rewardType);
    void promptPlayerCount();
    void startGameMessage();
    void endGameMessage();
    void promptAction();
    void invalidAction();
    Role chooseFromAvailableRoles(Player player);
}