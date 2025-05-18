public class Deadwood {
    public static void main(String[] args) {
        GameController moderator = new GameController();
        LocationManager locationManager = moderator.getLocationManager();
        View view = new TextView(moderator, locationManager);
        moderator.setView(view);

        view.startGameMessage();
        view.promptPlayerCount();
        //Main game loop
        while(!moderator.getGameOver()){
            moderator.setDayOver(false);
            while(!moderator.isDayOver()){
                Player player = moderator.getActivePlayer();
                if(!player.isActive()){ //if players first turn
                    view.displayCurrentPlayer(player);
                }
                player.setActive(true);
                view.promptAction();
            }
            moderator.endDay();
        }
        moderator.endGame();
    } 
}