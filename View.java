public interface View {
    void displayMessage(String message);
    void displayPlayerStatus(Player player);
    void displayLocation(Set set);
    void displayActResult(boolean success, int reward, String rewardType);
}