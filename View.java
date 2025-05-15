public interface View {
    void displayPlayerInfo(Player player);
    void displayLocation(Set set);
    void displayActResult(boolean success, int reward, String rewardType);
    void promptPlayerCount();
    void startGameMessage();
    void endGameMessage();
    void displayActivePlayerWorking(Player player, Role role);
    void promptAction();
}