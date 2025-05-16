public class Deadwood {
    public static void main(String[] args) {
        GameController moderator = new GameController();
        LocationManager locationManager = moderator.getLocationManager();
        View view = new TextView(moderator, locationManager);
        moderator.setView(view);

        view.promptPlayerCount();
        view.startGameMessage();
        //Main game loop
        while(!moderator.getGameOver()){
            while(!moderator.isDayOver()){
                Player player = moderator.getActivePlayer();
                view.promptAction();
            }
            moderator.endDay();
        }
        moderator.endGame();
    } 
}