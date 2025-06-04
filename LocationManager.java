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
        }
    }
}
