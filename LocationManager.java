import java.util.HashMap;
import java.util.Map;

public class LocationManager{
   private Map<Player, Set> playerLocations; //Tracks where players are
   private Map<String, Set> setList; //Contains list of sets and associated set object
   
   public LocationManager(Map<String, Set> setList){
        this.playerLocations = new HashMap<>();
        this.setList = new HashMap<>(setList);
   }

   public void updatePlayerLocation(Player player, Set location){
        playerLocations.remove(player);
        playerLocations.put(player, location);
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

    public Map<Player, Set> getAllPlayerLocations(){
        return this.playerLocations;
    }

    public Map<String, Set> getSetList(){
        return this.setList;
    }
    public Set getSet(String location){
        return setList.get(location);
    }
}
