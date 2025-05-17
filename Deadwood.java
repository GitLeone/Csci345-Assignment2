public class Deadwood {
    public static void main(String[] args) {
        GameController moderator = new GameController();
        LocationManager locationManager = moderator.getLocationManager();
        View view = new TextView(moderator, locationManager);
        moderator.setView(view);

        view.startGameMessage();
        int numPlayers = view.promptPlayerCount();
        System.out.println(numPlayers + " players");
        //Main game loop
        while(!moderator.getGameOver()){
            moderator.dealSceneCards();  // Deal new scene cards
            moderator.setDayOver(false);
            while(!moderator.isDayOver()){
                Player player = moderator.getActivePlayer();
                view.displayMessage(player.getName() + "'s turn!");
                view.promptAction();
            }
            moderator.endDay();
        }
        moderator.endGame();
    } 
}