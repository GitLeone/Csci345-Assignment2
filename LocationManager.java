import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Point;

public class LocationManager{
   private Map<Player, Set> playerLocations; //Tracks where players are
   private Map<String, Set> setList; //Contains list of sets and associated set object
   
   public LocationManager(Map<String, Set> setList){
        this.playerLocations = new HashMap<>();
        this.setList = new HashMap<>(setList);
        initializeSetBasePoints();
   }

   public void updatePlayerLocation(Player player, Set location){
        playerLocations.remove(player);
        playerLocations.put(player, location);
        Point newPoint = location.getPlayerBasePoint(player);
        int newX = newPoint.x;
        int newY = newPoint.y;
        player.setCoordinates(newX, newY);
   }

   public boolean validateMove(Player player, String location){
    //if the location the player wants to move to is not a neighbor of their current location, return false, else return true
    if(getPlayerLocation(player).getAdjacentSets().contains(location)){
        updatePlayerLocation(player, getSet(location));
        return true;
    }
    else{
        return false;
    }
   }

   public boolean validateUpgrade(Player player){
    if(getPlayerLocation(player).getName().equals("office")){
        return true;
    }
    else{
        return false;
    }
   }

    public Set getPlayerLocation(Player player) {
        return playerLocations.getOrDefault(player, setList.get("trailer")); // Default to trailers
    }

    public void displayAllPlayerLocations(List<Player> players, Player activePlayer) {
        System.out.println("\n=== PLAYER LOCATIONS ===");
    
        for (Player player : players) {
            Set location = getPlayerLocation(player);
            String locationInfo = location.getName();
        
            if (location.isSet() && location.getSceneCard() != null) {
                locationInfo += " (" + location.getSceneCard().getName() + ")";
                if (player.getWorking()) {
                    locationInfo += " [Working as " + player.getRole().getName() + "]";
                }
            }
        
            // Gives us the active player
            if (player.equals(activePlayer)) {
                System.out.print("ACTIVE PLAYER --> ");
            } else {
                System.out.print("                  ");
            }
        
            System.out.println(player.getName() + ": " + locationInfo);
        }
    }

