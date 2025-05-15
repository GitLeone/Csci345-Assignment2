public class Deadwood {
    public static void main(String[] args) {
        GameController moderator = new GameController();
        View view = new TextView(moderator);

        view.promptPlayerCount();
        view.displayMessage("Game starting..."); // Test message
    }
}