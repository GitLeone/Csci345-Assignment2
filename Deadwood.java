public class Deadwood {
    public static void main(String[] args) {
        GameController moderator = new GameController();
        LocationManager locationManager = moderator.getLocationManager();

        View view = new TextView(moderator, locationManager);
        View guiView = new GUIView(moderator, locationManager);
        moderator.setView(view);

        view.startGameMessage();
        view.promptPlayerCount();
        Player player = moderator.getActivePlayer();
        view.displayStartingStats(player);
        //Main game loop
        
        view.displayCurrentPlayer(player);
        while(!moderator.getGameOver()){
            moderator.setDayOver(false);
            while(!moderator.isDayOver()){
                player = moderator.getActivePlayer();
                view.promptAction();
            }
            moderator.endDay();
        }
        moderator.endGame();
    } 
}