    public Map<String, Set> getSetList(){
        return this.setList;
    }
    public Set getSet(String location){
        return setList.get(location);
    }
    public void initializeSetBasePoints(){
        for (Set set : setList.values()){
            String name = set.getName().toLowerCase();
            if(name.equals("trailer")){
                set.addBasePoint(new Point(995, 266));
                set.addBasePoint(new Point(995, 304));
                set.addBasePoint(new Point(995, 344));
                set.addBasePoint(new Point(995, 384));  
                set.addBasePoint(new Point(1147, 266));
                set.addBasePoint(new Point(1147, 304));
                set.addBasePoint(new Point(1147, 344));
                set.addBasePoint(new Point(1147, 384));  
            }
            else if(name.equals("main street")){
                set.addBasePoint(new Point(776, 72));
                set.addBasePoint(new Point(816, 72));
                set.addBasePoint(new Point(856, 72));
                set.addBasePoint(new Point(896, 72));  
                set.addBasePoint(new Point(776, 112));
                set.addBasePoint(new Point(816, 112));
                set.addBasePoint(new Point(856, 112));
                set.addBasePoint(new Point(896, 112));  
            }
            else if(name.equals("saloon")){
                set.addBasePoint(new Point(724, 236));
                set.addBasePoint(new Point(764, 236));
                set.addBasePoint(new Point(804, 236));
                set.addBasePoint(new Point(844, 236)); 
                set.addBasePoint(new Point(610, 401));
                set.addBasePoint(new Point(650, 401));
                set.addBasePoint(new Point(782, 401));
                set.addBasePoint(new Point(822, 401));  
            }
            else if(name.equals("hotel")){
                set.addBasePoint(new Point(1000, 470));
                set.addBasePoint(new Point(1000, 510));
                set.addBasePoint(new Point(1000, 550));
                set.addBasePoint(new Point(1000, 590)); 
                set.addBasePoint(new Point(946, 856));
                set.addBasePoint(new Point(986, 856));
                set.addBasePoint(new Point(1121, 856));
                set.addBasePoint(new Point(1161, 856));
            }
            else if(name.equals("bank")){
                set.addBasePoint(new Point(610, 593));
                set.addBasePoint(new Point(650, 593));
                set.addBasePoint(new Point(765, 593));
                set.addBasePoint(new Point(805, 593)); 
                set.addBasePoint(new Point(845, 593));
                set.addBasePoint(new Point(835, 463));
                set.addBasePoint(new Point(835, 503));
                set.addBasePoint(new Point(875, 463));
            }
            else if(name.equals("church")){
                set.addBasePoint(new Point(609, 852));
                set.addBasePoint(new Point(649, 852));
                set.addBasePoint(new Point(773, 852));
                set.addBasePoint(new Point(813, 852));
                set.addBasePoint(new Point(737, 688));
                set.addBasePoint(new Point(777, 688));
                set.addBasePoint(new Point(817, 688));
                set.addBasePoint(new Point(857, 688));
            }
            else if(name.equals("secret hideout")){
                set.addBasePoint(new Point(240, 845));
                set.addBasePoint(new Point(280, 845));
                set.addBasePoint(new Point(320, 845));
                set.addBasePoint(new Point(360, 845));
                set.addBasePoint(new Point(240, 805));
                set.addBasePoint(new Point(280, 805));
                set.addBasePoint(new Point(320, 805));
                set.addBasePoint(new Point(360, 805));
            }
            else if(name.equals("ranch")){
                set.addBasePoint(new Point(267, 652));
                set.addBasePoint(new Point(307, 652));
                set.addBasePoint(new Point(347, 647));
                set.addBasePoint(new Point(541, 600));
                set.addBasePoint(new Point(541, 560));
                set.addBasePoint(new Point(541, 520));
                set.addBasePoint(new Point(267, 612));
                set.addBasePoint(new Point(307, 612));
            }
            else if(name.equals("office")){
                set.addBasePoint(new Point(5, 495));
                set.addBasePoint(new Point(5, 535));
                set.addBasePoint(new Point(5, 575));
                set.addBasePoint(new Point(5, 605));
                set.addBasePoint(new Point(175, 495));
                set.addBasePoint(new Point(175, 535));
                set.addBasePoint(new Point(175, 575));
                set.addBasePoint(new Point(175, 605));
            }
            else if(name.equals("train station")){
                set.addBasePoint(new Point(10, 217));
                set.addBasePoint(new Point(10, 257));
                set.addBasePoint(new Point(10, 297));
                set.addBasePoint(new Point(10, 337));
                set.addBasePoint(new Point(50, 217));
                set.addBasePoint(new Point(118, 391));
                set.addBasePoint(new Point(-4, 16));
                set.addBasePoint(new Point(195, 16));
            }
            else if(name.equals("jail")){
                set.addBasePoint(new Point(513, 199));
                set.addBasePoint(new Point(473, 199));
                set.addBasePoint(new Point(433, 199));
                set.addBasePoint(new Point(393, 199));
                set.addBasePoint(new Point(353, 199));
                set.addBasePoint(new Point(313, 199));
                set.addBasePoint(new Point(400, 148));
                set.addBasePoint(new Point(313, 159));
            }
            else if(name.equals("general store")){
                set.addBasePoint(new Point(197, 400));
                set.addBasePoint(new Point(197, 360));
                set.addBasePoint(new Point(197, 320));
                set.addBasePoint(new Point(197, 280));
                set.addBasePoint(new Point(280, 400));
                set.addBasePoint(new Point(280, 360));
                set.addBasePoint(new Point(280, 320));
                set.addBasePoint(new Point(280, 280));
            }
        }
    }
}
